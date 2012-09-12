package core.sections.weldingstation;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class WeldingSimulator extends Thread implements ParallelPortManagerObserver {

	private static final long DEFAULT_WAIT_TIME = 2;
	private long currentJobTime;
	private WeldingManager manager;

	public WeldingSimulator(WeldingManager manager) {
		this.manager = manager;
		manager.registerObserver(this);
	}

	public void welding() {
		Logger.println("SOLDANDO PIEZAS!!");
	}

	@Override
	public void run() {
		boolean isEnabled = false;
		while (true) {
			isEnabled = manager.jobToBeDone();
			if (isEnabled) {
				welding();
				sleepSeconds(currentJobTime);
				manager.setJobDone();
			} else {
				sleepSeconds(DEFAULT_WAIT_TIME);
			}
		}
	}

	private void sleepSeconds(long seconds) {
		try {
			sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		String modifiedGroup = manager.getModifiedGroupName();
		if (modifiedGroup.equals(WeldingManager.DELIVER_WELDMENT)) {
			try {
				setTimeToComplete(manager.getValueByName(WeldingManager.TIME_TO_WELD) + manager.getValueByName(WeldingManager.TIME_TO_WELD));
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startJob(manager);
			return;
		}
	}

	private void startJob(ParallelPortManager manager) {
		try {
			manager.setValueByNameAsBoolean(WeldingManager.ENABLE, true);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setTimeToComplete(long i) {
		currentJobTime = i;
	}
}
