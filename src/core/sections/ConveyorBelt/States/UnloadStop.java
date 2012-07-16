package core.sections.ConveyorBelt.States;


public class UnloadStop extends AutomataStateCB{
	
	private static final long serialVersionUID = 1604310742418559089L;

	@Override
	public AutomataStateCB restart() {
		return createState(IdleUnloading.class,this);
	}


}

