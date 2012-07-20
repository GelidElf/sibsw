package master;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.MultipleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;

public class ATMaster extends AutomataContainer<ATMasterInput> {

	private static final int NUMBEROFINBOXES = 1;

	public ATMaster(Configuration conf) {
		super(null,new MultipleInboxCommunicationManager(CommunicationIds.MASTER,conf,NUMBEROFINBOXES));
	}

	@Override
	protected void consume(ATMasterInput currentInput) {
		switch (currentInput) {
		case START:
			sendBroadCastMessage(CommunicationMessageType.START);
			break;
		case NSTOP:
			sendBroadCastMessage(CommunicationMessageType.NSTOP);
			break;
		case ESTOP:
			sendBroadCastMessage(CommunicationMessageType.ESTOP);
			break;
		case RESUME:
			sendBroadCastMessage(CommunicationMessageType.RESUME);
			break;
		default:
			break;
		}
		
	}

	protected void sendBroadCastMessage(CommunicationMessageType messageType) {
		getCommunicationManager().sendMessage(new Message(messageType.name(), null, true, messageType, null));
	}

	@Override
	protected void begin() {
		getCommunicationManager().initialize();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}
	
}
