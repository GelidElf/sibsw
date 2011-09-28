package scada;

import core.aplication.RunnableApplication;

public class ScadaExecutable extends RunnableApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initialize(args,"scada.ini");
		new Scada(configuration);
	}

}
