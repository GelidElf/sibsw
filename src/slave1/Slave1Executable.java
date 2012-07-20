package slave1;
import core.aplication.RunnableApplication;


public class Slave1Executable extends RunnableApplication {

	public static void main (String []args){
		initialize(args,"slave1.ini");

		ATSlave1 slave1 = new ATSlave1(configuration);
		slave1.begin();
	}

}
