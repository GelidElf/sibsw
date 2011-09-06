package slave3;

import core.aplication.Configuration;
import core.messages.CommunicationManager;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.QualityStation.ATQualityStation;

public class ATslave3 extends Thread {

	
	private ATConveyorBelt conveyorBelt;
	private ATQualityStation qualityStation;
	private CommunicationManager commManager;
	private Configuration conf;
	
	
	public ATslave3(Configuration conf){
		this.conf = conf;
		commManager = new CommunicationManager(false, "Slave3",this.conf);
	}
	
	
	public void setATConveyorbelt(ATConveyorBelt cb){
		conveyorBelt = cb;
	}
	
	public void setATQualityStation (ATQualityStation qcs){
		qualityStation = qcs;
	}
	
	
	
}
