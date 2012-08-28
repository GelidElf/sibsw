package core.sections.conveyorbelt2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class ConveyorBeltManager extends ParallelPortManager {

	public static final String ENABLE = "ENABLE";
	public static final String FEED = "FEED";
	public static final String REMOVE = "REMOVE";
	public static final String AVAILABLE = "AVAILABLE";
	public static final String SPEED = "SPEED";
	public static final String CAPACITY = "CAPACITY";
	public static final String QUANTITY = "QUANTITY";

	public ConveyorBeltManager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(ConveyorBeltManager.ENABLE, 0, 0);
			this.setBitGroup(ConveyorBeltManager.FEED, 1, 1);
			this.setBitGroup(ConveyorBeltManager.REMOVE, 2, 2);
			this.setBitGroup(ConveyorBeltManager.AVAILABLE, 3, 3);
			this.setBitGroup(ConveyorBeltManager.SPEED, 4, 7);
			this.setBitGroup(ConveyorBeltManager.CAPACITY, 8, 11);
			this.setBitGroup(ConveyorBeltManager.QUANTITY, 12, 15);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
