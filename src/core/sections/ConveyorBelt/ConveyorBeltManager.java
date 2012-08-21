package core.sections.ConveyorBelt;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

/**
 * Example class implementing a specific manager for a parallel connection.
 * 
 * @author
 * 
 */

public class ConveyorBeltManager extends ParallelPortManager {

	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String SENSOR_UNLOAD = "SENSOR_UNLOAD";
	public static final String SENSOR_LOAD = "SENSOR_LOAD";
	public static final String RUNNING = "RUNNING";
	public static final String SPEED = "SPEED";
	public static final String CAPACITY = "CAPACITY";
	public static final String QUANTITY = "QUANTITY";

	public ConveyorBeltManager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(ConveyorBeltManager.SENSOR_UNLOAD, 0, 0);
			this.setBitGroup(ConveyorBeltManager.SENSOR_LOAD, 1, 1);
			this.setBitGroup(ConveyorBeltManager.RUNNING, 2, 2);
			this.setBitGroup(ConveyorBeltManager.SPEED, 3, 5);
			this.setBitGroup(ConveyorBeltManager.CAPACITY, 6, 10);
			this.setBitGroup(ConveyorBeltManager.QUANTITY, 11, 15);
			// continue
		} catch (ParallelPortException e) {
			Logger.println(e.getMessage());
			e.printStackTrace();
		}
		ParallelPortState state = new ParallelPortState();
		setState(state);
	}

	public void configure(int capacity, int speed) {
		try {
			setValueByName(ConveyorBeltManager.CAPACITY, capacity);
			setValueByName(ConveyorBeltManager.SPEED, speed);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public void setSensorInitial(Boolean value) {
		try {
			setValueByNameAsBoolean(SENSOR_LOAD, value);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public Boolean isSensorInitial() {
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(SENSOR_LOAD);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void setSensorFinish(Boolean value) {
		try {
			setValueByNameAsBoolean(SENSOR_UNLOAD, value);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public Boolean isSensorFinish() {
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(SENSOR_UNLOAD);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void setRunning(Boolean value) {
		try {
			setValueByNameAsBoolean(RUNNING, value);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	public Boolean isRunning() {
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(RUNNING);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}

	public Boolean isSensorUnloadMax() {
		return isSensorFinish() && (getBitGroupValue(CAPACITY) == getBitGroupValue(QUANTITY));
	}

	public Boolean isEmpty() {
		return (getBitGroupValue(QUANTITY) == 0);
	}

}
