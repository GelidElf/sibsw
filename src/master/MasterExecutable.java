package master;

import core.aplication.RunnableApplication;
import core.gui.interfaz;

public class MasterExecutable extends RunnableApplication{

	private static MasterAutomata master; 
	
	public static void main(String[] args) {
		initialize(args,"master.ini");
		master = new MasterAutomata(configuration);
		interfaz window = new interfaz(master);
		window.getFrame().setVisible(true);
		master.startCommand();
	}

}
