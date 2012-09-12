package master;

import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.MultipleInboxCommunicationManager;
import core.messages.OfflineCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.messages.enums.ReportValues;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.robot2.Robot2Automata;
import core.sections.robot2.Robot2Manager;
import core.sections.robot2.Robot2Model;

public class MasterAutomata extends AutomataContainer<MasterInput, MasterState, MasterModel> {

	private static final int NUMBEROFINBOXES = 2;
	private Robot2Automata robot;

	public MasterState getCurrentState() {
		return getModel().getState();
	}

	public MasterAutomata(Configuration conf) {
		super(null, MasterModel.getInstance(), new MultipleInboxCommunicationManager(CommunicationIds.MASTER, conf,
				NUMBEROFINBOXES));
		this.setName("MasterAutomata");
		robot = new Robot2Automata(this, new Robot2Model(), new OfflineCommunicationManager());
		//robot.getModel().addListener(this);
		getModel().setRobot2Model(robot.getModel());
		getModel().setAutomata(this);
		getModel().receiveSignal(ReportValues.SYSTEM_BOOTS);
	}

	@Override
	protected void consume(Message message) {
		message.consumeMessage();
		if (message.getType() == CommunicationMessageType.COMMAND) {
			MasterInput input = (MasterInput) message.getInputType();
			switch (input) {
			case START:
				sendBroadCastMessage(CommunicationMessageType.START);
				message.setConsumed(getModel().getState().execute(MasterInput.START));
				break;
			case NSTOP:
				getModel().receiveSignal(ReportValues.SYSTEM_STOPS);
				sendBroadCastMessage(CommunicationMessageType.NSTOP);
				message.setConsumed(getModel().getState().execute(MasterInput.NSTOP));
				break;
			case ESTOP:
				getModel().receiveSignal(ReportValues.SYSTEM_EMERGENCIES);
				sendBroadCastMessage(CommunicationMessageType.ESTOP);
				message.setConsumed(getModel().getState().execute(MasterInput.ESTOP));
				break;
			case RESUME:
				sendBroadCastMessage(CommunicationMessageType.RESTART);
				message.setConsumed(getModel().getState().execute(MasterInput.RESUME));
				break;
			default:
				message.setConsumed(getModel().getState().execute(input));
				break;
			}
		} else {
			if (message.getType() == CommunicationMessageType.REPORT) {

				message.setConsumed(true);
			}
			if (message.getType() == CommunicationMessageType.LOG_MESSAGE) {
				MasterModel.getInstance().putTextInBuffer(message.getOwner(),(String) message.getAttributeValue("MESSAGE"));
				message.setConsumed(true);
			}
		}

	}

	protected void sendBroadCastMessage(CommunicationMessageType messageType) {
		getCommunicationManager().sendMessage(
				new Message(messageType.name(), CommunicationIds.BROADCAST, true, messageType, null));
	}

	@Override
	public void startCommand() {
		this.start();
		getCommunicationManager().initialize();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		ConfigurationParameters parameter = ConfigurationParameters.getEnum(attribute.getName());
		if (parameter != null) {
			try {
				switch (parameter) {
				case PICK_TIME_ASSEMBLED:
					robot.getManager().setValueByName(Robot2Manager.TIME_TO_ASSEMBLED_P,
							(Integer) attribute.getValue());
					break;
				case TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS:
					robot.getManager().setValueByName(Robot2Manager.TIME_TO_WELDED,
							(Integer) attribute.getValue());
					break;
				case TRANSPORT_PLACE_TIME_WELDED:
					robot.getManager().setValueByName(Robot2Manager.TIME_TO_CB, (Integer) attribute.getValue());
					break;
				default:
					break;
				}
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void updateWithModelFromMessage(CommunicationIds commId, AutomataModel<?, ?> model) {
		MasterModel.getInstance().setModel(commId, model);
	}

	public void setRobot(Robot2Automata robot) {
		this.robot = robot;
	}

	public Robot2Automata getRobot() {
		return robot;
	}

}
