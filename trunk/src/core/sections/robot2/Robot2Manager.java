package core.sections.robot2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot2Manager extends ParallelPortManager {

	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String DELIVER_ASSEMBLED_PIECE = "DELIVER_ASSEMBLED_PIECE";
	public static final String DELIVER_WELDED_PIECE = "DELIVER_WELDED_PIECE";
	public static final String DELIVER_CHECKED_PIECE = "DELIVER_CHECKED_PIECE";
	public static final String ENABLE = "BUSY";
	//public static final String SPEED = "SPEED";
	public static final String TIME_TO_ASSEMBLED_P = "TIME_TO_ASSEMBLED_P"; //time from assembled to welded
	public static final String TIME_TO_WELDED = "TIME_TO_WELDED"; //time to welded to checked
	public static final String TIME_TO_CB = "TIME_TO_CB"; //time to checked to CB
	
	public Robot2Manager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(Robot2Manager.DELIVER_ASSEMBLED_PIECE, 0, 0);
			this.setBitGroup(Robot2Manager.DELIVER_WELDED_PIECE, 1, 1);
			this.setBitGroup(Robot2Manager.DELIVER_CHECKED_PIECE, 2, 2);
			this.setBitGroup(Robot2Manager.ENABLE, 3, 3);
			//this.setBitGroup(Robot2Manager.SPEED, 4, 6);
			this.setBitGroup(Robot2Manager.TIME_TO_ASSEMBLED_P, 4, 7);
			this.setBitGroup(Robot2Manager.TIME_TO_CB, 8, 11);
			this.setBitGroup(Robot2Manager.TIME_TO_WELDED, 12, 15);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void configure(int timeToAssembledP, int timeToTransport, int TimeToWelded) {
		try {
			//setValueByName(Robot2Manager.SPEED, speed);
			setValueByName(Robot2Manager.TIME_TO_ASSEMBLED_P, timeToAssembledP);
			setValueByName(Robot2Manager.TIME_TO_CB, timeToTransport);
			setValueByName(Robot2Manager.DELIVER_WELDED_PIECE, TimeToWelded);
			clearJobTypes();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public void setJobDone() {
		try {
			setValueByNameAsBoolean(Robot2Manager.ENABLE, false);
			clearJobTypes();
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearJobTypes() throws ParallelPortException {
		setValueByNameAsBoolean(Robot2Manager.DELIVER_ASSEMBLED_PIECE, false);
		setValueByNameAsBoolean(Robot2Manager.DELIVER_WELDED_PIECE, false);
		setValueByNameAsBoolean(Robot2Manager.DELIVER_CHECKED_PIECE, false);
	}

	public boolean jobToBeDone() {
		boolean isEnabled = true;
		try {
			isEnabled = getValueByNameAsBoolean(Robot2Manager.ENABLE);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return isEnabled;
	}
}
