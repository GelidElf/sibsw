package core.model;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

public abstract class AutomataContainer<T extends Enum<T>> extends Thread {

	protected Configuration conf;
	private CommunicationManager commManager;
	protected AutomataContainer<?> father;

	public AutomataContainer(AutomataContainer<?> father) {
		this.father = father;
	}

	public AutomataContainer(AutomataContainer<?> father, CommunicationManager commManager) {
		this.father = father;
		this.commManager = commManager;
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
		super.run();
		while (true){
			try {
				consume(commManager.getInbox().getMessage());
			} catch (InterruptedException e) {
				Logger.println("error reading messages");
				e.printStackTrace();
			}
		}
	}

	protected abstract void consume(Message currentMessage);

	protected abstract void startCommand();

	protected abstract void changeConfigurationParameter(Attribute attribute);

	public void sendMessage(Message message) {
		commManager.sendMessage(message);
	}

	public synchronized void feedInput(T input, boolean isUrgent) {
		commManager.feed(createDummyMessageForInput(input, isUrgent));
	}

	protected Message createDummyMessageForInput(T input, boolean isUrgent) {
		Message message = new Message("dummyInputMessage", null, isUrgent, CommunicationMessageType.COMMAND, input);
		return message;
	}

}
