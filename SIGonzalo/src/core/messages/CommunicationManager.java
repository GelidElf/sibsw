	package core.messages;

import java.net.ServerSocket;
import java.net.Socket;

import core.aplication.Configuration;

public abstract class CommunicationManager {
	
	protected static final int DATAGRAM_LENGTH = 20000;
	protected static final int DEFAULT_PORT = 40000;
	protected ServerSocket serverSocket = null;
	protected Socket _socket = null;
	protected int serverPort = -1;
	protected Configuration conf;
	protected String ID;
	
	public CommunicationManager(String ID) {
		this.ID = ID;
		initializeVariables();
		initialize();
	}

	public CommunicationManager(String ID, Configuration conf) {
		this.ID = ID;
		this.conf = conf;
	}
	
	protected abstract void initializeVariables();
	protected abstract void initialize();
	public abstract void sendMessage(Message message);
	public abstract Inbox getInboxByName(String commName);
}
