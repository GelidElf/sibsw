package core.sections.ConveyorBelt.States;


public class OccupiedStop extends AutomataStateCB{

	private static final long serialVersionUID = -9197305477014332293L;

	@Override
	public AutomataStateCB restart() {
		return createState(IdleUnloadingLoadOccupied.class,this);
	}


}

