package core.sections.AssembyStation.States;

import core.sections.AssembyStation.AssemblyStation;
import core.sections.AssembyStation.AssemblyStationSimulator;

public class Idle implements AssemblyStationState{

	private AssemblyStationSimulator sim;
	public void execute(AssemblyStation AS) {
		if(AS.isGearNeeded()==false && AS.isAxisNeeded()==false && !AS.isComplete()){
			System.out.println("idle: ASSEMBLYNG");
			AS.setEmpty(false);
			sim = new AssemblyStationSimulator();
			sim.run();
			try {
				sim.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AS.setComplete(true);
		}
	}
}
