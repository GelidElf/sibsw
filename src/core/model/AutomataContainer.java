package core.model;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

public abstract class AutomataContainer<INPUT extends Enum<INPUT>, STATE extends State<INPUT>, MODEL extends AutomataModel<INPUT, STATE>> extends Thread {

	protected Configuration conf;
	private CommunicationManager commManager;
	protected AutomataContainer<?, ?, ?> father;
	private MODEL model;

	public AutomataContainer(AutomataContainer<?, ?, ?> father, MODEL model, CommunicationManager commManager) {
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

	public abstract void startCommand();

	protected abstract void changeConfigurationParameter(Attribute attribute);

	/**
	 * Debe de ser sobrecargado en aquellos hijos que tengan que reaccionar
	 * cuando el estado del otro automata cambie (Master)
	 * 
	 * @param commId
	 * @param model
	 */
	protected void updateWithModelFromMessage(CommunicationIds commId, AutomataModel model) {
	}

	public void sendMessage(Message message) {
		message.setCurrentModel(model);
		commManager.sendMessage(message);
	}

	/**
	 * alimenta un input al automata, se usa para comunicar automatas del mismo
	 * slave, entrada de usuario y el resto de comunicacion que no sea a trav�s
	 * de mensajes
	 * 
	 * @param input
	 *            el input del automata a incluir en el inbox
	 * @param isUrgent
	 *            si es urgente
	 */
	public synchronized void feedInput(INPUT input, boolean isUrgent) {
		commManager.feed(createDummyMessageForInput(input, isUrgent));
	}

	protected Message createDummyMessageForInput(INPUT input, boolean isUrgent) {
		Message message = new Message("FeededInputDummyMessage", getCommunicationManager().getOwner(), isUrgent, CommunicationMessageType.COMMAND, input);
		return message;
	}

	/**
	 * @return the model
	 */
	public MODEL getModel() {
		return model;
	}

	public AutomataContainer<?, ?, ?> getFather() {
		return father;
	}

}
