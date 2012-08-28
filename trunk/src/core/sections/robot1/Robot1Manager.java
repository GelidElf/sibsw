package core.sections.robot1;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot1Manager extends ParallelPortManager {

	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String DELIVER_GEAR = "DELIVER_GEAR";
	public static final String DELIVER_AXIS = "DELIVER_AXIS";
	public static final String DELIVER_ASSEMBLED = "DELIVER_ASSEMBLED";
	public static final String ENABLE = "BUSY";
	public static final String SPEED = "SPEED";
	public static final String TIME_TO_AXIS_GEAR = "TIME_TO_AXIS_GEAR";
	public static final String TIME_TO_TRANSPORT = "TIME_TO_TRANSPORT";
	public static final String TIME_TO_ASSEMBLED = "TIME_TO_ASSEMBLED";

	public Robot1Manager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(Robot1Manager.DELIVER_GEAR, 0, 0);
			this.setBitGroup(Robot1Manager.DELIVER_AXIS, 1, 1);
			this.setBitGroup(Robot1Manager.DELIVER_ASSEMBLED, 2, 2);
			this.setBitGroup(Robot1Manager.ENABLE, 3, 3);
			this.setBitGroup(Robot1Manager.SPEED, 4, 6);
			this.setBitGroup(Robot1Manager.TIME_TO_AXIS_GEAR, 7, 9);
			this.setBitGroup(Robot1Manager.TIME_TO_TRANSPORT, 10, 12);
			this.setBitGroup(Robot1Manager.TIME_TO_ASSEMBLED, 13, 15);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void configure(int speed, int timeToAxisGear, int timeToTransport, int timeToAssembled) {
		try {
			setValueByName(Robot1Manager.SPEED, speed);
			setValueByName(Robot1Manager.TIME_TO_AXIS_GEAR, timeToAxisGear);
			setValueByName(Robot1Manager.TIME_TO_TRANSPORT, timeToTransport);
			setValueByName(Robot1Manager.TIME_TO_ASSEMBLED, timeToAssembled);
			clearJobTypes();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public void setJobDone() {
		try {
			setValueByNameAsBoolean(Robot1Manager.ENABLE, false);
			clearJobTypes();
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearJobTypes() throws ParallelPortException {
		setValueByNameAsBoolean(Robot1Manager.DELIVER_AXIS, false);
		setValueByNameAsBoolean(Robot1Manager.DELIVER_GEAR, false);
		setValueByNameAsBoolean(Robot1Manager.DELIVER_ASSEMBLED, false);
	}

	public boolean jobToBeDone() {
		boolean isEnabled = true;
		try {
			isEnabled = getValueByNameAsBoolean(Robot1Manager.ENABLE);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return isEnabled;
	}

}
