package core.aplication;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		 Thread serverThread = new Thread() {
	            public void run() {
	                master.MasterExecutable.main(args);
	            }
	        };

	        Thread clientThread = new Thread() {
	            public void run() {
	                slave3.Slave3Executable.main(args);
	            }
	        };
	        
	        Thread scadaThread = new Thread() {
	            public void run() {
	                scada.ScadaExecutable.main(args);
	            }
	        };

	        serverThread.start();

	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        scadaThread.start();
	        clientThread.start();
	}

}
