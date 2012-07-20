package core.messages;

import java.net.Socket;

import core.aplication.Configuration;
import core.messages.enums.CommunicationIds;

public class SingleInboxCommunicationManager implements CommunicationManager{

	private CommunicationIds owner;
	private ConnectionManager connection;
	private Socket socket;
	private String address;
	private int serverPort;
	private Configuration conf;
	private Inbox inbox;
	
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
			connection=new ConnectionManager(socket,owner,inbox);
			connection.writeMessage(new Message("CONNECT",CommunicationIds.MASTER,false,null,null));
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
	public CommunicationIds getOwner() {
		return owner;
	}

	@Override
	public void initialize() {
		initializeVariables();
		connectAndStartThread();
	}
	
}
