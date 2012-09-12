package core.sections.ConveyorBelt;

import java.util.Random;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

/**
 * 
 * @author GelidElf
 * 
 */
public class ConveyorBeltSimulator extends Thread implements ParallelPortManagerObserver {

	private int[] contents = null;
	private ConveyorBeltManager manager = null;
	private int capacity = 0;
	private double tiempoEsperaEntrePiezas = 0;
	private double espacioDePieza = 0;
	private int realSpeed = 0;
	private int realCapacity = 0;
	private int realLength = 0;
	private int quantity = 0;

	private Random rand;
	private boolean autoFeed = false;

	public ConveyorBeltSimulator(String name, ConveyorBeltManager manager) {
		this.setName(name + "CBSimulator");
		this.manager = manager;
		try {
			contents = new int[this.manager.getValueByName(ConveyorBeltManager.CAPACITY)];
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		cleanContentsOfBelt();
		this.manager.registerObserver(this);
		rand = new Random(System.currentTimeMillis());
	}

	/**
	 * 
	 */
	private void move() {
		StringBuilder movement = new StringBuilder();
		movement.append ("Movement DEBUG \nbefore:");
		printContents(movement);
		movement.append ("\n");
		for (int i = 1; i < contents.length; i++) {
			contents[i - 1] = contents[i];
		}
		contents[contents.length - 1] = 0;
		try {
			if (contents[contents.length - 2] == 1){
				manager.resetBitGroupValue(ConveyorBeltManager.SENSOR_LOAD);
			}
			manager.setValueByName(ConveyorBeltManager.SENSOR_UNLOAD, contents[0]);

		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		movement.append ("after :");
		printContents(movement);
		//Logger.println(movement.toString());
	}

	private void printContents(StringBuilder builder) {
		for (int i = 0; i < contents.length; i++) {
			builder.append(" " + contents[i]);
		}
	}


	@Override
	public void run() {
		while (true) {
			try {
				if (running()) {
					setElementInInitialPositionInContents();
					if (beltHasElements()) {
						if (finalSensorInactive()) {
							move();
						} else {
							Logger.println("Paramos la cinta!");
							manager.setRunning(false);
						}
					}
					sleepExecution();
				}else{
					sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
	}

	private void sleepExecution() throws InterruptedException {
		sleep(new Double(tiempoEsperaEntrePiezas * 1000).longValue());
	}

	private void setElementInInitialPositionInContents() throws ParallelPortException {
		int initialSensorValue;
		if (autoFeed){
			initialSensorValue = rand.nextBoolean()?1:0;
		}else{
			initialSensorValue = manager.getValueByName(ConveyorBeltManager.SENSOR_LOAD);
		}
		contents[contents.length - 1] = initialSensorValue;
		quantity+=initialSensorValue;
	}

	private boolean beltHasElements() {
		return quantity > 0;
	}

	private void updateCapacity(int newCapacity) throws ParallelPortException {
		if (newCapacity != capacity) {
			capacity = newCapacity;
			int residualCuantity = changeCapacity(newCapacity);
			//manager.setValueByName(ConveyorBeltManager.QUANTITY, residualCuantity);
			quantity = residualCuantity;
		}
	}

	private boolean running() throws ParallelPortException {
		return manager.getValueByNameAsBoolean(ConveyorBeltManager.RUNNING);
	}

	private boolean finalSensorInactive(){
		return contents[0] == 0;
	}

	/**
	 * cleans the contents of the belt
	 */
	private void cleanContentsOfBelt() {
		for (int i = 0; i < capacity; i++) {
			contents[i] = 0;
		}
	}

	private int changeCapacity(int capacity) {
		int quantity = 0;
		if (capacity > contents.length) {
			int[] aux = new int[capacity];
			for (int i = 0; i < contents.length; i++) {
				aux[i] = contents[i];
				if (contents[i] == 1) {
					quantity++;
				}
			}
			contents = aux;
		} else if (capacity < contents.length) {
			int[] aux = new int[capacity];
			for (int i = 0; i < capacity; i++) {
				aux[i] = contents[i];
				if (contents[i] == 1) {
					quantity++;
				}
			}
			contents = aux;
		}
		return quantity;
	}

	public ConveyorBeltManager getManager() {
		return manager;
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		String modifiedGroupName = this.manager.getModifiedGroupName();
		try{
			if( (modifiedGroupName.equals(ConveyorBeltManager.SPEED)) ||
					(modifiedGroupName.equals(ConveyorBeltManager.CAPACITY)) ||
					(modifiedGroupName.equals(ConveyorBeltManager.LENGTH)) ){
				int valorPinVelocidad = manager.getValueByName(ConveyorBeltManager.SPEED);
				realSpeed = (valorPinVelocidad * 5) + 20;
				int valorPinCapacidad = manager.getValueByName(ConveyorBeltManager.CAPACITY);
				realCapacity = valorPinCapacidad + 50;
				try {
					updateCapacity(realCapacity);
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int valorPinLongitud = manager.getValueByName(ConveyorBeltManager.LENGTH);
				realLength = valorPinLongitud + 10;
				espacioDePieza = realLength / realCapacity;
				tiempoEsperaEntrePiezas = espacioDePieza / realSpeed / 60;
			}
		}catch(ParallelPortException e){
			e.printStackTrace();
		}

	}

	public void setAutoFeeed(boolean enable){
		autoFeed = enable;
	}

	public boolean getAutoFeed(){
		return autoFeed;
	}

}
