package core.sections.AssembyStation.States;

public class AxisLoaded extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 2437928529777038489L;

	@Override
	public AutomataStateAssemblyStation gearDetectedFalse() {
		return createState(Idle.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation gearDetectedTrue() {
		return createState(PiecesReady.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation estop() {
		return createState(Stop.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation nstop() {
		return createState(Stop.class, this);
	}
}
