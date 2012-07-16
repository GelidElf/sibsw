package core.sections.AssembyStation.States;

import core.model.AutomataState;

public abstract class AutomataStateAssemblyStation extends AutomataState {

	protected static String nombreGrupo = "ASCB";
	
	private static final long serialVersionUID = 8098110414633719827L;
	
	public static AutomataStateAssemblyStation estadoInicial(int capacidad){
		AutomataStateAssemblyStation state = createState(Idle.class,null);
		return state;
	}
	
	public static AutomataStateAssemblyStation createState(Class <? extends AutomataStateAssemblyStation> targetClass, AutomataState currentState) {
		return (AutomataStateAssemblyStation)createState(targetClass, currentState, AutomataStateAssemblyStation.class);
	}
	
	public AutomataStateAssemblyStation estop() {
		return this;
	}

	public AutomataStateAssemblyStation restart() {
		return this;
	}

	public AutomataStateAssemblyStation nstop() {
		return this;
	}

	public AutomataStateAssemblyStation axisDetectedTrue() {
		return this;
	}

	public AutomataStateAssemblyStation axisDetectedFalse() {
		return this;
	}

	public AutomataStateAssemblyStation gearDetectedTrue() {
		return this;
	}

	public AutomataStateAssemblyStation gearDetectedFalse() {
		return this;
	}

	public AutomataStateAssemblyStation engage() {
		return this;
	}

	public AutomataStateAssemblyStation apDetectedTrue() {
		return this;
	}
	
	public AutomataStateAssemblyStation apDetectedFalse() {
		return this;
	}

}
