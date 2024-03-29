package core.sections.robot2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class Robot2Simulator extends Thread implements ParallelPortManagerObserver {

	private static final long DEFAULT_WAIT_TIME = 2;
	private long currentJobTime;
	private Robot2Manager manager;
	private String orden;

	public Robot2Simulator(Robot2Manager manager) {
		this.manager = manager;
		setName("Robot2Simulator");
		manager.registerObserver(this);
	}

	public void move() {
		Logger.println("MOVIENDO ROBOT: " + orden);
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
			if (modifiedGroup.equals(Robot2Manager.DELIVER_ASSEMBLED_PIECE) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot2Manager.TIME_TO_ASSEMBLED_P));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
			if (modifiedGroup.equals(Robot2Manager.DELIVER_WELDED_PIECE) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot2Manager.TIME_TO_WELDED));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
			if (modifiedGroup.equals(Robot2Manager.DELIVER_CHECKED_PIECE) && manager.getValueByNameAsBoolean(modifiedGroup)) {
				setTimeToComplete(manager.getValueByName(Robot2Manager.TIME_TO_CB));
				startJob(manager);
				orden = modifiedGroup;
				return;
			}
		}
		catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	private void startJob(ParallelPortManager manager) {
		try {
			manager.setValueByNameAsBoolean(Robot2Manager.ENABLE, true);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setTimeToComplete(long i) {
		currentJobTime = i;
	}

}
