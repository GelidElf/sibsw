package core.sections.QualityStation;

import java.util.Random;

import slave3.states.Slave3State;

import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.messages.MessageFactory.SlaveAutomaton3MessageFactory;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.QualityStation.States.AutomataStateQS;
import core.sections.QualityStation.States.Idle;

public class ATQualityStation extends Thread implements ParallelPortManagerObserver{

	private QualityStationManager _manager = null;
	private AutomataStateQS currentState = null;
	private AutomataContainer father = null;
	//private Random rand = new Random(System.currentTimeMillis());
	//private CommunicationManager commManager= null;
	
	public ATQualityStation(AutomataContainer father){
		_manager = new QualityStationManager();
		this.father = father;
		//commManager = new CommunicationManager(false,);
	}
	
	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);	
	}
	
	public void run(){
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
			}
			if(currentState.getClass().getName().equals("Working")){
				try {
					if(_manager.getValueByName(QualityStationManager.ENABLED) == 0){
						if(_manager.getValueByName(QualityStationManager.RESULT) == 1){
							currentState = currentState.valid();
							Message message = new Message("QCS.AssemblyValid","MASTER",false);
							father.injectMessage(message);
						}else{
							currentState = currentState.invalid();
							Message message = new Message("QCS.AssemblyInvalid","MASTER",false);
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
		return _manager;
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

	@Override
	public void setParallelPortState(ParallelPortState state) {
		_manager.setState(state);
	}
	
	public void setupParametersFromMessage(Message mes) {
		String activationTime = mes.getAttributeValue(SlaveAutomaton3MessageFactory.ACTIVATION_TIME);
		String failurePercentage = mes.getAttributeValue(SlaveAutomaton3MessageFactory.FAILURE_PERCENTAGE);
		try {
			_manager.setValueByName(QualityStationManager.ACTIVATION_TIME, Integer.parseInt(activationTime));
			_manager.setValueByName(QualityStationManager.FAILURE_PERCENTAGE, Integer.parseInt(failurePercentage));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
}
