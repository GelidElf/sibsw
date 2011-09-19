package Slave;


import core.sections.AssembyStation.AssemblyStation;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltSimulator;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.Robot.Robot;
import Slave.States.AutomataStateSlave;
import Slave.States.Idle;

public class ATSlave1 extends Thread{
	
	private ATConveyorBelt gearBelt;
	private ATConveyorBelt axisBelt;
	private AutomataStateSlave currentState;
	private AssemblyStation assemblyStation;
	private Robot robot;
	
	


	public AutomataStateSlave getCurrentState() {
		return currentState;
	}


	public ATSlave1(){
		System.out.println("lanzo simulación en robot");
		/* Iniciamos robot*/
		robot = new Robot();
		
		assemblyStation = new AssemblyStation();
		assemblyStation.setEmpty(true);
		assemblyStation.setGearNeeded(true);
		assemblyStation.setAxisNeeded(true);
		assemblyStation.start();
		
		ParallelPortState state = new ParallelPortState();
		gearBelt = new ATConveyorBelt();
		gearBelt.getManager().setState(state);
		
	
		ParallelPortState state2 = new ParallelPortState();
		axisBelt = new ATConveyorBelt();
		axisBelt.getManager().setState(state2);
		axisBelt.start();
		
	}
	
	
	/*
	 * Recibir config del buzón, 
	 */
	public void start(){
		setInitialSettings("Mensaje(s) leido del buzón con los parámetros");
		gearBelt.start();
		robot.start();
		setCurrentState(new Idle());
		while(true){
			currentState.execute(this);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public static void main (String args[]){
		ATSlave1 slave = new ATSlave1();
		System.out.println("ARRRRRRRANCO!");
		slave.start();
	}
}
