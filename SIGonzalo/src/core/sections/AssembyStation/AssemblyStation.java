package core.sections.AssembyStation;

public class AssemblyStation {
	
	private boolean gearNeeded;
	private boolean axisNeeded;
	private boolean complete;
	private boolean empty;
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
