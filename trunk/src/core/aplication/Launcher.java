package core.aplication;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		Thread slave1Thread = new Thread() {
			@Override
			public void run() {
				slave1.Slave1Executable.main(args);
			}
		};

		Thread slave2Thread = new Thread() {
			@Override
			public void run() {
				slave2.Slave2Executable.main(args);
			}
		};

		Thread slave3Thread = new Thread() {
			@Override
			public void run() {
				slave3.Slave3Executable.main(args);
			}
		};

		slave1Thread.start();
		sleep(300);
		slave3Thread.start();
		sleep(300);
		slave2Thread.start();

	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
