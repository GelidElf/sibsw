package core.sections.QualityStation.States;

public class Finished implements AutomataStateQS{
	
	static AutomataStateQS instance = null;
	
	public static AutomataStateQS getInstance(){
		if (instance == null){
			instance = new Finished();
		}
		return instance;
	}
	
	private Finished(){
		
	}

	@Override
	public AutomataStateQS estop() {
		// TODO Auto-generated method stub
		return StopFinished.getInstance();
	}

	@Override
	public AutomataStateQS restart() {	
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateQS wpDetected() {
		// TODO Auto-generated method stub
		return null;
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
