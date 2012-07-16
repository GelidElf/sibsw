package core.sections.robot2.states;

import core.model.AutomataState;

public class Idle extends Robot2State{

	private static final long serialVersionUID = 1L;
	
	@Override
	public AutomataState Store(){
		return createState(Ok.class,this);
	}
	
	@Override
	public AutomataState Trash(){
		return createState(Trash.class,this);
	}
	
	@Override
	public AutomataState EStop(){
		return createState(Stop.class,this);
	}
	
	@Override
	public AutomataState Load_QCS(){
		return createState(QCS.class,this);
	}
	
	@Override
	public AutomataState Load_WS(){
		return createState(WS.class,this);
	}
	
	

}
