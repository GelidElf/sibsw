package core.sections.robot1;

import core.utilities.log.Logger;

public class RobotSimulator extends Thread {

	private int speed = 1;

	public void move() {
		Logger.println("MOVIENDO ROBOT!!");
	}

	@Override
	public void run() {
		move();
		try {
			sleep(5000 / speed);
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
