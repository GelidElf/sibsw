package core.sections.robot1;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot1Manager extends ParallelPortManager {

	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String PICK_GEAR = "PICK_GEAR";
	public static final String PICK_AXIS = "PICK_AXIS";
	public static final String PICK_ASSEMBLED = "PICK_ASSEMBLED";
	public static final String BUSY = "BUSY";
	public static final String SPEED = "SPEED";
	public static final String PICK_TIME = "PICK_TIME";
	public static final String TRANSPORT_TIME = "TRANSPORT_TIME";
	public static final String ASSEMBLED_TIME = "ASSEMBLED_TIME";

	public Robot1Manager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(Robot1Manager.PICK_GEAR, 0, 0);
			this.setBitGroup(Robot1Manager.PICK_AXIS, 1, 1);
			this.setBitGroup(Robot1Manager.PICK_ASSEMBLED, 2, 2);
			this.setBitGroup(Robot1Manager.BUSY, 3, 3);
			this.setBitGroup(Robot1Manager.SPEED, 4, 6);
			this.setBitGroup(Robot1Manager.PICK_TIME, 7, 9);
			this.setBitGroup(Robot1Manager.TRANSPORT_TIME, 10, 12);
			this.setBitGroup(Robot1Manager.ASSEMBLED_TIME, 13, 15);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
