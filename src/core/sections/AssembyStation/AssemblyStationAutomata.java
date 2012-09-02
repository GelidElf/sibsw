package core.sections.AssembyStation;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class AssemblyStationAutomata extends AutomataContainer<AssemblyStationInput, AssemblyStationState, AssemblyStationModel> implements ParallelPortManagerObserver {

	private AssemblyStationManager manager;
	private AssemblyStationSimulator simulator;

	public AssemblyStationAutomata(AutomataContainer<?, ?, ?> father, AssemblyStationManager manager) {
		super(father, new AssemblyStationModel(), new OfflineCommunicationManager());
		this.manager = manager;
		manager.registerObserver(this);
		simulator = new AssemblyStationSimulator(this.manager);
		getModel().setAutomata(this);
	}

	public static void main(String[] args) {
		AssemblyStationManager m = new AssemblyStationManager();
		m.configure(10);
		AssemblyStationAutomata atcb = new AssemblyStationAutomata(null, m);
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(AssemblyStationManager.ASSEMBLING_TIME, "32"));
		atcb.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atcb.injectMessage(mess2);
	}

	@Override
	protected void consume(Message message) {
		//TODO

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
			consume(createDummyMessageForInput(AssemblyStationInput.apDetectedFalse, false));
		}
		if (this.manager.isAxisDetected()) {
			consume(createDummyMessageForInput(AssemblyStationInput.axisDetectedTrue, false));
		} else {
			consume(createDummyMessageForInput(AssemblyStationInput.axisDetectedFalse, false));
		}
		if (this.manager.isGearDetected()) {
			consume(createDummyMessageForInput(AssemblyStationInput.gearDetectedTrue, false));
		} else {
			consume(createDummyMessageForInput(AssemblyStationInput.gearDetectedFalse, false));
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
