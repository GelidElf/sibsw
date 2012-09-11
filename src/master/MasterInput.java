package master;

public enum MasterInput {
	START,
	AS_READY,
	EMPTY_TRANSFER_CB,
	AP_IN_WS, //assembled piece in welding station
	WP_IN_QS, //welded piece in quality station
	QCS_LOADED, //checked piece in conveyor belt
	AS_IN_TCB, // Assembled piece in transfer belt (for communication between
	// slave1 and 2)
	ESTOP,
	NSTOP,
	RESUME,
	FEED_WS,
	AS_READY_TO_PICKUP,
	QCS_EMPTY,
	PIECE_OK,
	OK_LOADED,
	PIECE_NOK,
	NOK_LOADED,
	DeliveredChecked,
	TRANSFER_CLEAR; //shortcut for communication between slave2 and 1
}
