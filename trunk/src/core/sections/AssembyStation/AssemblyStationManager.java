package core.sections.AssembyStation;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class AssemblyStationManager extends ParallelPortManager{


	// We create the names for the groups and pins, so that we can access the
	// values later from other objects.
	public static final String ENGAGE = "ENGAGE";
	public static final String GEAR_DETECTED = "GEAR_DETECTED";
	public static final String AXIS_DETECTED = "AXIS_DETECTED";
	public static final String AP_DETECTED = "AP_DETECTED";
	public static final String ASSEMBLING_TIME = "ASSEMBLING_TIME";

	public AssemblyStationManager() {
		super();
		// We should set a name for all the pins, just in case
		try {
			this.setBitGroup(AssemblyStationManager.ENGAGE, 0, 0);
			this.setBitGroup(AssemblyStationManager.GEAR_DETECTED, 1, 1);
			this.setBitGroup(AssemblyStationManager.AXIS_DETECTED, 2, 2);
			this.setBitGroup(AssemblyStationManager.AP_DETECTED, 3, 3);
			this.setBitGroup(AssemblyStationManager.ASSEMBLING_TIME, 4, 15);
			// continue
		} catch (ParallelPortException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Boolean isEngaged(){
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(ENGAGE);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public void setEngaged(Boolean value){
		try {
			setValueByNameAsBoolean(ENGAGE, value);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Boolean isGearDetected(){
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(GEAR_DETECTED);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public Boolean isAxisDetected(){
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(AXIS_DETECTED);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public Boolean isAPDetected(){
		Boolean value = null;
		try {
			value = getValueByNameAsBoolean(AP_DETECTED);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
