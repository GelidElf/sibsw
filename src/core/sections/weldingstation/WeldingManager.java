package core.sections.weldingstation;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class WeldingManager extends ParallelPortManager {

	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String DELIVER_WELDMENT = "DELIVER_WELDMENT";
	public static final String ENABLE = "BUSY";
	public static final String TIME_TO_WELD = "TIME_TO_WELD";

	public WeldingManager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(WeldingManager.DELIVER_WELDMENT, 0, 0);
			this.setBitGroup(WeldingManager.ENABLE, 1, 1);
			this.setBitGroup(WeldingManager.TIME_TO_WELD, 2, 7);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void configure(int speed, int timeToAxisGear, int timeToTransport, int timeToAssembled) {
		try {
			setValueByName(WeldingManager.TIME_TO_WELD, timeToAssembled);
			clearJobTypes();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public void setJobDone() {
		try {
			setValueByNameAsBoolean(WeldingManager.ENABLE, false);
			clearJobTypes();
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearJobTypes() throws ParallelPortException {
		setValueByNameAsBoolean(WeldingManager.DELIVER_WELDMENT, false);
	}

	public boolean jobToBeDone() {
		boolean isEnabled = true;
		try {
			isEnabled = getValueByNameAsBoolean(WeldingManager.ENABLE);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return isEnabled;
	}

}
