package core.messages;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.acl.Owner;
import java.util.HashMap;

import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;

public class MultipleInboxCommunicationManager implements CommunicationManager{

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

	private void setUpVariablesForMaster(){
		connections = new HashMap<CommunicationIds,ConnectionManager>(numberOfIncoming);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void waitForSocketsToConnect() throws IOException {
		while (connections.size() < numberOfIncoming){
			System.out.println(String.format("Waiting for connection n1 %d", connections.size()));
			Socket newSocket = serverSocket.accept();
			serverSocket.close();
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
			System.out.println("new connection established");
			ConnectionManager c = new ConnectionManager(newSocket, this, inbox);
			CommunicationIds s = c.getPeer();
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
			connection=new ConnectionManager(socket,owner,inbox);
			connection.writeMessage(new Message(owner+".CONNECT",null,false,null,null));
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
	public CommunicationIds getOwner() {
		return owner;
	}
	
}
