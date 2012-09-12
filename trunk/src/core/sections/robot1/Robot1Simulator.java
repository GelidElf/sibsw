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
		setName("Robot1Simulator");
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
		try{
			if (modifiedGroup.equals(Robot1Manager.DELIVER_AXIS) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot1Manager.TIME_TO_AXIS_GEAR));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
			if (modifiedGroup.equals(Robot1Manager.DELIVER_GEAR) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot1Manager.TIME_TO_AXIS_GEAR));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
			if (modifiedGroup.equals(Robot1Manager.DELIVER_ASSEMBLED) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot1Manager.TIME_TO_TRANSPORT));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
		}catch (ParallelPortException e) {
			e.printStackTrace();
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
