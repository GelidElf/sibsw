package core.messages;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import core.aplication.Configuration;

public class SingleInboxCommunicationManager extends CommunicationManager{

	private ConnectionManager connection;
	private Socket socket;
	private String address;
	private int serverPort;
	private String applicationID;
	private Configuration conf;
	
	public SingleInboxCommunicationManager(String ID, Configuration conf) {
		this.applicationID = ID;
		this.conf = conf;
		initializeVariables();
		initialize();
	}

	@Override
	public Inbox getInboxByName(String commName) {
		return connection.getInbox();
	}

	protected void initialize() {
		tryToConnectToServer();
		connection.run();
	}

	private void tryToConnectToServer() {
		socket = new Socket();
		InetAddress remoteAddress = null;
		try {
			remoteAddress = InetAddress.getByName(address);
			socket.bind(new InetSocketAddress(remoteAddress,serverPort));
		} catch (Exception e) {
			System.out.println(String.format("Error connecting to server at %s:%s %s",address,serverPort,e.getMessage()));
			e.printStackTrace();
		}
		connection=new ConnectionManager(socket,applicationID);
	}


	protected void initializeVariables() {
		loadConfiguration();
	}

	private void loadConfiguration() {
		address = conf.address;
		serverPort = conf.getServerPortAsInt();
	}

	@Override
	public void sendMessage(Message message) {
		connection.writeMessage(message);
	}

	public Inbox getInboxByName() {
		return connection.getInbox();
	}

	public Message readMessage() {
		return connection.readMessage();
		
	}
	
	
}
