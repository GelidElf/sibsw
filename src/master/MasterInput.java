package master;

public enum MasterInput {
	START,
	EMPTY_TRANSFER_CB,
	FEED_AXIS,
	FEED_GEAR,
	AUTO_FEED_AXIS_ON,
	AUTO_FEED_AXIS_OFF,
	AUTO_FEED_GEAR_ON,
	AUTO_FEED_GEAR_OFF, 
	AP_IN_WS, //assembled piece in welding station
	WP_IN_QS, //welded piece in quality station
	CP_IN_CB, //checked piece in conveyor belt
	AS_IN_TCB, // Assembled piece in transfer belt (for communication between
				// slave1 and 2)
	ESTOP, 
	NSTOP, 
	RESUME
}
