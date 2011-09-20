package slave3;
import core.aplication.RunnableApplication;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltSimulator;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.QualityStation.ATQualityStation;
import core.sections.QualityStation.QualityStationSimulator;


public class Slave3Executable extends RunnableApplication {

	public static void main (String []args){
		initialize(args,"slave3.ini");
		
		ATslave3 slave3 = new ATslave3(configuration);
		createAndSetQualityStation(slave3);
		createAndSetConveyorBelt(slave3);

		slave3.run();
	}

	private static void createAndSetQualityStation(ATslave3 slave3) {
		ParallelPortState QCSstate = new ParallelPortState();
		ATQualityStation atQCS = new ATQualityStation();
		atQCS.setParallelPortState(QCSstate);
		slave3.setATQualityStation(atQCS);
		QualityStationSimulator QCSS = new QualityStationSimulator();
		QCSS.setParallelPortState(QCSstate);
		QCSS.run();
		atQCS.run();
	}

	private static void createAndSetConveyorBelt(ATslave3 slave3) {
		ParallelPortState CBstate = new ParallelPortState();
		ATConveyorBelt atcb = new ATConveyorBelt();
		atcb.setParallelPortState(CBstate);
		slave3.setATConveyorbelt(atcb);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator();
		cbs.setParallelPortState(CBstate);
		cbs.run();
		atcb.run();
	}
	
	
	
}
