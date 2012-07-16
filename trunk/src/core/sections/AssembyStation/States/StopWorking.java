package core.sections.AssembyStation.States;

public class StopWorking extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = -1635083940951810374L;

	@Override
	public AutomataStateAssemblyStation restart() {
		return createState(Working.class, this);
	}
	
}
