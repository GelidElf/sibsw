package slave3;
import core.aplication.RunnableApplication;
import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ConveyorBelt.ConveyorBeltSimulator;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.QualityStation.ATQualityStation;
import core.sections.QualityStation.QualityStationManager;
import core.sections.QualityStation.QualityStationSimulator;


public class Slave3Executable extends RunnableApplication {

	public static void main (String []args){
		initialize(args,"slave3.ini");

		ATslave3 slave3 = new ATslave3(configuration);
		//		createAndSetQualityStation(slave3);
		//		createAndSetConveyorBelt(slave3);

//		slave3.start();
	}

	private static void createAndSetQualityStation(ATslave3 slave3) {
		QualityStationManager manager = new QualityStationManager();
		ATQualityStation atQCS = new ATQualityStation(slave3,manager);
		slave3.setATQualityStation(atQCS);
		QualityStationSimulator QCSS = new QualityStationSimulator(manager);
		QCSS.start();
		atQCS.start();
	}

	private static void createAndSetConveyorBelt(ATslave3 slave3) {
		ConveyorBeltManager manager = new ConveyorBeltManager();
		ATConveyorBelt atcb = new ATConveyorBelt(slave3,manager);
		slave3.setATConveyorbelt(atcb);
		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator(manager);
		cbs.start();
	}



}
