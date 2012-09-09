package core.sections.robot1;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot1Simulator extends Thread implements ParallelPortManagerObserver {

	private static final long DEFAULT_WAIT_TIME = 2;
	private long currentJobTime;
	private Robot1Manager manager;
	private String orden;

	public Robot1Simulator(Robot1Manager manager) {
		this.manager = manager;
		manager.registerObserver(this);
	}

	public void move() {
		Logger.println("MOVIENDO ROBOT: "+orden);
	}

	@Override
	public void run() {
		boolean isEnabled = false;
		while (true) {
			isEnabled = manager.jobToBeDone();
			if (isEnabled) {
				move();
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
		if (modifiedGroup.equals(Robot1Manager.DELIVER_AXIS)) {
			setTimeToComplete(manager.getBitGroupValue(Robot1Manager.TIME_TO_AXIS_GEAR));
			startJob(manager);
			orden = modifiedGroup;
			return;
		}
		if (modifiedGroup.equals(Robot1Manager.DELIVER_GEAR)) {
			setTimeToComplete(manager.getBitGroupValue(Robot1Manager.TIME_TO_AXIS_GEAR));
			startJob(manager);
			orden = modifiedGroup;
			return;
		}
		if (modifiedGroup.equals(Robot1Manager.DELIVER_ASSEMBLED)) {
			setTimeToComplete(manager.getBitGroupValue(Robot1Manager.TIME_TO_TRANSPORT));
			startJob(manager);
			orden = modifiedGroup;
			return;
		}
	}

	private void startJob(ParallelPortManager manager) {
		try {
			manager.setValueByNameAsBoolean(Robot1Manager.ENABLE, true);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setTimeToComplete(long i) {
		currentJobTime = i;
	}
}
