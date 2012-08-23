package core.messages;

import java.net.Socket;

import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

/**
 * Client communication manager, created for all the slaves
 * 
 * tries to connect to the server, and when connection is lost, tryes to
 * reconnect
 * 
 * @author GelidElf
 * 
 */
public class SingleInboxCommunicationManager implements CommunicationManager {

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
		this.inbox = new Inbox();
		loadConfiguration();
	}

	private void loadConfiguration() {
		address = conf.address;
		serverPort = conf.getRemotePortAsInt();
	}

	private void connectAndStartThread() {
		while (!connected) {
			if (tryToConnectToServer()) {
				connection.setPeer(CommunicationIds.MASTER);
				connection.start();
				Logger.println("Connection achieved");
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
		while (keepTryingToConnect()) {
			try {
				socket = new Socket(address, serverPort);
				weHaveConnection();
				socket.setTcpNoDelay(true);
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
		connectAndStartThread();
	}

}
