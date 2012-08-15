package core.model;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

public abstract class AutomataContainer<T extends Enum<T>> extends Thread {

	protected Configuration conf;
	private CommunicationManager commManager;
	protected AutomataContainer<?> father;
	protected AutomataModel model;

	public AutomataContainer(AutomataContainer<?> father, AutomataModel model) {
		this.model = model;
		this.father = father;
	}

	public AutomataContainer(AutomataContainer<?> father, AutomataModel model, CommunicationManager commManager) {
		this.father = father;
		this.commManager = commManager;
		this.model = model;
	}

	public CommunicationManager getCommunicationManager() {
		return commManager;
	}

	public void injectMessage(Message message) {
		if (message == null) {
			return;
		}
		commManager.getInbox().add(message);
	}

	@Override
	public void run() {
		sendPeerInformation(); //TODO: Comprobar este cambio!
		super.run();
		while (true) {
			try {
				Message message = commManager.getInbox().getMessage();
				actualizarEstadoConModeloRemoto(message);
				consume(message);
			} catch (InterruptedException e) {
				Logger.println("error reading messages");
				e.printStackTrace();
			}
		}
	}

	private void sendPeerInformation() {
		Message message = new Message("IDENTIFICATION", CommunicationIds.BROADCAST, true, CommunicationMessageType.HANDSHAKE, null);
		message.setCurrentModel(model);
		sendMessage(message);
	}

	private void actualizarEstadoConModeloRemoto(Message message) {
		if (mensajeConModeloYDebemosActualizarConElCambio(message)) {
			//			model = message.getCurrentModel();
			updateWithModelFromMessage(message.getOwner(), message.getCurrentModel());
		}
	}

	private boolean mensajeConModeloYDebemosActualizarConElCambio(Message message) {
		boolean existeModeloEnMensaje = message.getCurrentModel() != null;
		return somosElMaster() && existeModeloEnMensaje;
	}

	private boolean somosElMaster() {
		return commManager.getOwner() == CommunicationIds.MASTER;
	}

	protected abstract void consume(Message currentMessage);

	protected abstract void startCommand();

	protected abstract void changeConfigurationParameter(Attribute attribute);

	/**
	 * Debe de ser sobrecargado en aquellos hijos que tengan que reaccionar cuando el estado del otro automata cambie (Master)
	 * @param commId 
	 * @param model
	 */
	protected void updateWithModelFromMessage(CommunicationIds commId, AutomataModel model) {
	}

	public void sendMessage(Message message) {
		message.setCurrentModel(model);
		commManager.sendMessage(message);
	}

	public synchronized void feedInput(T input, boolean isUrgent) {
		commManager.feed(createDummyMessageForInput(input, isUrgent));
	}

	protected Message createDummyMessageForInput(T input, boolean isUrgent) {
		Message message = new Message("FeededInputDummyMessage", getCommunicationManager().getOwner(), isUrgent, CommunicationMessageType.COMMAND, input);
		return message;
	}

}
