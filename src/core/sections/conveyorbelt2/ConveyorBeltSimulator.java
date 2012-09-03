package core.sections.conveyorbelt2;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ConveyorBeltSimulator extends Thread implements ParallelPortManagerObserver {

	private final int DEFAULT_CAPACITY = 10;
	
	private ConveyorBeltManager manager;
	private boolean[] contents;

	private int currentCapacity;

	public ConveyorBeltSimulator(ConveyorBeltManager manager) {
		this.manager = manager;
		this.contents = new boolean[DEFAULT_CAPACITY];
	}

	public ConveyorBeltSimulator() {
		this.manager = new ConveyorBeltManager();
	}

	@Override
	public void run() {
		super.run();
		while (true){
			updateCapacityIfNeccesary();
			if (manager.isEnabled()){
				move();
			}
			//Wait
		}
	}
	

	private void updateCapacityIfNeccesary() {
		int currentConfiguredCapacity = manager.getCapacity();
		int newQuantity = 0;
		if (currentConfiguredCapacity != currentCapacity){
			newQuantity = updateCapacityAndGetQuantity(currentConfiguredCapacity, newQuantity);
			try {
				manager.setValueByName(ConveyorBeltManager.QUANTITY, newQuantity);
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
	}

	private int updateCapacityAndGetQuantity(int currentConfiguredCapacity, int newQuantity) {
		if (currentConfiguredCapacity > currentCapacity) {
			boolean[] auxContents = new boolean[currentConfiguredCapacity];
			for (int i = 0; i < currentConfiguredCapacity; i++) {
				auxContents[i] = contents[i];
				if (contents[i]) {
					newQuantity++;
				}
			}

		} else {
			if (currentConfiguredCapacity < currentCapacity) {
				boolean[] auxContents = new boolean[currentConfiguredCapacity];
				for (int i =0;i<currentConfiguredCapacity;i++){
					auxContents[i] = contents[i];
					if (contents[i]) {
						newQuantity++;
					}
				}
			}
		}
		return newQuantity;
	}

	private void move() {
		
		
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(ConveyorBeltManager.ENABLE)) {
			
		}

	}

}
