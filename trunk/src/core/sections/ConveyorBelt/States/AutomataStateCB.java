package core.sections.ConveyorBelt.States;

import core.model.AutomataState;

public abstract class AutomataStateCB extends AutomataState {

	protected static String nombreGrupo = "ASCB";
	
	private static final long serialVersionUID = 8098110414633719827L;
	
	private boolean [] posicionesOcupadas = new boolean[10];
	
	private int cantidad = 0;
	private int capacidad = 0;
	private int velocidad = 0;
	private boolean enMovimiento;
	
	public static AutomataStateCB stadoInicial(int capacidad){
		AutomataStateCB state = createState(Idle.class,null);
		state.setCapacidad(capacidad);
		state.setPosicionesOcupadas(new boolean[capacidad]);
		state.setEnMovimiento(false);
		return state;
	}
	
	public static AutomataStateCB createState(Class <? extends AutomataStateCB> targetClass, AutomataState currentState) {
		return (AutomataStateCB)createState(targetClass, currentState, AutomataStateCB.class);
	}
	
	public AutomataStateCB estop() {
		return this;
	}

	public AutomataStateCB restart() {
		return this;
	}

	public AutomataStateCB nstop() {
		return this;
	}

	public AutomataStateCB loadSensorTrue() {
		return this;
	}

	public AutomataStateCB unloadSensorTrue() {
		return this;
	}

	public AutomataStateCB unloadSensorTrueMax() {
		return this;
	}

	public AutomataStateCB unloadSensorFalse() {
		return this;
	}

	public AutomataStateCB empty() {
		return this;
	}

	/**
	 * @return the posicionesOcupadas
	 */
	public boolean[] getPosicionesOcupadas() {
		return posicionesOcupadas;
	}

	/**
	 * @param posicionesOcupadas the posicionesOcupadas to set
	 */
	public void setPosicionesOcupadas(boolean[] posicionesOcupadas) {
		this.posicionesOcupadas = posicionesOcupadas;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return the velocidad
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * @param velocidad the velocidad to set
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * @return the enMovimiento
	 */
	public boolean isEnMovimiento() {
		return enMovimiento;
	}

	/**
	 * @param enMovimiento the enMovimiento to set
	 */
	public void setEnMovimiento(boolean enMovimiento) {
		this.enMovimiento = enMovimiento;
	}

}
