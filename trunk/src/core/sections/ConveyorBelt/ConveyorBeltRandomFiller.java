package core.sections.ConveyorBelt;

import java.util.Random;

public class ConveyorBeltRandomFiller extends Thread {

	private ConveyorBeltManager manager;
	private Random rand = new Random(System.currentTimeMillis());

	public ConveyorBeltRandomFiller(ConveyorBeltManager manager){
		this.manager = manager;
	}
	
	
	@Override
	public void run() {
		super.run();
		if (!manager.isSensorInitial()) {
			if (rand.nextBoolean()) {
				manager.setSensorInitial(true);
				// System.out.println("sensor = 1");
			} else {
				try {
					/*
					 * NOS TENEMOS QUE DORMIR EL MISMO TIEMPO QUE EL
					 * SIMULADOR, si no, en cuanto el simulador se
					 * duerma, el random hace 1000000 intentos y acaba
					 * por salir true. Esto nos lleva a que SIEMPRE
					 * soltamos un gear/axis, o lo que es lo mismo, la
					 * cinta siempre est� llena
					 */
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
