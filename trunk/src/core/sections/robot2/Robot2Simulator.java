package core.sections.robot2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class Robot2Simulator extends Thread implements ParallelPortManagerObserver {

	private Robot2Manager _manager = null;
	
	public Robot2Simulator(){
		_manager = new Robot2Manager();
	}
	
	public ParallelPortManager getManager() {
		return _manager;
	}

	@Override
	public void run() {
		while(true){
			try {
				if(_manager.getValueByName(Robot2Manager.ENABLED) == 1){
					sleep(_manager.getValueByName(Robot2Manager.TIME_WORKING)*1000);
					_manager.setValueByName(Robot2Manager.ENABLED,0);
				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

}
