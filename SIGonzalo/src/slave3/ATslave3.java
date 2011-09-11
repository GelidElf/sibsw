package slave3;

import core.aplication.Configuration;
import core.messages.CommunicationManager;
import core.messages.SingleInboxConnectionManager;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.QualityStation.ATQualityStation;

public class ATslave3 extends AutomataContainer {

	private ATConveyorBelt conveyorBelt;
	private ATQualityStation qualityStation;
	private CommunicationManager commManager;
	
	
	public ATslave3(Configuration conf){
		super(conf);
		commManager = new SingleInboxConnectionManager("Slave3",conf);
	}
	
	
	public void setATConveyorbelt(ATConveyorBelt cb){
		conveyorBelt = cb;
	}
	
	public void setATQualityStation (ATQualityStation qcs){
		qualityStation = qcs;
	}
	
	
	
}
