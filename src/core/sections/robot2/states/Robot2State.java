package core.sections.robot2.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public abstract class Robot2State extends AutomataState {
	
	private static final long serialVersionUID = -170786020874169763L;


	public static AutomataState createState(Class <? extends Robot2State> targetClass, AutomataState currentState) {
		return createState(targetClass,currentState,Robot2State.class);
	}
	
	public AutomataState Start(){
		return this;
	}
	public AutomataState EStop(){
		return this;
	}
	public AutomataState NStop(){
		return this;
	}
	public AutomataState Restart(){
		return this;
	}
	public AutomataState Completed(){
		return this;
	}
	public AutomataState Load_WS(){
		return this;
	}
	public AutomataState Store(){
		return this;
	}
	public AutomataState Trash() {
		// TODO Auto-generated method stub
		return null;
	}
	public AutomataState Load_QCS() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}

}

