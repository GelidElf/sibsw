package core.messages;

import java.net.Socket;

import core.aplication.Configuration;

public class SingleInboxCommunicationManager implements CommunicationManager{

	private ConnectionManager connection;
	private Socket socket;
	private String address;
	private int serverPort;
	private String applicationID;
	private Configuration conf;
	private Inbox inbox;
	
	public SingleInboxCommunicationManager(String ID, Configuration conf) {
		this.applicationID = ID;
		this.conf = conf;
		initializeVariables();
		connectAndStartThread();
	}

	@Override
	public Inbox getInbox() {
		return connection.getInbox();
	}

	@Override
	public void sendMessage(Message message) {
		connection.writeMessage(message);
	}
	
	@Override
	public Message readMessage(){
		return connection.readMessage();
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
		tryToConnectToServer();
		try{
			connection.start();
		}catch (Exception e){
			connectAndStartThread();
		}
	}

	private void tryToConnectToServer() {
		try {
			socket = new Socket(address, serverPort);
			connection=new ConnectionManager(socket,applicationID,inbox);
			connection.writeMessage(new Message(applicationID+".CONNECT","MASTER",false));
		} catch (Exception e) {
			System.out.println(String.format("Error connecting to server at %s:%s %s",address,serverPort,e.getMessage()));
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
