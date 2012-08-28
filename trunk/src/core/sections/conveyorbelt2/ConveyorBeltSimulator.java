package core.sections.conveyorbelt2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;

public class ConveyorBeltSimulator extends Thread implements ParallelPortManagerObserver {

	private ConveyorBeltManager manager;

	public ConveyorBeltSimulator(ConveyorBeltManager manager) {
		this.manager = manager;
	}

	public ConveyorBeltSimulator() {
		this.manager = new ConveyorBeltManager();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(ConveyorBeltManager.ENABLE)) {
			// if ()
		}

	}

}
