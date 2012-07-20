package master;

import java.awt.EventQueue;

import core.aplication.RunnableApplication;
import core.gui.interfaz;

public class MasterExecutable extends RunnableApplication{

	public static void main(String[] args) {
		initialize(args,"master.ini");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATMaster master = new ATMaster(configuration);
					interfaz window = new interfaz(master);
					window.getFrame().setVisible(true);
					master.begin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
