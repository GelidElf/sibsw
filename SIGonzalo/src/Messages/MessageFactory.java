package Messages;

public class MessageFactory {
	
	public static final String SEPARATOR = "#$#";

	public class MasterAtomataMessageFactory{
		
		public static final String ID = "Master";
		
		public static final String BROADCAST_MESSAGE = ID+SEPARATOR+"BROADCAST_MESSAGE";
		
		
		
		public static final String SERVER_PORT = "SERVER_PORT";
		
	}
	
	public class SlaveAutomaton1MessageFactory{
		
		public static final String ID = "Slave1";
		
		public static final String BROADCAST_MESSAGE = ID+SEPARATOR+"BROADCAST_MESSAGE";
	
	}
	
	
	
	public static Message createMessage(String ID){
		return new Message(ID);
	}
}
