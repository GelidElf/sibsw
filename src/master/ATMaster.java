package master;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.MultipleInboxCommunicationManager;
import core.model.AutomataContainer;

public class ATMaster extends AutomataContainer<ATMasterInput> {

	private CommunicationManager commManager;
	private static final int NUMBEROFINBOXES = 2;

	public ATMaster(Configuration conf) {
		super(null);
		//super(conf,new MultipleInboxCommunicationManager("MASTER",conf,NUMBEROFINBOXES));
		//send message to start process
	}

	@Override
	protected void consume(ATMasterInput currentInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}

	/**
	public void run (){
		while (true){
			try{
				Message m = commManager.readMessage();
				System.out.println(m.getID().toString());
			}catch (Exception e){
				
			}
		}
	}*/
	
}
