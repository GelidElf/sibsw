package core.messages;

import java.util.LinkedList;

public class Inbox {

	private final LinkedList<Message> inbox = new LinkedList<Message>();
	private final LinkedList<Message> priorityInbox = new LinkedList<Message>();
	
	
	public Inbox(){}
	
	public Inbox(boolean isMaster){
		
	}
	
	public synchronized void add(Message message){
		if (message.isUrgent())
			priorityInbox.addLast(message);
		else
			inbox.addLast(message);
		
	}
	
	public synchronized Message getMessage(){
		Message message = null;
		if (priorityInbox.size() > 0){
			message = priorityInbox.getFirst();
		}
		else if (inbox.size() > 0){
			message = inbox.getFirst();
		}
		return message;
	}
	
	
}
