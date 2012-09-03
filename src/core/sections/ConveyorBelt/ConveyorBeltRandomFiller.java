package core.sections.ConveyorBelt;

import java.util.Random;

public class ConveyorBeltRandomFiller extends Thread {

	private ConveyorBeltManager manager;
	private Random rand = new Random(System.currentTimeMillis());
	private boolean enabled;

	public ConveyorBeltRandomFiller(ConveyorBeltManager manager) {
		this.manager = manager;
		enabled = false;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			if (enabled && !manager.isSensorInitial()) {
				manager.setSensorInitial(rand.nextBoolean());
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
