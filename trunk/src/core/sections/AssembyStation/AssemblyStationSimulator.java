package core.sections.AssembyStation;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class AssemblyStationSimulator extends Thread implements ParallelPortManagerObserver{
	
	private AssemblyStationManager manager;
	
	public AssemblyStationSimulator(AssemblyStationManager manager) {
		this.manager = manager;
	}

	private int getAssemblingTime() {
		return manager.getBitGroupValue(AssemblyStationManager.ASSEMBLING_TIME);
	}

	@Override
	public void run() {
		super.run();
		try {
			Logger.println("Assembling");
			sleepExecution(getAssemblingTime());
			Logger.println("Finished assembling");
			manager.setValueByNameAsBoolean(AssemblyStationManager.AP_DETECTED, true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
	}

	private void sleepExecution(int velocity) throws InterruptedException {
		if (velocity == 0) {
			Logger.println("Speed set to 0");
		} else {
			
			sleep(1000 / velocity);
		}
	}

	@Override
	public void update(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(AssemblyStationManager.ENGAGE)){
			start();
		}
		
	}

	
	
	
	
}
