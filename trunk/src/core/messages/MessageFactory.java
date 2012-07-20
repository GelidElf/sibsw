package core.messages;

import core.messages.enums.CommunicationIds;

public class MessageFactory {
	
	public static final String SEPARATOR = "#$#";

	public class MasterAtomataMessageFactory{
		
		public static final String ID = "MASTER";
		public static final String BROADCAST_MESSAGE = ID + SEPARATOR + "BROADCAST_MESSAGE";
		public static final String SERVER_PORT = "SERVER_PORT";
		
	}
	
	public class GeneralMessageFactory{

		public static final String RESTART = "RESTART";
		public static final String NSTOP = "NSTOP";
		public static final String ESTOP = "ESTOP";
		
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
	
	
}
