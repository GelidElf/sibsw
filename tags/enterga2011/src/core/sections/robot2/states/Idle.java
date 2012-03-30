package core.sections.robot2.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class Idle extends Robot2State{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public AutomataState Store(){
		return createState("OK",this);
	}
	
	@Override
	public AutomataState Trash(){
		return createState("Trash",this);
	}
	
	@Override
	public AutomataState EStop(){
		return createState("Stop",this);
	}
	
	@Override
	public AutomataState Load_QCS(){
		return createState("QCS",this);
	}
	
	@Override
	public AutomataState Load_WS(){
		return createState("WS",this);
	}
	
	

}
