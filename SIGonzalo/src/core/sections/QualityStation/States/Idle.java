package core.sections.QualityStation.States;

public class Idle  implements AutomataStateQS{
	
	static AutomataStateQS instance = null;
	
	public static AutomataStateQS getInstance(){
		if (instance == null){
			instance = new Idle();
		}
		return instance;
	}
	
	private Idle(){
		
	}


	@Override
	public AutomataStateQS estop() {
		// TODO Auto-generated method stub
		return Stop.getInstance();
	}

	@Override
	public AutomataStateQS restart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateQS wpDetected() {
		// TODO Auto-generated method stub
		return Ready.getInstance();
	}

	@Override
	public AutomataStateQS slave3_R2_Idle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateQS valid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateQS invalid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateQS wpNotDetected() {
		// TODO Auto-generated method stub
		return Idle.getInstance();
	}


}
