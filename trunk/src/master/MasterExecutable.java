package master;

import core.aplication.RunnableApplication;
import core.gui.interfaz;

public class MasterExecutable extends RunnableApplication{

	private static ATMaster master; 
	
	public static void main(String[] args) {
		initialize(args,"master.ini");
		master = new ATMaster(configuration);
		interfaz window = new interfaz(master);
		window.getFrame().setVisible(true);
		master.startCommand();
	}

}
