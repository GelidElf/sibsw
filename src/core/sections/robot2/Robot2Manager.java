package core.sections.robot2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot2Manager extends ParallelPortManager {

	public static final String ENABLED = "ENABLED";
	public static final String TIME_WORKING = "TIME_WORKING";

	public Robot2Manager() {
		super();
		//We should set a name for all the pins, just in case
		try {
			this.setBitGroup(Robot2Manager.ENABLED, 0, 0);
			this.setBitGroup(Robot2Manager.TIME_WORKING, 1, 15);
			//continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
