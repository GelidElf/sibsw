package slave3;
import aplication2.RunnableApplication;
import ConveyorBelt.ATConveyorBelt;
import ConveyorBelt.ConveyorBeltSimulator;
import ParallelPort.ParallelPortState;
import QualityStation.ATQualityStation;
import QualityStation.QualityStationSimulator;


public class Slave3Executable extends RunnableApplication {

	public static void main (String []args){
		fileName = "slave3.ini";
		initialize(args);
		
		ATslave3 slave3 = new ATslave3(configuration);
		
		ParallelPortState QCSstate = new ParallelPortState();
		ATQualityStation atQCS = new ATQualityStation();
		atQCS.getManager().setState(QCSstate);
		slave3.setATQualityStation(atQCS);
		QualityStationSimulator QCSS = new QualityStationSimulator();
		QCSS.getManager().setState(QCSstate);
		atQCS.run();
		QCSS.run();
		
		
		ParallelPortState CBstate = new ParallelPortState();
		ATConveyorBelt atcb = new ATConveyorBelt();
		atcb.getManager().setState(CBstate);
		slave3.setATConveyorbelt(atcb);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator();
		cbs.getManager().setState(CBstate);
		atcb.run();
		cbs.run();
		
		
		
	}
}
