package core.sections.AssembyStation;

import core.sections.AssembyStation.States.AssemblyStationState;
import core.sections.AssembyStation.States.Idle;

public class AssemblyStation extends Thread{
	
	private boolean gearNeeded;
	private boolean axisNeeded;
	private boolean complete;
	private boolean empty;
	private AssemblyStationState currentState;
	
	public AssemblyStation(){
		gearNeeded = true;
		axisNeeded = true;
		complete = false;
		empty = true;
	}
	
	public void run(){
		currentState = new Idle();
		while(true){
			currentState.execute(this);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isGearNeeded() {
		return gearNeeded;
	}
	public void setGearNeeded(boolean gearNeeded) {
		this.gearNeeded = gearNeeded;
	}
	public boolean isAxisNeeded() {
		return axisNeeded;
	}
	public void setAxisNeeded(boolean axisNeeded) {
		this.axisNeeded = axisNeeded;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
