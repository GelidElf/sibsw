package QualityStation;

import ParallelPort.ParallelPortManager;
import ParallelPort.Utils.ParallelPortException;

public class QualityStationManager extends ParallelPortManager{

	public static final String SENSOR = "SENSOR";
	public static final String ENABLED = "ENABLED";
	public static final String RESULT = "RESULT";
	public static final String ACTIVATION_TIME = "ACTIVATION_TIME";
	public static final String FAILURE_PERCENTAGE = "FAILURE_PERCENTAGE";
	
	public QualityStationManager() {
		super();
		//We should set a name for all the pins, just in case
		try{
			this.setBitGroup(QualityStationManager.SENSOR, 0, 0);
			this.setBitGroup(QualityStationManager.SENSOR, 1, 1);
			this.setBitGroup(QualityStationManager.SENSOR, 2, 2);
			this.setBitGroup(QualityStationManager.FAILURE_PERCENTAGE, 3, 9);
			this.setBitGroup(QualityStationManager.ACTIVATION_TIME, 10, 15);
			//continue
		}catch (ParallelPortException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
