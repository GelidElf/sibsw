package core.sections.ConveyorBelt;

import java.util.ArrayList;
import java.util.Random;

import core.sections.ParallelPort.ParallelPortObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;



public class ConveyorBeltSimulator extends Thread implements ParallelPortObserver{
	
	private ArrayList<Integer> contents = null;
	private ConveyorBeltManager manager = null;
	private int capacity = 0;
	
	public ConveyorBeltSimulator(){
		manager = new ConveyorBeltManager();
	}
	
	private void move(){
		if (contents.get(0) == 1)
			try {
				manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY)-1);
			} catch (ParallelPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		while(true){
			try {
				int auxCapacity = manager.getValueByName(ConveyorBeltManager.CAPACITY); 
				if (auxCapacity != capacity){
					contents.set(contents.size(), changeCapacity(auxCapacity));
					capacity = auxCapacity;
				}
				contents.set(contents.size(), manager.getValueByName(manager.SENSOR_INITIAL));
				if (manager.getValueByName(manager.SENSOR_INITIAL) == 1)
					manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY)+1);
				if(manager.getValueByName(ConveyorBeltManager.RUNNING) == 1){
					move();
					sleep(5000/manager.getValueByName(manager.VELOCITY));
				}else{
					sleep(5000/manager.getValueByName(manager.VELOCITY));
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
		manager.setState(state);
	}
	
	private int changeCapacity(int capacity){
		int quantity = 0;
		if (capacity > contents.size()){
			ArrayList <Integer> aux = new ArrayList<Integer>(capacity);
			for (int i = 0; i<contents.size();i++){
				aux.set(i,contents.get(i));
				if (contents.get(i) == 1)
					quantity++;
			}
			contents = aux;
		}else if (capacity < contents.size()){
			ArrayList <Integer> aux = new ArrayList<Integer>(capacity);
			for (int i = 0; i<capacity;i++){
				aux.set(i,contents.get(i));
				if (contents.get(i) == 1)
					quantity++;
			}
			contents = aux;
		}
		return quantity;
	}
	
	public ConveyorBeltManager getManager(){
		return manager;
	}

}
