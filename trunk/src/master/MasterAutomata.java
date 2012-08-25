package master;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.MultipleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.model.AutomataModel;

public class MasterAutomata extends AutomataContainer<MasterInput, MasterState, MasterModel> {

	private static final int NUMBEROFINBOXES = 1;

	public MasterAutomata(Configuration conf) {
		super(null, MasterModel.getInstance(), new MultipleInboxCommunicationManager(CommunicationIds.MASTER, conf, NUMBEROFINBOXES));
	}

	@Override
	protected void consume(Message message) {
		if (message.getType() == CommunicationMessageType.COMMAND) {
			switch ((MasterInput) message.getInputType()) {
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
				sendBroadCastMessage(CommunicationMessageType.RESTART);
				break;
			default:
				break;
			}
		}

	}

	protected void sendBroadCastMessage(CommunicationMessageType messageType) {
		getCommunicationManager().sendMessage(new Message(messageType.name(), CommunicationIds.BROADCAST, true, messageType, null));
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		this.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateWithModelFromMessage(CommunicationIds commId, AutomataModel model) {
		MasterModel.getInstance().setModel(commId, model);
	}

}
