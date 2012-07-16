package core.sections.AssembyStation;

import core.messages.Attribute;
import core.model.AutomataContainer;
import core.sections.AssembyStation.States.AutomataStateAssemblyStation;
import core.sections.AssembyStation.States.Idle;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATAssemblyStation extends AutomataContainer<ATAssemblyStationInput> implements ParallelPortManagerObserver{

	private AssemblyStationManager manager;
	private AssemblyStationSimulator simulator;
	private AutomataStateAssemblyStation currentState;
	
	public ATAssemblyStation(AutomataContainer<?> father, AssemblyStationManager manager) {
		super(father);
		this.manager = manager;	
		manager.registerObserver(this);
		currentState = AutomataStateAssemblyStation.createState(Idle.class, null);
		simulator = new AssemblyStationSimulator(this.manager);
		System.out.println("AT CB Created");
	}

	@Override
	protected void consume(ATAssemblyStationInput currentInput) {
		switch (currentInput) {
		case apDetectedFalse:
			currentState.apDetectedFalse();
			break;
		case axisDetectedFalse:
			currentState.axisDetectedFalse();
			break;
		case axisDetectedTrue:
			currentState.axisDetectedTrue();
			break;
		case apDetectedTrue:
			currentState.apDetectedTrue();
			break;
		case engage:
			currentState.engage();
			break;
		case estop:
			currentState.estop();
			break;
		case nstop:
			currentState.nstop();
			break;
		case gearDetectedFalse:
			currentState.gearDetectedFalse();
			break;
		case gearDetectedTrue:
			currentState.gearDetectedTrue();
			break;	
		case restart:
			currentState.restart();
			break;	
		}
		
	}

	@Override
	protected void begin() {
		simulator.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			if (attribute.getName().equals(AssemblyStationManager.ASSEMBLING_TIME)){
				manager.setValueByName(AssemblyStationManager.ASSEMBLING_TIME, Integer.parseInt(attribute.getValue()));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(ParallelPortManager manager) {
		if (!this.manager.isAPDetected()){
			inputStorage.add(ATAssemblyStationInput.apDetectedFalse);
		}
		if (this.manager.isAxisDetected()){
			inputStorage.add(ATAssemblyStationInput.axisDetectedTrue);
		}else{
			inputStorage.add(ATAssemblyStationInput.axisDetectedFalse);
		}
		if (this.manager.isGearDetected()){
			inputStorage.add(ATAssemblyStationInput.gearDetectedTrue);
		}else{
			inputStorage.add(ATAssemblyStationInput.gearDetectedFalse);
		}
		
	}

}
