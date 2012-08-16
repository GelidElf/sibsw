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

import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;
import core.model.MasterModel;

public class MultipleInboxCommunicationManager implements CommunicationManager {

	private HashMap<CommunicationIds, ConnectionManager> connections = new HashMap<CommunicationIds, ConnectionManager>();
	private int numberOfIncoming;
	private CommunicationIds owner;
	private Configuration conf;
	private int serverPort;
	private ServerSocket serverSocket;
	private Inbox inbox;
	private ConnectionManager connection;

	public MultipleInboxCommunicationManager(CommunicationIds owner, Configuration conf, int numberOfIncoming) {
		this.owner = owner;
		this.conf = conf;
		this.numberOfIncoming = numberOfIncoming;
		inbox = new Inbox();
		loadConfigurationFromFonfiguration();
		setUpVariablesForMaster();
	}

	@Override
	public void initialize() {
		try {
			waitForSocketsToConnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadConfigurationFromFonfiguration() {
		serverPort = conf.getServerPortAsInt();
	}

	private void setUpVariablesForMaster() {
		connections = new HashMap<CommunicationIds, ConnectionManager>(numberOfIncoming);
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(serverPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitForSocketsToConnect() throws IOException {
		while (connections.size() < numberOfIncoming) {
			connectClient();
		}
		System.out.println("All conections stablished");
	}

	private void connectClient() throws IOException {
		System.out.println(String.format("Waiting for connection n1 %d", connections.size()));
		Socket socket = serverSocket.accept();
		serverSocket.close();
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(serverPort));
		System.out.println("new connection established");
		manageNewSocketReceived(socket);
	}

	private void reconnectClient(CommunicationIds commId) {
		ExecutorService executor = Executors.newFixedThreadPool(1);

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

	private void manageNewSocketReceived(Socket socket) {
		ConnectionManager c = new ConnectionManager(socket, this, inbox);
		CommunicationIds s = c.waitTilMessageReceivedAndReturnPeer();
		connections.put(s, c);
		System.out.println(String.format("%s connected", s));
		c.enable();
		startConnection(c);
	}

	public void clientDisconnected(CommunicationIds commId) {
		connections.get(commId).disable();
		MasterModel.getInstance().setModel(commId, null);
		//TODO: Must inform that the client was disconnected
		reconnectClient(commId);
	}

	private void startConnection(ConnectionManager conn) {
		try {
			conn.start();
		} catch (Exception e) {
			startConnection(conn);
		}
	}

	@Override
	public Inbox getInbox() {
		return inbox;
	}

	public void sendMessage(Message message) {
		if ((message.getDestination() == null) || (CommunicationIds.BROADCAST == message.getDestination())) {
			for (ConnectionManager conn : connections.values()) {
				conn.writeMessage(message);
			}
		} else {
			connections.get(message.getDestination()).writeMessage(message);
		}
	}

	public void createClientConnectionTo(String address, int port) {
		Socket socket;
		try {
			socket = new Socket(address, serverPort);
			connection = new ConnectionManager(socket, this, inbox);
			connection.writeMessage(new Message(owner + ".CONNECT", null, false, null, null));
		} catch (Exception e) {
			System.out.println(String.format("Error connecting to server at %s:%s %s", address, port, e.getMessage()));
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

}
