package core.messages;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import core.aplication.Configuration;

public class MultipleInboxCommunicationManager implements CommunicationManager{

	private HashMap<String, ConnectionManager> connections = new HashMap<String, ConnectionManager>();
	private int numberOfIncoming;
	private String applicationID;
	private Configuration conf;
	private int serverPort;
	private ServerSocket serverSocket;
	private Inbox inbox;
	private ConnectionManager connection;
	
	public MultipleInboxCommunicationManager(String ID, Configuration conf, int numberOfIncoming) {
		applicationID = ID;
		this.conf = conf;
		this.numberOfIncoming = numberOfIncoming;
		inbox = new Inbox();
		initialize();
	}

	private void initialize() {
		try {
			loadConfigurationFromFonfiguration();
			setUpVariablesForMaster();
			waitForSocketsToConnect(numberOfIncoming);
		} catch (IOException e) {
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

	private void waitForSocketsToConnect(int numberOfIncoming) throws IOException {
		while (connections.size() < numberOfIncoming){
			System.out.println(String.format("Waiting for connection n1 %d", connections.size()));
			Socket newSocket = serverSocket.accept();
			serverSocket.close();
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
			System.out.println("new connection established");
			ConnectionManager c = new ConnectionManager(newSocket, this, inbox);
			String s = c.getNameOfPeer();
			System.out.println(String.format("%s connected",s));
			connections.put(s, c);
			startConnection(connections.get(s));
		}
		System.out.println("All conections stablished");
	}
	
	private void startConnection(ConnectionManager conn) {
		try{
			conn.start();
		}catch(Exception e){
			startConnection(conn);
		}
	}

	@Override
	public Inbox getInbox() {
		return inbox;
	}
	
	@Override
	public Message readMessage(){
		return inbox.getMessage();
	}

	@Override
	public void sendMessage(Message message) {
		if (message.getDestination() != null)
			for (ConnectionManager conn: connections.values()){
				conn.writeMessage(message);
			}
		else
			connections.get(message.getDestination()).writeMessage(message);
	}

	public void createClientConnectionTo(String address, int port){
		Socket socket;
		try {
			socket = new Socket(address, serverPort);
			connection=new ConnectionManager(socket,applicationID,inbox);
			connection.writeMessage(new Message(applicationID+".CONNECT",null,false));
		} catch (Exception e) {
			System.out.println(String.format("Error connecting to server at %s:%s %s",address,port,e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}

	@Override
	public String getApplicationId() {
		return applicationID;
	}
	
	
}
