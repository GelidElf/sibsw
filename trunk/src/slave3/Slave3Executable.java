package slave3;

import core.aplication.RunnableApplication;

public class Slave3Executable extends RunnableApplication {

	public static void main(String[] args) {
		initialize(args, "slave3.ini");

		Slave3Automata slave3 = new Slave3Automata(configuration);
		slave3.startCommand();
	}

}
