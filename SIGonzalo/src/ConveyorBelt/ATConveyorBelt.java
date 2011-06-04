package ConveyorBelt;
import java.util.Random;

import ConveyorBelt.States.AutomataStateCB;
import ConveyorBelt.States.Idle;
import ParallelPort.*;
import ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends Thread implements ParallelPortObserver{
	
	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	
	public ATConveyorBelt(){
		manager = new ConveyorBeltManager();
	}

	@Override
	public void update(ParallelPortState state) {
		manager.setState(state);		
	}
	
	public void run(){
		while(true){
			if(currentState == null){
				currentState = Idle.getInstance();
			}
			try {
				if(manager.getValueByName(ConveyorBeltManager.SENSOR_INITIAL) == 0){
					if (rand.nextBoolean()){
						manager.setValueByName(ConveyorBeltManager.SENSOR_INITIAL, 1);
						manager.update();
					}
				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			currentState = currentState.estop();
		}
	}
	
	
	
	

}
