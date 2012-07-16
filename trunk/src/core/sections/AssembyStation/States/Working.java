package core.sections.AssembyStation.States;

public class Working extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 3849934426058421877L;

	@Override
	public AutomataStateAssemblyStation estop() {
		return createState(StopWorking.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation nstop() {
		return createState(StopWorking.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation apDetectedTrue() {
		return createState(Finished.class, this);
	}
	
}
