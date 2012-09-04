package slave2;

import core.aplication.RunnableApplication;

public class Slave2Executable extends RunnableApplication {

	public static void main(String[] args) {
		initialize(args, "slave2.ini");

		Slave2Automata slave2 = new Slave2Automata(configuration);
		slave2.startCommand();
	}

}
