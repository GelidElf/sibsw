package core.sections.QualityStation;

import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton3MessageFactory;
import core.messages.enums.CommunicationIds;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.QualityStation.States.AutomataStateQS;
import core.sections.QualityStation.States.Idle;

public class ATQualityStation extends Thread implements ParallelPortManagerObserver{

	private QualityStationManager manager = null;
	private AutomataStateQS currentState = null;
	private AutomataContainer father = null;
	//private Random rand = new Random(System.currentTimeMillis());
	//private CommunicationManager commManager= null;
	
	public ATQualityStation(AutomataContainer father, QualityStationManager manager){
		this.manager = manager;
		this.father = father;
		//commManager = new CommunicationManager(false,);
	}
	
	public void run(){
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
			}
			if(currentState.getClass().getName().equals("Working")){
				try {
					if(manager.getValueByName(QualityStationManager.ENABLED) == 0){
						if(manager.getValueByName(QualityStationManager.RESULT) == 1){
							currentState = currentState.valid();
							Message message = new Message("QCS.AssemblyValid",CommunicationIds.SLAVE3,false,null,null);
							father.injectMessage(message);
						}else{
							currentState = currentState.invalid();
							Message message = new Message("QCS.AssemblyInvalid",CommunicationIds.SLAVE3,false,null,null);
							father.injectMessage(message);
						}
					}
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentState = currentState.estop();
		}
	}
	
	public QualityStationManager getManager(){
		return manager;
	}
	
	/*public static void main (String[] args){
		ParallelPortState state = new ParallelPortState();
		ATQualityStation atcb = new ATQualityStation();
		atcb._manager.setState(state);
		QualityStationSimulator cbs = new QualityStationSimulator();
		cbs.getManager().setState(state);
		atcb.start();
		cbs.start();
	}*/

	public void setupParametersFromMessage(Message mes) {
		String activationTime = mes.getAttributeValue(SlaveAutomaton3MessageFactory.ACTIVATION_TIME);
		String failurePercentage = mes.getAttributeValue(SlaveAutomaton3MessageFactory.FAILURE_PERCENTAGE);
		try {
			manager.setValueByName(QualityStationManager.ACTIVATION_TIME, Integer.parseInt(activationTime));
			manager.setValueByName(QualityStationManager.FAILURE_PERCENTAGE, Integer.parseInt(failurePercentage));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
