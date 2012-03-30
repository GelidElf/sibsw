package core.sections.Robot;

import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltSimulator;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.Robot.States.AutomataStateRobot;
import core.sections.Robot.States.Idle;

public class Robot extends Thread{
	
	private boolean gearReady;
	private boolean axisReady;
	private boolean unloadAs;
	private AutomataStateRobot currentState;
	private RobotSimulator simulator;
	
	public Robot(){
		simulator = new RobotSimulator();		
	}
	
	public void run(){
		currentState = new Idle();
		while(true){
			currentState.execute(this);
		}
	}
	
	public boolean isGearReady() {
		return gearReady;
	}
	public void setGearReady(boolean gearReady) {
		this.gearReady = gearReady;
	}
	public boolean isAxisReady() {
		return axisReady;
	}
	public void setAxisReady(boolean axisReady) {
		this.axisReady = axisReady;
	}

	public boolean isUnloadAs() {
		return unloadAs;
	}

	public void setUnloadAs(boolean unloadAs) {
		this.unloadAs = unloadAs;
	}

	public RobotSimulator getSimulator() {
		return simulator;
	}

	public void setSimulator(RobotSimulator simulator) {
		this.simulator = simulator;
	}
	
	/*public static void main(String args[]){
		System.out.println("lanzo simulación en robot");
	
		Robot a = new Robot();
	//	a.setDone(false);
		a.start();

		ParallelPortState state = new ParallelPortState();
		ATConveyorBelt atcb = new ATConveyorBelt();
		atcb.getManager().setState(state);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator(atcb.getManager());
		System.out.println("hilos lanzados1");
		cbs.getManager().setState(state);
		System.out.println("hilos lanzados2");
		atcb.start();
		System.out.println("hilos lanzados3");
		

		try {
			while(true){
				sleep(1000);
				if(atcb.isReady()) System.out.println("GearReady!!");
				a.setGearReady(atcb.isReady());
			//	if(a.isDone())atcb.piecePicked();
	
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
