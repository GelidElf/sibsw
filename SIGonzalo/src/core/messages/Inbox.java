package core.messages;

import java.util.LinkedList;

public class Inbox {

	private LinkedList<Message> _inbox = null;
	private LinkedList<Message> _priorityInbox = null;
	
	
	public Inbox(){}
	
	public Inbox(boolean isMaster){
		
	}
	
	public synchronized void add(Message message){
		if (message.isUrgent())
			_priorityInbox.add(message);
		else
			_inbox.add(message);
		
	}
	
	public synchronized Message getMessage(){
		if (_priorityInbox.size() >= 0){
			_inbox.remove(_priorityInbox.getFirst());
			return _priorityInbox.getFirst();
		}else{
			return _inbox.getFirst();
		}
	}
	
	
}
