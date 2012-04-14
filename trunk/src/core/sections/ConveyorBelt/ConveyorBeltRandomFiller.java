package core.sections.ConveyorBelt;

import java.util.Random;

public class ConveyorBeltRandomFiller extends Thread {

	private ConveyorBeltManager manager;
	private Random rand = new Random(System.currentTimeMillis());

	public ConveyorBeltRandomFiller(ConveyorBeltManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			if (!manager.isSensorInitial()) {
				manager.setSensorInitial(rand.nextBoolean());
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
