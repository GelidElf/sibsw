package core.sections.AssembyStation.States;

public class Finished extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 5909329994058433092L;

	@Override
	public AutomataStateAssemblyStation estop() {
		return createState(StopFinished.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation nstop() {
		return createState(StopFinished.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation apDetectedFalse() {
		return createState(Idle.class, this);
	}
	
}
