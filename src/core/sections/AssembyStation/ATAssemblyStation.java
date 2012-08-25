package core.sections.AssembyStation;

import core.gui.satuspanel.ModeEnum;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.AssembyStation.States.AutomataStateAssemblyStation;
import core.sections.AssembyStation.States.Idle;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATAssemblyStation extends AutomataContainer<ATAssemblyStationInput, AssemblyStationState, AssemblyStationModel> implements ParallelPortManagerObserver {

	private AssemblyStationManager manager;
	private AssemblyStationSimulator simulator;
	private AutomataStateAssemblyStation currentState;

	public ATAssemblyStation(AutomataContainer<?, ?, ?> father, AssemblyStationManager manager) {
		super(father, new AssemblyStationModel(), new OfflineCommunicationManager());
		this.manager = manager;
		manager.registerObserver(this);
		currentState = AutomataStateAssemblyStation.createState(Idle.class, null);
		simulator = new AssemblyStationSimulator(this.manager);
	}

	public static void main(String[] args) {
		AssemblyStationManager m = new AssemblyStationManager();
		m.configure(10);
		ATAssemblyStation atcb = new ATAssemblyStation(null, m);
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(AssemblyStationManager.ASSEMBLING_TIME, "32"));
		atcb.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atcb.injectMessage(mess2);
	}

	@Override
	protected void consume(Message message) {
		switch ((ATAssemblyStationInput) message.getInputType()) {
		case START:
			getModel().setCurrentMode(ModeEnum.IDLE);
			break;
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
	public void startCommand() {
		simulator.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			if (attribute.getName().equals(AssemblyStationManager.ASSEMBLING_TIME)) {
				manager.setValueByName(AssemblyStationManager.ASSEMBLING_TIME, Integer.parseInt((String) attribute.getValue()));
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
	public void updateFromPortManager(ParallelPortManager manager) {
		if (!this.manager.isAPDetected()) {
			consume(createDummyMessageForInput(ATAssemblyStationInput.apDetectedFalse, false));
		}
		if (this.manager.isAxisDetected()) {
			consume(createDummyMessageForInput(ATAssemblyStationInput.axisDetectedTrue, false));
		} else {
			consume(createDummyMessageForInput(ATAssemblyStationInput.axisDetectedFalse, false));
		}
		if (this.manager.isGearDetected()) {
			consume(createDummyMessageForInput(ATAssemblyStationInput.gearDetectedTrue, false));
		} else {
			consume(createDummyMessageForInput(ATAssemblyStationInput.gearDetectedFalse, false));
		}

	}

	public void putAxis() {
		try {
			manager.setValueByNameAsBoolean(AssemblyStationManager.AXIS_DETECTED, true);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void putEngranaje() {
		try {
			manager.setValueByNameAsBoolean(AssemblyStationManager.GEAR_DETECTED, true);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sacaPieza() {
		try {
			manager.setValueByNameAsBoolean(AssemblyStationManager.AP_DETECTED, false);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
