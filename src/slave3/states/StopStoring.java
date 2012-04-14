package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopStoring extends Slave3State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1854670985608351091L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState(Storing.class,this);
	}
	


}
