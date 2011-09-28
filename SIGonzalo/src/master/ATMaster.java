package master;

import core.aplication.Configuration;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.MultipleInboxCommunicationManager;
import core.model.AutomataContainer;

public class ATMaster extends AutomataContainer {

	private CommunicationManager commManager;
	private static final int NUMBEROFINBOXES = 2;

	public ATMaster(Configuration conf) {
		super(conf,new MultipleInboxCommunicationManager("MASTER",conf,NUMBEROFINBOXES));
		//send message to start process
	}

	@Override
	public void run (){
		while (true){
			try{
				Message m = commManager.readMessage();
				System.out.println(m.getID().toString());
			}catch (Exception e){
				
			}
		}
	}
	
}
