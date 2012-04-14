package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class Started extends Slave3State {
	
	private static final long serialVersionUID = -1329748406408094832L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Start() {
		return createState(IdleQCSEmpty.class,this);
	}
}
