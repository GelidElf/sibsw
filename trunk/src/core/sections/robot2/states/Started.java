package core.sections.robot2.states;

import core.model.AutomataState;
public class Started extends Robot2State{
	
	private static final long serialVersionUID = -7859112230362585164L;

	@Override
	public AutomataState Start(){
		return createState(Idle.class,this);
	}


}
