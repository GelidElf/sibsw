package Slave;


import core.sections.AssembyStation.AssemblyStation;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.Robot.Robot;
import Slave.States.AutomataStateSlave;
import Slave.States.Idle;

public class ATSlave1{
	
	private ATConveyorBelt gearBelt;
	private ATConveyorBelt axisBelt;
	private AutomataStateSlave currentState;
	private AssemblyStation assemblyStation;
	private Robot robot;
	
	


	public AutomataStateSlave getCurrentState() {
		return currentState;
	}


	public ATSlave1(){
		gearBelt = new ATConveyorBelt();
		axisBelt = new ATConveyorBelt();
	}
	
	
	/*
	 * Recibir config del buzón, 
	 */
	public void start(){
		setInitialSettings("Mensaje(s) leido del buzón con los parámetros");
		setCurrentState(new Idle());
		while(true){
			currentState.execute(this);
		}
		
		
	}
	
	public void setInitialSettings(String settings){
		
		//TODO: po hacerlo >.<
		//robot.setSpeed......
		//gearBelt.setLength,speed, capacity....
		//AS.setAssemblyDelay....
	
	}
	public void setCurrentState(AutomataStateSlave state){
		if(currentState==null || state == null){
			currentState = new Idle();
		}else{
			currentState = state;
		}
	}
	
	public ATConveyorBelt getGearBelt() {
		return gearBelt;
	}


	public void setGearBelt(ATConveyorBelt gearBelt) {
		this.gearBelt = gearBelt;
	}


	public ATConveyorBelt getAxisBelt() {
		return axisBelt;
	}


	public void setAxisBelt(ATConveyorBelt axisBelt) {
		this.axisBelt = axisBelt;
	}


	public AssemblyStation getAssemblyStation() {
		return assemblyStation;
	}


	public void setAssemblyStation(AssemblyStation assemblyStation) {
		this.assemblyStation = assemblyStation;
	}


	public Robot getRobot() {
		return robot;
	}


	public void setRobot(Robot robot) {
		this.robot = robot;
	}
}
