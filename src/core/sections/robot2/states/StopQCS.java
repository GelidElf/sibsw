package core.sections.robot2.states;

import core.model.AutomataState;

public class StopQCS extends Robot2State{
	
	private static final long serialVersionUID = -7761199814735072334L;

	@Override
	public AutomataState Restart(){
		return createState(QCS.class,this);
	}
	

}
