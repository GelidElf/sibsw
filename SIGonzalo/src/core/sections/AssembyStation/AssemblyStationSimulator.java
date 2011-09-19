package core.sections.AssembyStation;

import java.util.ArrayList;
import java.util.Random;

import core.sections.ParallelPort.ParallelPortObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;



public class AssemblyStationSimulator extends Thread{
	
	private int speed = 1;
	
	public void move(){
		System.out.println("ASSEMBLYNG!!!!!!!");
	}
	@Override
	public void run() {
		move();
		try {
			sleep(5000/speed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}



}
