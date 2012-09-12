package core.sections.AssembyStation;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class AssemblyStationSimulator extends Thread implements ParallelPortManagerObserver {

	private AssemblyStationManager manager;

	public AssemblyStationSimulator(AssemblyStationManager manager) {
		this.manager = manager;
		setName("ASSimulator");
	}

	private int getAssemblingTime() {
		try {
			return manager.getValueByName(AssemblyStationManager.ASSEMBLING_TIME);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			if (stationLoadedCorrectly() && stationClearToProceed()) {
				try {
					Logger.println("Assembling");
					sleepExecution(getAssemblingTime());
					Logger.println("Finished assembling");
					manager.setValueByNameAsBoolean(AssemblyStationManager.AP_DETECTED, true);
					manager.setValueByNameAsBoolean(AssemblyStationManager.AXIS_DETECTED, false);
					manager.setValueByNameAsBoolean(AssemblyStationManager.GEAR_DETECTED, false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParallelPortException e) {
					e.printStackTrace();
				}
			} else {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					Logger.println("error during sleep");
				}
			}
		}
	}

	private boolean stationClearToProceed() {
		try {
			return !manager.getValueByNameAsBoolean(AssemblyStationManager.AP_DETECTED);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean stationLoadedCorrectly() {
		boolean loaded = true;
		try {
			loaded = loaded && manager.getValueByNameAsBoolean(AssemblyStationManager.AXIS_DETECTED);
			loaded = loaded && manager.getValueByNameAsBoolean(AssemblyStationManager.GEAR_DETECTED);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		return loaded;
	}

	private void sleepExecution(int velocity) throws InterruptedException {
		if (velocity == 0) {
			Logger.println("Speed set to 0");
		} else {

			sleep(1000 / velocity);
		}
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(AssemblyStationManager.ENGAGE)) {
			start();
		}

	}

}
