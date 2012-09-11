package slave1;

public enum Slave1Input {

	START,
	NSTOP,
	ESTOP,
	RESTART,
	AXIS_IN_AS,
	GEAR_IN_AS,
	AS_IN_TRANSPORT,
	AUTO_FEED_AXIS_ON,
	AUTO_FEED_AXIS_OFF,
	AUTO_FEED_GEAR_ON,
	AUTO_FEED_GEAR_OFF,
	AXIS_READY,
	GEAR_READY,
	AS_READY,
	AS_EMPTY,
	TRANSPORT_READY; //When Master says that the transport can hold more elements

}
