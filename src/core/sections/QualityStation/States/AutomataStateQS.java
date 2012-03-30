package core.sections.QualityStation.States;

public interface AutomataStateQS {

	public abstract AutomataStateQS estop();
	public abstract AutomataStateQS restart();
	public abstract AutomataStateQS wpDetected();
	public abstract AutomataStateQS slave3_R2_Idle();
	public abstract AutomataStateQS valid();
	public abstract AutomataStateQS invalid();
	public abstract AutomataStateQS wpNotDetected();	
	
}
