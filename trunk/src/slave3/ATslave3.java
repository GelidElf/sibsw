package slave3;

import slave3.states.IdleQCSEmpty;
import slave3.states.Slave3State;
import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.SingleInboxCommunicationManager;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.QualityStation.ATQualityStation;
import core.sections.QualityStation.QualityStationManager;

public class ATslave3 extends AutomataContainer {

	private ATConveyorBelt conveyorBelt;
	private ATQualityStation qualityStation;
	private Slave3State currentState;
	private Message messageToSend;
	
	public ATslave3(Configuration conf){
		super(conf, new SingleInboxCommunicationManager("Slave3",conf));
		currentState = (Slave3State) Slave3State.createState(IdleQCSEmpty.class, currentState);
	}	
	
	public void setATConveyorbelt(ATConveyorBelt cb){
		conveyorBelt = cb;
	}
	
	public void setATQualityStation(ATQualityStation qcs){
		qualityStation = qcs;
	}
	
	/**@Override
	//TODO Hacer la parte del START
	public void run() {
		super.run();
		while(true){
			Message message = commManager.readMessage();
			String id = message.getID();
			String owner = message.getOwner();
			if(id.equals("Slave3.NormalStop")){
				currentState = (Slave3State) currentState.NStop();
			}
			if(id.equals("Slave3.EmergencyStop")){
				currentState = (Slave3State) currentState.EStop();
			}
			if(id.equals("Slave.Restart")){
				currentState = (Slave3State) currentState.Restart();
			}
			if(id.equals("Slave3.NewParameters")){
				conveyorBelt.setupParametersFromMessage(message);
				qualityStation.setupParametersFromMessage(message);
			}
			if(owner.equals("Master")){
				Attribute action = message.getAttribute("action");
				if(action.getValue().equals("R2Idle")){
					currentState = (Slave3State) currentState.R2Idle();
					try {
						qualityStation.getManager().setBitGroupValue(QualityStationManager.SENSOR, 0);
					} catch (ParallelPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(action.getValue().equals("LOAD_QCS")){
					currentState = (Slave3State) currentState.LoadQCS();
					try {
						qualityStation.getManager().setBitGroupValue(QualityStationManager.SENSOR, 1);
					} catch (ParallelPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(action.getValue().equals("START")){
					currentState = (Slave3State) currentState.Start();
				}
			
			}
			if(id.equals("QCS.AssemblyInvalid")){
				currentState = (Slave3State) currentState.Invalid();
				messageToSend = new Message("Slave3.Invalid","MASTER",false);
				commManager.sendMessage(messageToSend);
			}
			if(id.equals("QCS.AssemblyValid")){
				currentState = (Slave3State) currentState.Valid();
				messageToSend = new Message("Slave3.Valid","MASTER",false);
				commManager.sendMessage(messageToSend);
				try {
					if(conveyorBelt.isFirstPositionEmpty() == true){
						messageToSend = new Message("Slave3.CBReadyToPlaceWP","MASTER",false);
						commManager.sendMessage(messageToSend);
						currentState = (Slave3State) currentState.NotFull();
						
					}
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		
	}*/



		
}
	
