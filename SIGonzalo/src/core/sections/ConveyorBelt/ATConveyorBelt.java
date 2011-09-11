package core.sections.ConveyorBelt;
import java.util.Random;

import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ConveyorBelt.States.Idle;
import core.sections.ParallelPort.*;
import core.sections.ParallelPort.Utils.ParallelPortException;


public class ATConveyorBelt extends Thread implements ParallelPortObserver{
	
	private ConveyorBeltManager _manager = null;
	private AutomataStateCB currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	private CommunicationManager commManager= null;
	
	
	public ATConveyorBelt(){
		_manager = new ConveyorBeltManager();
		//TODO: hay que quitar este commManager
		commManager = new CommunicationManager(false,"ATCB");
	}

	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);		
	}
	
	public void run(){
		Message mes = commManager.getInboxByName("unknown").getMessage();
		String speed = mes.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = mes.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			_manager.setValueByName(ConveyorBeltManager.VELOCITY, Integer.parseInt(speed));
			_manager.setValueByName(ConveyorBeltManager.CAPACITY, Integer.parseInt(capacity));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
			}
			try {
				if(_manager.getValueByName(ConveyorBeltManager.SENSOR_INITIAL) == 0){
					if (rand.nextBoolean()){
						_manager.setValueByName(ConveyorBeltManager.SENSOR_INITIAL, 1);
					}
				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			currentState = currentState.estop();
		}
	}
	
	public boolean isReady(){
		boolean op = false;
		try {
			if(_manager.getValueByName(ConveyorBeltManager.SENSOR_FINISH)==1){
				op = true;
			}
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}
	
	public ConveyorBeltManager getManager(){
		return _manager;
	}
	
	public static void main (String[] args){
		ParallelPortState state = new ParallelPortState();
		ATConveyorBelt atcb = new ATConveyorBelt();
		atcb._manager.setState(state);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator();
		cbs.getManager().setState(state);
		atcb.run();
		cbs.run();
	}
	
	

}
