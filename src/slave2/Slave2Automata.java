package slave2;

import slave1.Slave1Input;
import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.model.AutomataContainer;
import core.model.ModelListener;
import core.sections.ConveyorBelt.ConveyorBeltAutomata;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.weldingstation.WeldingAutomata;
import core.sections.weldingstation.WeldingManager;
import core.sections.weldingstation.WeldingModel;

public class Slave2Automata extends AutomataContainer<Slave2Input, Slave2State, Slave2Model> implements ModelListener {

	private ConveyorBeltAutomata transferBelt;
	private WeldingAutomata weldingStation;

	public Slave2State getCurrentState() {
		return getModel().getState();
	}

	public Slave2Automata(Configuration conf) {
		super(null, new Slave2Model(), new SingleInboxCommunicationManager(CommunicationIds.SLAVE2, conf));
		setName("Slave2Automata");
		ConveyorBeltManager transferManager = new ConveyorBeltManager();
		transferManager.configure(10, 2);
		transferBelt = new ConveyorBeltAutomata("TRANSFER",this, transferManager, Slave2Input.ASSEMBLED_READY_FOR_PICKUP,Slave2Input.TRANSFER_CLEAR);
		getModel().setTransferBeltModel(transferBelt.getModel());
		transferBelt.getModel().addListener(this);

		weldingStation = new WeldingAutomata(this, new WeldingModel(), new OfflineCommunicationManager());
		weldingStation.getModel().addListener(this);
		getModel().setWeldingModel(weldingStation.getModel());

		getModel().setAutomata(this);
		getModel().addListener(this);
	}

	public ConveyorBeltAutomata getTransferBelt() {
		return transferBelt;
	}

	public void setTransferBelt(ConveyorBeltAutomata transferBelt) {
		this.transferBelt = transferBelt;
	}

	public WeldingAutomata getWeldingStation() {
		return weldingStation;
	}

	public void setWeldingStation(WeldingAutomata weldingStation) {
		this.weldingStation = weldingStation;
	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			Slave2Input input = (Slave2Input) message.getInputType();
			switch (input) {
			case TRANSFER_CLEAR:
				sendCommandMessage(CommunicationIds.SLAVE1, Slave1Input.TRANSPORT_READY);
				message.consumeMessage();
				break;
			case ASSEMBLED_IN_TRANSPORT:
				getTransferBelt().feedInput(ConveyorBeltInput.loadSensorTrue, false);
				message.consumeMessage();
				break;
			case ASSEMBLED_REMOVED_FROM_TCB:
				getTransferBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
				message.consumeMessage();
				break;
			default:
				message.setConsumed(getModel().getState().execute(input));
				break;
			}
		}
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			feedInput(Slave2Input.START, message.isUrgent());
			break;
		case NSTOP:
			feedInput(Slave2Input.NSTOP, message.isUrgent());
			break;
		case ESTOP:
			feedInput(Slave2Input.ESTOP, message.isUrgent());
			break;
		case RESTART:
			feedInput(Slave2Input.RESTART, message.isUrgent());
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			break;
		case HANDSHAKE:
			message.consumeMessage();
			break;
		default:
			message.didNotConsumeMessage();
			break;
		}

	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		getTransferBelt().startCommand();
		getWeldingStation().startCommand();
		this.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {

		ConfigurationParameters parameter = ConfigurationParameters.getEnum(attribute.getName());
		if (parameter != null) {
			try {
				switch (parameter) {
				case CB_TRANSFER_LENGTH:
					transferBelt.getManager().setValueByName(ConveyorBeltManager.LENGTH, getPinLength((Integer) attribute.getValue()));
					break;
				case CB_TRANSFER_SPEED:
					transferBelt.getManager().setValueByName(ConveyorBeltManager.SPEED, getPinSpeed((Integer) attribute.getValue()));
					break;
				case ACTIVATION_TIME_WS:
					weldingStation.getManager().setValueByName(WeldingManager.TIME_TO_WELD, (Integer) attribute.getValue());
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
	public void updateOnModelChange() {
		sendMessage(new Message("MODEL_UPDATE", CommunicationIds.MASTER, false, CommunicationMessageType.STATUS_UPDATE, null));
	}

	private int getPinSpeed(int valorVelocidad){
		int valorPinesSpeed = (valorVelocidad - 20) / 5;
		return valorPinesSpeed;
	}

	private int getPinLength(int valorLength){
		int valorPinesLen = (valorLength- 10);
		return valorPinesLen;
	}

}
