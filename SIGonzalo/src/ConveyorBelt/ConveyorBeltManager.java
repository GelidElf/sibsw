package ConveyorBelt;


import ParallelPort.ParallelPortManager;
import ParallelPort.Utils.ParallelPortException;

/**
 * Example class implementing a specific manager for a parallel connection.
 * @author 
 *
 */

public class ConveyorBeltManager extends ParallelPortManager {

	//We create the names for the groups and pins, so that we can access the values later from other objects.
	public static final String SENSOR_FINISH = "SENSOR_FINISH";
	public static final String SENSOR_INITIAL = "SENSOR_INITIAL";
	public static final String RUNNING = "RUNNING";
	public static final String VELOCITY = "VELOCITY";
	public static final String CAPACITY = "CAPACITY";
	public static final String QUANTITY = "QUANTITY";
	
	
	
	public ConveyorBeltManager() {
		super();
		//We should set a name for all the pins, just in case
		try{
			this.setBitGroup(ConveyorBeltManager.SENSOR_FINISH, 0, 0);
			this.setBitGroup(ConveyorBeltManager.SENSOR_INITIAL, 1, 1);
			this.setBitGroup(ConveyorBeltManager.RUNNING, 2, 2);
			this.setBitGroup(ConveyorBeltManager.VELOCITY, 3, 5);
			this.setBitGroup(ConveyorBeltManager.CAPACITY, 6, 10);
			this.setBitGroup(ConveyorBeltManager.QUANTITY, 11,15);
			//continue
		}catch (ParallelPortException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
