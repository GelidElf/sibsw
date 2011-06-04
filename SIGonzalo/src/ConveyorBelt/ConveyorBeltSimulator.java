package ConveyorBelt;

import java.util.ArrayList;
import java.util.Random;

import ParallelPort.ParallelPortObserver;
import ParallelPort.ParallelPortState;
import ParallelPort.Utils.ParallelPortException;


public class ConveyorBeltSimulator extends Thread implements ParallelPortObserver{
	
	private ArrayList<Integer> contents = null;
	private ConveyorBeltManager manager = null;
	
	public ConveyorBeltSimulator(){
		manager = new ConveyorBeltManager();
	}
	
	private void move(){
		for(int i =1; i<contents.size();i++){
			contents.set(i-1, contents.get(i));
		}
		contents.set(contents.size(), 0);
		try {
			manager.setValueByName(manager.SENSOR_INITIAL, contents.get(contents.size()));
			manager.setValueByName(manager.SENSOR_FINISH, contents.get(0));
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				contents.set(contents.size(), manager.getValueByName(manager.SENSOR_INITIAL));
				if(manager.getValueByName(ConveyorBeltManager.RUNNING) == 1){
					move();
					sleep(1000/manager.getValueByName(manager.VELOCITY));
					manager.update();
				}else{
					sleep(1000/manager.getValueByName(manager.VELOCITY));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(ParallelPortState state) {
		// TODO Auto-generated method stub
		manager.setState(state);
	}
	


}
