package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopCBReady extends Slave3State {
	
	static Slave3State instance = null;
	
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new StopCBReady();
		}
		return instance;
	}

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState("CBReady",this);
	}

}
