	package core.messages;

import core.messages.enums.CommunicationIds;


public interface CommunicationManager {
	
	public static final int DATAGRAM_LENGTH = 20000;
	public static final int DEF_SERVER_PORT = 40000;
	
	public void sendMessage(Message message);
	public Message readMessage();
	public Inbox getInbox();
	public void setInbox(Inbox inbox);
	public CommunicationIds getOwner();
	public void initialize();
}
