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
		setName("AssemblyStationAutomata");
		this.manager = manager;
		manager.registerObserver(this);
		simulator = new AssemblyStationSimulator(this.manager);
		getModel().setAutomata(this);
	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			reactToInput((AssemblyStationInput) message.getInputType());
		}
	}

	private void reactToInput(AssemblyStationInput input) {
		getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			reactToInput(AssemblyStationInput.START);
			break;
		case NSTOP:
			reactToInput(AssemblyStationInput.NSTOP);
			break;
		case ESTOP:
			reactToInput(AssemblyStationInput.ESTOP);
			break;
		case RESTART:
			reactToInput(AssemblyStationInput.RESTART);
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			break;
		default:
			message.didNotConsumeMessage();
			break;
		}
	}

	@Override
	public void startCommand() {
		simulator.start();
		this.start();
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
		if (this.manager.isAPDetected()) {
			feedInput(AssemblyStationInput.JobDone, false);
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

	public void putGear() {
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

	public AssemblyStationManager getManager() {
		return manager;
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

}
