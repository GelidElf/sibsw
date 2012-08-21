package core.messages;

import core.messages.enums.CommunicationIds;

public class OfflineCommunicationManager implements CommunicationManager {

	private Inbox inbox;
	
	public OfflineCommunicationManager() {
		inbox = new Inbox();
	}
	
	@Override
	public void sendMessage(Message message) {}

	@Override
	public Inbox getInbox() {
		return inbox;
	}

	@Override
	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}

	@Override
	public CommunicationIds getOwner() {
		return null;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void feed(Message message) {
		// TODO Auto-generated method stub
		inbox.add(message);
	}

	@Override
	public void clientDisconnected(CommunicationIds commId) {}

}
