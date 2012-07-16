package core.sections.AssembyStation.States;

public class Stop extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = -6322412145465649967L;

	@Override
	public AutomataStateAssemblyStation restart() {
		return createState(Idle.class, this);
	}
	
}
