package core.messages;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import core.aplication.Configuration;

public class MultipleInboxCommunicationManager extends CommunicationManager{

	private HashMap<String, ConnectionManager> connections = new HashMap<String, ConnectionManager>();
	private int numberOfIncoming;
	private String applicationID;
	private Configuration conf;
	private int serverPort;
	private ServerSocket serverSocket;
	
	public MultipleInboxCommunicationManager(String ID, Configuration conf, int numberOfIncoming) {
		applicationID = ID;
		this.conf = conf;
		this.numberOfIncoming = numberOfIncoming;
	}

	protected void initialize() {
		try {
			loadConfigurationFromFonfiguration();
			setUpVariablesForMaster();
			waitForSocketsToConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadConfigurationFromFonfiguration() {
		serverPort = conf.getServerPortAsInt();
	}

	private void setUpVariablesForMaster(){
		connections = new HashMap<String,ConnectionManager>(numberOfIncoming);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void waitForSocketsToConnect() throws IOException {
		while (connections.size() < numberOfIncoming){
			Socket newSocket = serverSocket.accept();
			ConnectionManager c = new ConnectionManager(newSocket, this);
			String s = c.getOwner();
			connections.put(s, c);
			connections.get(connections.size()-1).run();
		}
	}
	
	@Override
	public Inbox getInboxByName(String commName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	public void createClientConnectionTo(String address, int port){
		ConnectionManager connection = null;
		Socket socket = new Socket();
		InetAddress remoteAddress = null;
		try {
			remoteAddress = InetAddress.getByName(address);
			socket.bind(new InetSocketAddress(remoteAddress,port));
		} catch (Exception e) {
			System.out.println(String.format("Error connecting to server at %s:%s %s",address,port,e.getMessage()));
			e.printStackTrace();
		}
		connection=new ConnectionManager(socket,applicationID);
	}
	
	
}
