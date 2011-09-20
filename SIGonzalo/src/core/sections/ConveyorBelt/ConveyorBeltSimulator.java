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
	private int speed = 1;
	private int running = 1;
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ConveyorBeltSimulator(){
		manager = new ConveyorBeltManager();
		contents = new ArrayList<Integer>();
	}
	
	public ConveyorBeltSimulator(ConveyorBeltManager m){
		manager = m;
		contents = new ArrayList<Integer>();
	}
	
	private void move(){
		System.out.print("before:");
		for(int i =0; i<contents.size();i++){
			System.out.print(" "+contents.get(i));
		}
		System.out.println();
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
			contents.set(contents.size()-1, 0);
		try {
			manager.setValueByName(manager.SENSOR_INITIAL, contents.get(contents.size()-1));
			manager.setValueByName(manager.SENSOR_FINISH, contents.get(0));
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("after :");
		for(int i =0; i<contents.size();i++){
			System.out.print(" "+contents.get(i));
		}
		System.out.println();
	}

	@Override
	public void run() {
		
		try {
			manager.setValueByName(manager.RUNNING, 1);
			for(int i = 0; i<capacity;i++){
				contents.add(0);
			}
		//	System.out.println("capacity: "+manager.getValueByName(ConveyorBeltManager.CAPACITY) );
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {	
//				int auxCapacity = manager.getValueByName(ConveyorBeltManager.CAPACITY); 
//				if (auxCapacity != capacity){
//					contents.set(contents.size(), changeCapacity(auxCapacity));
//					capacity = auxCapacity;
//				}
				if(contents.size()>0){
					contents.set(contents.size()-1, manager.getValueByName(manager.SENSOR_INITIAL));
					if (manager.getValueByName(manager.SENSOR_INITIAL) == 1)
						manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY)+1);
				//	System.out.println("running: " +manager.getValueByName(ConveyorBeltManager.RUNNING)+" f:"+ manager.getValueByName(ConveyorBeltManager.SENSOR_FINISH));
					if(running == 1 && manager.getValueByName(ConveyorBeltManager.SENSOR_FINISH) == 0){
				//		System.out.println("muevo la cinta!:"+manager.getValueByName(manager.VELOCITY));
						move();
						sleep(5000/speed);
					}else{
				//		System.out.println("NO muevo la cinta!");
						sleep(5000/speed);
					}
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

	@Override
	public void setParallelPortState(ParallelPortState state) {
		manager.setState(state);
	}

}
