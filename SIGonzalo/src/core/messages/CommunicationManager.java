	package core.messages;


public abstract class CommunicationManager {
	
	protected static final int DATAGRAM_LENGTH = 20000;
	protected static final int DEF_SERVER_PORT = 40000;
	
	public abstract void sendMessage(Message message);
	public abstract Inbox getInboxByName(String commName);
}
