package core.messages;

import java.net.Socket;

import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

/**
 * Client communication manager, created for all the slaves
 * 
 * tries to connect to the server, and when connection is lost, tries to
 * reconnect
 * 
 * @author GelidElf
 * 
 */
public class SingleInboxCommunicationManager implements CommunicationManager{

	private static final int MAX_NUMBER_OF_CONNECTION_ATTEMPTS = 3;
	private CommunicationIds owner;
	private ConnectionManager connection;
	private Socket socket;
	private String address;
	private int serverPort;
	private Configuration conf;
	private Inbox inbox;
	private boolean connected = false;
	private int numberOfAttempts = 0;
	private boolean disconnectInProgress = false;
	private boolean disconnectionOcurred = false;

	public SingleInboxCommunicationManager(CommunicationIds owner, Configuration conf) {
		this.owner = owner;
		this.conf = conf;
	}

	@Override
	public Inbox getInbox() {
		return connection.getInbox();
	}

	@Override
	public void sendMessage(Message message) {
		connection.writeMessage(message);
	}

	private void initializeVariables() {
		this.inbox = new Inbox(owner.name());
		loadConfiguration();
	}

	private void loadConfiguration() {
		address = conf.address;
		serverPort = conf.getRemotePortAsInt();
	}

	private void connectAndStartThread() {
		while (!disconnectInProgress && !connected) {
			if (tryToConnectToServer()) {
				Logger.registerListener(this);
				Logger.println("Connection achieved");
				if (disconnectionOcurred){
					disconnectionOcurred = false;
					inbox.add(new Message("RECOONECTION_RESUME",owner,true,CommunicationMessageType.RESTART,null));
				}
				connection.setPeer(CommunicationIds.MASTER);
				connection.start();
			} else {
				Logger.println("Unable to connect to server, retrying");
				numberOfAttempts = 0;
				sleepSeconds(5);
			}
		}
	}

	private boolean tryToConnectToServer() {
		Logger.println("Trying to connect to server");
		numberOfAttempts = 0;
		while (!disconnectInProgress && keepTryingToConnect()) {
			try {
				socket = new Socket(address, serverPort);
				socket.setTcpNoDelay(true);
				weHaveConnection();
				connection = new ConnectionManager(socket, this, inbox);
				connection.enable();
				connection.writeMessage(new Message("CONNECT", CommunicationIds.MASTER, false, CommunicationMessageType.HANDSHAKE, null));
				return true;
			} catch (Exception e) {
				Logger.println(String.format("Error connecting to server at %s:%s %s", address, serverPort, e.getMessage()));
				sleepSeconds(numberOfAttempts);
			} finally {
				numberOfAttempts++;
			}
		}
		return false;
	}

	private void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds + 1000);
		} catch (InterruptedException e1) {
			Logger.println("Error trying to sleep connection thread");
		}
	}

	private void weHaveConnection() {
		connected = true;
	}

	private boolean keepTryingToConnect() {
		return !connected && (numberOfAttempts < MAX_NUMBER_OF_CONNECTION_ATTEMPTS);
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
	public void initialize() {
		initializeVariables();
		connectAndStartThread();
	}

	@Override
	public void feed(Message message) {
		inbox.add(message);
	}

	@Override
	public void clientDisconnected(CommunicationIds commId) {
		connected = false;
		connection.disable();
		Logger.unregisterListener(this);
		Logger.println("Client has disconnected.");
		disconnectionOcurred  = true;
		inbox.add(new Message("DISCONNECTED_EMERGENCY_STOP",owner,true,CommunicationMessageType.ESTOP,null));
		connectAndStartThread();
	}

	@Override
	public void print(String string) {
		Message message = new Message("Logger", CommunicationIds.MASTER, false, CommunicationMessageType.LOG_MESSAGE, null);
		message.addAttribute("MESSAGE",string);
		sendMessage(message);

	}

	@Override
	public void println(String text) {
		Message message = new Message("Logger", CommunicationIds.MASTER, false, CommunicationMessageType.LOG_MESSAGE, null);
		message.addAttribute("MESSAGE",text+"\n");
		sendMessage(message);
	}

	public void setDisconnectInProgress(boolean disconnectInProgress) {
		this.disconnectInProgress = disconnectInProgress;
	}

	public boolean isDisconnectInProgress() {
		return disconnectInProgress;
	}

}
