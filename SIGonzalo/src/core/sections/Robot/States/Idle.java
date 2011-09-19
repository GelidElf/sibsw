package core.sections.Robot.States;

import core.sections.Robot.Robot;
import core.sections.Robot.RobotSimulator;

public class Idle implements AutomataStateRobot {

	
	@Override
	public void execute(Robot robot) {
		if(robot.isUnloadAs()){
			RobotSimulator simulator = new RobotSimulator();
			simulator.start();
			try {
				simulator.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.setUnloadAs(false);
		}else if(robot.isGearReady()){
			RobotSimulator simulator = new RobotSimulator();
			simulator.start();
			try {
				simulator.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.setGearReady(false);
			System.out.println("Gear picked and deposited...");
		}else if(robot.isAxisReady()){
			RobotSimulator simulator = new RobotSimulator();
			simulator.start();
			try {
				simulator.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.setAxisReady(false);
			System.out.println("Axis picked and deposited...");
		}else{
			//doing nothing
		}

	}

}
