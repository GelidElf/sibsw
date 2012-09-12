package master;

public enum MasterInput {
	START,
	AP_IN_WS, //assembled piece in welding station
	WP_IN_QS, //welded station finished and piece ready
	QCS_LOADED, //checked piece in conveyor belt
	AS_IN_TCB, // Assembled piece in transfer belt (for communication between
	// slave1 and 2)
	ESTOP,
	NSTOP,
	RESUME,
	FEED_WS,
	MOVE_AS_FROM_TCB_TO_WS, //Se�al para que indique al robot que mueva la pieza
	MOVE_WP_FROM_WS_TO_QCS, //Se�al para que indique al robot que mueva la pieza
	QCS_EMPTY,
	PIECE_OK,
	OK_LOADED,
	PIECE_NOK,
	NOK_LOADED,
	DeliveredChecked;
}
