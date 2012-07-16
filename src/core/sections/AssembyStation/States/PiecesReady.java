package core.sections.AssembyStation.States;

public class PiecesReady extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 6470122830778287803L;

	@Override
	public AutomataStateAssemblyStation estop() {
		return createState(Stop.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation nstop() {
		return createState(Stop.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation engage() {
		return createState(Working.class, this);
	}
	
}
