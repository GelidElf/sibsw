package core.sections.ConveyorBelt.States;


public class Stop extends AutomataStateCB {

	private static final long serialVersionUID = 4853069318562690881L;

	@Override
	public AutomataStateCB restart() {
		return createState(Running.class, this);
	}

}
