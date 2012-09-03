package slave2;

import core.aplication.RunnableApplication;

public class Slave2Executable extends RunnableApplication {

	public static void main(String[] args) {
		initialize(args, "slave1.ini");

		Slave2Automata slave1 = new Slave2Automata(configuration);
		slave1.startCommand();
	}

}
