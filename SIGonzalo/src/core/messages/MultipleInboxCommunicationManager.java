package core.messages;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import core.aplication.Configuration;

public class MultipleInboxCommunicationManager extends CommunicationManager{

	private HashMap<String, ConnectionManager> connections = new HashMap<String, ConnectionManager>();
	private int numberOfInboxes;
	
	public MultipleInboxCommunicationManager(String ID, Configuration conf, int numberOfInboxes) {
		super(ID, conf);
		this.numberOfInboxes = numberOfInboxes;
		// TODO Auto-generated constructor stub
	}

	@Override
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
		connections = new HashMap<String,ConnectionManager>(numberOfInboxes);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void waitForSocketsToConnect() throws IOException {
		while (connections.size() < numberOfInboxes){
			Socket newSocket = serverSocket.accept();
			ConnectionManager c = new ConnectionManager(newSocket, this);
			String s = c.getOwner();
			connections.put(s, c);
			connections.get(connections.size()-1).run();
		}
	}

	@Override
	protected void initializeVariables() {
		// TODO Auto-generated method stub
		
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

	
	
}
