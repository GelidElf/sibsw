package slave3;

import slave3.states.Slave3State;
import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.SingleInboxCommunicationManager;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.QualityStation.ATQualityStation;

public class ATslave3 extends AutomataContainer {

	private ATConveyorBelt conveyorBelt;
	private ATQualityStation qualityStation;
	private SingleInboxCommunicationManager commManager;
	private Slave3State currentState;
	private String reaction;
	
	public ATslave3(Configuration conf){
		super(conf);
		commManager = new SingleInboxCommunicationManager("Slave3",conf);
		currentState = (Slave3State) Slave3State.createState("Idle", currentState);
	}
	
	
	public void setATConveyorbelt(ATConveyorBelt cb){
		conveyorBelt = cb;
	}
	
	public void setATQualityStation (ATQualityStation qcs){
		qualityStation = qcs;
	}
	
	@Override
	public void run() {
		super.run();
		while(true){
			commManager.getInboxByName();
			Message message = commManager.readMessage();
			String id = message.getID();
			String owner = message.getOwner();
			if(id.equals("normalStop")){
				currentState = (Slave3State) currentState.NStop();
			}
			if(id.equals("emergencyStop")){
				currentState = (Slave3State) currentState.EStop();
			}
			if(id.equals("newParameters")){
				conveyorBelt.setupParametersFromMessage(message);
				qualityStation.setupParametersFromMessage(message);
			}
			if(owner.equals("master")){
				Attribute action = message.getAttribute("action");
				if(action.getValue().equals("R2Idle")){
					currentState = (Slave3State) currentState.R2Idle();
				}
				if(action.getValue().equals("LOAD_QCS")){
					currentState = (Slave3State) currentState.LoadQCS();
				}
				if(action.getValue().equals("START")){
					currentState = (Slave3State) currentState.Start();
				}
			
			}
			if(reaction.equals("QCS_INVALID")){
				currentState = (Slave3State) currentState.Invalid();
				//mandar mensaje a master "Trashing" (seguir esquema del automata)
			}
			
		}
		
		
		
	}



		
}
	
