package core.sections.Robot.States;

import core.sections.Robot.Robot;
import core.sections.Robot.RobotSimulator;

public class Idle implements AutomataStateRobot {

	
	@Override
	public void execute(Robot robot) {
		if(robot.isUnloadAs()){
			robot.getSimulator().start();
			robot.setUnloadAs(false);
		}else if(robot.isGearReady()){
			RobotSimulator simulator = new RobotSimulator();
			simulator.start();
			try {
				robot.getSimulator().join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.setGearReady(false);
			robot.setDone(true);
			System.out.println("gear picked and deposited...");
		}else if(robot.isAxisReady()){
			robot.getSimulator().start();
			robot.setAxisReady(false);
		}else{
			//doing nothing
		}

	}

}
