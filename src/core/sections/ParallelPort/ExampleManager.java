package core.sections.ParallelPort;

import core.sections.ParallelPort.Utils.ParallelPortException;

/**
 * Example class implementing a specific manager for a parallel connection.
 * @author GelidElf
 *
 */

public class ExampleManager extends ParallelPortManager {

	//We create the names for the groups and pins, so that we can access the values later from other objects.
	public static final String ACTIVIDAD = "ACTIVIDAD";
	public static final String ESTADO = "ESTADO";
	public static final String SENSOR_CARGA = "SENSOR_CARGA";
	public static final String SENSOR_DESCARGA = "SENSOR_DESCARGA";
	
	public ExampleManager() {
		super();
		//We should set a name for all the pins, just in case
		try{
			this.setBitGroup(ExampleManager.ACTIVIDAD, 0, 2);
			this.setBitGroup(ExampleManager.ESTADO, 3, 6);
			this.setBitGroup(ExampleManager.SENSOR_CARGA, 7, 7);
			this.setBitGroup(ExampleManager.SENSOR_DESCARGA, 8, 8);
			//continue
		}catch (ParallelPortException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
