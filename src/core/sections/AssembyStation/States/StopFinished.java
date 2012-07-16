package core.sections.AssembyStation.States;

public class StopFinished extends AutomataStateAssemblyStation {

	private static final long serialVersionUID = 4676647920687242504L;

	@Override
	public AutomataStateAssemblyStation restart() {
		return createState(Finished.class, this);
	}
}
