package core.messages;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import core.aplication.Configuration;

public class SingleInboxConnectionManager extends CommunicationManager{

	private ConnectionManager connection;
	private Socket socket;
	private String address;
	private int serverPort;
	
	public SingleInboxConnectionManager(String ID, Configuration conf) {
		super(ID, conf);
	}

	@Override
	public Inbox getInboxByName(String commName) {
		return connection.getInbox();
	}

	@Override
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
		connection=new ConnectionManager(socket,ID);
	}


	@Override
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
	
}
