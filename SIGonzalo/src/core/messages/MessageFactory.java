package core.messages;

public class MessageFactory {
	
	public static final String SEPARATOR = "#$#";

	public class MasterAtomataMessageFactory{
		
		public static final String ID = "Master";
		
		public static final String BROADCAST_MESSAGE = ID + SEPARATOR + "BROADCAST_MESSAGE";
		
		
		
		public static final String SERVER_PORT = "SERVER_PORT";
		
	}
	
	public class SlaveAutomaton1MessageFactory{
		
		public static final String ID = "Slave1";		
		public static final String SPEED = "SPEED";
		public static final String CAPACITY = "CAPACITY";
	
	}
	
	public class SlaveAutomaton3MessageFactory{
		
		public static final String ID = "Slave3";		
		public static final String FAILURE_PERCENTAGE = "FAILURE_PERCENTAGE";
		public static final String ACTIVATION_TIME = "ACTIVATION_TIME";
	}
	
	
	public static Message createMessage(String ID){
		return new Message(ID);
	}
}
