package master;

import core.aplication.RunnableApplication;

public class MasterExecutable extends RunnableApplication{

	public static void main(String[] args) {
		initialize(args,"master.ini");
		ATMaster master = new ATMaster(configuration);
		master.start();
	}

}
