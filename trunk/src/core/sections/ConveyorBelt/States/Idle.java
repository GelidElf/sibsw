package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class Idle extends AutomataStateCB {

	private static final long serialVersionUID = -3042264608258209787L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataStateCB estop() {
		return createState(Stop.class, this);
	}

	@Override
	public AutomataStateCB nstop() {
		return createState(Stop.class, this);
	}

	@Override
	public AutomataStateCB loadSensorTrue() {
		return createState(Running.class, this);
	}

}
