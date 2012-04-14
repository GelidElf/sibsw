package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class OccupiedStop extends AutomataStateCB{

	private static final long serialVersionUID = -9197305477014332293L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB restart() {
		return createState(IdleUnloadingLoadOccupied.class,this);
	}


}

