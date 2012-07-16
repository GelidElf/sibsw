package core.sections.AssembyStation.States;

public class GearLodaded extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 8040033532951788293L;

	@Override
	public AutomataStateAssemblyStation axisDetectedFalse() {
		return createState(Idle.class,this);
	}
	
	@Override
	public AutomataStateAssemblyStation axisDetectedTrue() {
		return createState(PiecesReady.class,this);
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
