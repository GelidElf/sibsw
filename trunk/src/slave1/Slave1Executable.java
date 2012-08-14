package slave1;
import core.aplication.RunnableApplication;


public class Slave1Executable extends RunnableApplication {

	public static void main (String []args){
		initialize(args,"slave1.ini");

		Slave1Automata slave1 = new Slave1Automata(configuration);
		slave1.startCommand();
	}

}
