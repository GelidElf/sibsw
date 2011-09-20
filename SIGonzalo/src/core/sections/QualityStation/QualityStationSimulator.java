package core.sections.QualityStation;

import java.util.Random;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;


public class QualityStationSimulator  extends Thread implements ParallelPortObserver{

	private Random _random = null;
	private QualityStationManager _manager = null;
	private int result = 2;
	private int failurePercentage = 0;
	
	public QualityStationSimulator() {
		_random = new Random(System.currentTimeMillis());
		_manager = new QualityStationManager();
	}
	
	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);
	}

	@Override
	public void run() {
		while(true){
			try {
				failurePercentage = _manager.getValueByName(QualityStationManager.FAILURE_PERCENTAGE);
				if (_manager.getValueByName(QualityStationManager.ENABLED) == 1){
					result = (_random.nextInt(100) >= failurePercentage)?1:0;
					sleep(_manager.getValueByName(_manager.ACTIVATION_TIME)*1000);
				}
				_manager.setValueByName(QualityStationManager.RESULT,result);
				_manager.setValueByName(QualityStationManager.ENABLED,0);
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
		// TODO Auto-generated method stub
		return _manager;
	}
	
}
