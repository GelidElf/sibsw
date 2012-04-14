package core.sections.QualityStation;

import java.util.Random;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;


public class QualityStationSimulator  extends Thread implements ParallelPortManagerObserver{

	private Random _random = null;
	private QualityStationManager manager = null;
	private int result = 2;
	private int failurePercentage = 0;
	
	public QualityStationSimulator(QualityStationManager manager) {
		_random = new Random(System.currentTimeMillis());
		this.manager = manager;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				failurePercentage = manager.getValueByName(QualityStationManager.FAILURE_PERCENTAGE);
				if (manager.getValueByName(QualityStationManager.ENABLED) == 1){
					result = (_random.nextInt(100)>=failurePercentage)?1:0;
					sleep(manager.getValueByName(manager.ACTIVATION_TIME)*1000);
				}
				manager.setValueByName(QualityStationManager.RESULT,result);
				manager.setValueByName(QualityStationManager.ENABLED,0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public ParallelPortManager getManager() {
		return manager;
	}

	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

}
