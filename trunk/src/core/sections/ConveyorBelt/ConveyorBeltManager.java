package core.sections.ConveyorBelt;


import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;

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
	public static final String SPEED = "SPEED";
	public static final String CAPACITY = "CAPACITY";
	public static final String QUANTITY = "QUANTITY";
	
	
	
	public ConveyorBeltManager() {
		super();
		//We should set a name for all the pins, just in case
		try{
			this.setBitGroup(ConveyorBeltManager.SENSOR_FINISH, 0, 0);
			this.setBitGroup(ConveyorBeltManager.SENSOR_INITIAL, 1, 1);
			this.setBitGroup(ConveyorBeltManager.RUNNING, 2, 2);
			this.setBitGroup(ConveyorBeltManager.SPEED, 3, 5);
			this.setBitGroup(ConveyorBeltManager.CAPACITY, 6, 10);
			this.setBitGroup(ConveyorBeltManager.QUANTITY, 11,15);
			//continue
		}catch (ParallelPortException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public Boolean isSensorFinish(){
		try {
			return getValueByName(SENSOR_FINISH)==1;
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean isSensorInitial(){
		try {
			return getValueByName(SENSOR_INITIAL)==1;
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean isRunning(){
		try {
			return getValueByName(RUNNING)==1;
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getSpeed(){
		try {
			return getValueByName(SPEED);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getCapacity(){
		try {
			return getValueByName(CAPACITY);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getQuantity(){
		try {
			return getValueByName(QUANTITY);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
