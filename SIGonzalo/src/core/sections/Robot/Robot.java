package core.sections.Robot;

public class Robot {
	
	private boolean gearReady;
	private boolean axisReady;
	private boolean unloadAs;
	
	public Robot(){
		
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

}
