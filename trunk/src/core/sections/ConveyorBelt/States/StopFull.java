package core.sections.ConveyorBelt.States;


public class StopFull extends AutomataStateCB{
	
	private static final long serialVersionUID = -9099799405325292141L;

	@Override
	public AutomataStateCB restart() {
		return createState(Full.class,this);
	}


}

