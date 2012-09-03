package core.messages.enums;

public enum ConfigurationParameters {
	CB_AXIS_LENGTH,
	CB_GEARS_LENGTH,
	CB_AXIS_SPEED,
	CB_GEARS_SPEED,
	CB_AXIS_CAPACITY,
	CB_GEARS_CAPACITY,
	ACTIVATION_TIME_AS,
	CB_TRANSFER_LENGTH,
	CB_TRANSFER_SPEED,
	ACTIVATION_TIME_WS,
	ACTIVATION_TIME_QCS,
	CB_OK_LENGTH,
	CB_WRONG_LENGTH,
	CB_OK_SPEED,
	PICK_TIME_AXIS_GEAR,
	TRANSPORT_PLACE_TIME_AXIS_GEAR,
	TRANSPORT_PLACE_TIME_ASSEMBLED,
	PICK_TIME_ASSEMBLED,
	TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS,
	TRANSPORT_PLACE_TIME_WELDED;
	
	public static ConfigurationParameters getEnum(String name){
		for(ConfigurationParameters parameter:ConfigurationParameters.values()){
			if(parameter.name().equals(name)){
				return parameter;
			}
		}
		return null;
	}
}


