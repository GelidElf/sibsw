package core.messages.enums;

public enum ReportValues {
	OK_PIECES,
	NOT_OK_PIECES,
	SYSTEM_BOOTS,
	SYSTEM_STOPS,
	SYSTEM_EMERGENCIES;

	public static ReportValues getEnum(String name){
		for(ReportValues parameter:ReportValues.values()){
			if(parameter.name().equals(name)){
				return parameter;
			}
		}
		return null;
	}
}
