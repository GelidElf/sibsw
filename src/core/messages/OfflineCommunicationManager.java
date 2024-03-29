package core.messages;

import core.messages.enums.CommunicationIds;

/**
 * Dummy communication manager, just includes the Inbox
 * 
 * @author GelidElf
 * 
 */
public class OfflineCommunicationManager implements CommunicationManager {

	private Inbox inbox;

	public OfflineCommunicationManager() {
		inbox = new Inbox("SECTION");
	}

	@Override
	public void sendMessage(Message message) {
	}

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
	public void clientDisconnected(CommunicationIds commId) {
	}

	@Override
	public void setDisconnectInProgress(boolean disconnectInProgress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(String text) {
		// TODO Auto-generated method stub

	}

}
