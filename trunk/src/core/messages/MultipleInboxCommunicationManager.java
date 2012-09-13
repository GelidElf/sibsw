package core.messages;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import master.MasterInput;
import master.MasterModel;
import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

public class MultipleInboxCommunicationManager implements CommunicationManager{

	private HashMap<CommunicationIds, ConnectionManager> connections = new HashMap<CommunicationIds, ConnectionManager>();
	private int numberOfIncoming;
	private CommunicationIds owner;
	private Configuration conf;
	private int serverPort;
	private ServerSocket serverSocket;
	private Inbox inbox;
	private ConnectionManager connection;
	private boolean emergencyStopDetected = false;

	public MultipleInboxCommunicationManager(CommunicationIds owner, Configuration conf, int numberOfIncoming) {
		this.owner = owner;
		this.conf = conf;
		this.numberOfIncoming = numberOfIncoming;
		Logger.registerListener(this);
		inbox = new Inbox(owner.name());
		serverPort = this.conf.getServerPortAsInt();
		createServerSocket();
	}

	@Override
	public void initialize() {
		try {
			waitForSocketsToConnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialices the array of connections and creates the server socket and
	 * binds it to the server port
	 */
	private void createServerSocket() {
		connections = new HashMap<CommunicationIds, ConnectionManager>(numberOfIncoming);
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(serverPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Waits for each client to connect
	 * 
	 * @throws IOException
	 *             if the connection had a problem
	 */
	private void waitForSocketsToConnect() throws IOException {
		while (connections.size() < numberOfIncoming) {
			connectClient();
		}
		Logger.println("All conections stablished");
	}

	/**
	 * Manages the connection of a client creates the socket, and calls to
	 * manage the client connection
	 * 
	 * @throws IOException
	 */
	private void connectClient() throws IOException {
		Logger.println(String.format("Waiting for connection %d/%d", connections.size()+1,numberOfIncoming));
		Socket socket = serverSocket.accept();
		Logger.println("new connection established");
		manageNewSocketReceived(socket);
	}

	/**
	 * adds the socket to connections, waits for the peer information, notifies
	 * that that client has connected and enables the connectionManager thread
	 * 
	 * @param socket
	 */
	private void manageNewSocketReceived(Socket socket) {
		ConnectionManager c = new ConnectionManager(socket, this, inbox);
		CommunicationIds s = c.waitTilMessageReceivedAndReturnPeer();
		if (connections.get(s)== null){
			connections.put(s, c);
			Logger.println(String.format("%s connected", s));
			startConnection(c);
			sendConfigurationParameters(s);
			if (emergencyStopDetected && (connections.size() == numberOfIncoming)){
				emergencyStopDetected = false;
				getInbox().add(new Message("ResumeFromDistonnectionEStop", CommunicationIds.MASTER, true,
						CommunicationMessageType.COMMAND, MasterInput.RESTART));
				sendMessage(new Message("StartDisconnectedClient", s, true,
						CommunicationMessageType.START, null));
			}
		}else{
			c.close("Client Already Connected");
		}
	}

	private void sendConfigurationParameters(CommunicationIds id) {
		Message configurationMessage = new Message("InitialConfiguration", id, true, CommunicationMessageType.CONFIGURATION, null);
		for (Attribute atribute : MasterModel.getInstance().getCurrentScadaConfiguration().getAsAttributeList()){
			configurationMessage.addAttribute(atribute);
		}
		sendMessage(configurationMessage);
	}

	/**
	 * Deals with the reconnection of the client using a new executor service so
	 * that there is no wait in this thread until ir reconnects
	 * 
	 * @param commId
	 *            the id of the client disconnected
	 */
	private void reconnectClient(CommunicationIds commId) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Logger.println("Atemtpting to reconnect with "+ commId.name());

		FutureTask<Socket> future = new FutureTask<Socket>(new Callable<Socket>() {

			@Override
			public Socket call() throws Exception {
				Socket newSocket = serverSocket.accept();
				serverSocket.close();
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(serverPort));
				return newSocket;
			}

		});
		executor.execute(future);
		try {
			manageNewSocketReceived(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disables the connectionManager thread, notifies the model of the
	 * disconnection and launches the reconnection of the client
	 */
	@Override
	public void clientDisconnected(CommunicationIds commId) {
		Logger.println(commId.name() + "- disconnected");
		connections.get(commId).disable();
		connections.remove(commId);
		MasterModel.getInstance().setModel(commId, null);
		emergencyStopDetected = true;
		getInbox().add(	new Message("EmergencyFromDisconnection", CommunicationIds.MASTER, true,
				CommunicationMessageType.COMMAND, MasterInput.ESTOP));
		reconnectClient(commId);
	}

	/**
	 * Enables and starts the connectionManager thread
	 * 
	 * @param conn
	 */
	// FIXME: if something happens this will constantly call itself, use the same approach as with the client
	private void startConnection(ConnectionManager conn) {
		try {
			conn.enable();
			conn.start();
		} catch (Exception e) {

			startConnection(conn);
		}
	}

	@Override
	public Inbox getInbox() {
		return inbox;
	}

	@Override
	public void sendMessage(Message message) {
		if ((message.getDestination() == null) || (CommunicationIds.BROADCAST == message.getDestination())) {
			for (ConnectionManager conn : connections.values()) {
				conn.writeMessage(message);
			}
		} else {
			connections.get(message.getDestination()).writeMessage(message);
		}
	}

	/**
	 * Method to enable this server implementation of a communication manager to
	 * be the client, tryes to connect to the specified adderes and port
	 * 
	 * Main use is to connect to the possible independent SCADA node
	 * 
	 * @param address
	 *            the address to connect to
	 * @param port
	 *            the port to connect to
	 */
	public void createClientConnectionTo(String address, int port) {
		Socket socket;
		try {
			socket = new Socket(address, serverPort);
			connection = new ConnectionManager(socket, this, inbox);
			connection.writeMessage(new Message(owner + ".CONNECT", null, false, null, null));
		} catch (Exception e) {
			Logger.println(String.format("Error connecting to server at %s:%s %s", address, port, e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}

	@Override
	public CommunicationIds getOwner() {
		return owner;
	}

	@Override
	public void feed(Message message) {
		inbox.add(message);
	}

	@Override
	public void print(String string) {
		MasterModel.getInstance().putTextInBuffer(CommunicationIds.MASTER, string);
	}

	@Override
	public void println(String text) {
		MasterModel.getInstance().putTextInBuffer(CommunicationIds.MASTER, text+"\n");
	}

	@Override
	public void setDisconnectInProgress(boolean disconnectInProgress) {
		// No disconnection for master
	}

}
