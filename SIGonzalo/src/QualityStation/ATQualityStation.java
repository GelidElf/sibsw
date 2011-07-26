package QualityStation;

import java.util.Random;
import QualityStation.States.*;
import Messages.CommunicationManager;
import Messages.Message;
import Messages.MessageFactory.SlaveAutomaton1MessageFactory;
import Messages.MessageFactory.SlaveAutomaton3MessageFactory;
import ParallelPort.ParallelPortObserver;
import ParallelPort.ParallelPortState;
import ParallelPort.Utils.ParallelPortException;
import QualityStation.States.AutomataStateQS;

public class ATQualityStation extends Thread implements ParallelPortObserver{

	private QualityStationManager _manager = null;
	private AutomataStateQS currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	private CommunicationManager commManager= null;
	
	public ATQualityStation(){
		_manager = new QualityStationManager();
		commManager = new CommunicationManager(false);
	}
	
	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);	
	}

	public void run(){
		Message mes = commManager.getInbox().getMessage();
		String failurePercentage = mes.getAttributeValue(SlaveAutomaton3MessageFactory.FAILURE_PERCENTAGE);
		String activationTime = mes.getAttributeValue(SlaveAutomaton3MessageFactory.ACTIVATION_TIME);
		try {
			_manager.setValueByName(QualityStationManager.FAILURE_PERCENTAGE, Integer.parseInt(failurePercentage));
			_manager.setValueByName(QualityStationManager.ACTIVATION_TIME, Integer.parseInt(activationTime));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			e1.printStackTrace();
		}
		
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
			}
			if(currentState.getClass().getName().equals("Working")){
				try {
					if(_manager.getValueByName(QualityStationManager.ENABLED) == 0){
						if(_manager.getValueByName(QualityStationManager.RESULT) == 1){
							currentState = currentState.valid();
						}else{
							currentState = currentState.invalid();
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
	
	public static void main (String[] args){
		ParallelPortState state = new ParallelPortState();
		ATQualityStation atcb = new ATQualityStation();
		atcb._manager.setState(state);
		QualityStationSimulator cbs = new QualityStationSimulator();
		cbs.getManager().setState(state);
		atcb.run();
		cbs.run();
	}
	
}
