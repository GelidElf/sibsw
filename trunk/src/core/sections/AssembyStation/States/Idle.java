package core.sections.AssembyStation.States;


public class Idle extends AutomataStateAssemblyStation{

	private static final long serialVersionUID = -5445748675365451285L;
	
	@Override
	public AutomataStateAssemblyStation estop() {
		return createState(Stop.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation nstop() {
		return createState(Stop.class, this);
	} 
	
	@Override
	public AutomataStateAssemblyStation gearDetectedTrue() {
		return createState(GearLodaded.class, this);
	}
	
	@Override
	public AutomataStateAssemblyStation axisDetectedTrue() {
		return createState(AxisLoaded.class, this);
	}

}
