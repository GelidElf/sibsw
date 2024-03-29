package slave3;

import slave2.Slave2Input;
import core.aplication.Configuration;
import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.SingleInboxCommunicationManager;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.messages.enums.ReportValues;
import core.model.AutomataContainer;
import core.model.ModelListener;
import core.sections.ConveyorBelt.ConveyorBeltAutomata;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.sections.QualityStation.QualityStationAutomata;
import core.sections.QualityStation.QualityStationManager;
import core.sections.QualityStation.QualityStationModel;


public class Slave3Automata extends AutomataContainer<Slave3Input, Slave3State, Slave3Model> implements ModelListener {

	private ConveyorBeltAutomata okBelt;
	private ConveyorBeltAutomata notOkBelt;
	private QualityStationAutomata qualityStation;

	public Slave3State getCurrentState() {
		return getModel().getState();
	}

	public Slave3Automata(Configuration conf) {
		super(null, new Slave3Model(), new SingleInboxCommunicationManager(CommunicationIds.SLAVE3, conf));
		this.setName("Slave3Automata");
		qualityStation = new QualityStationAutomata(this, new QualityStationModel(), new OfflineCommunicationManager());
		qualityStation.getModel().addListener(this);
		getModel().setQualityStationModel(qualityStation.getModel());

		ConveyorBeltManager okManager = new ConveyorBeltManager();
		okManager.configure(10, 2);
		okBelt = new ConveyorBeltAutomata("OK",this, okManager, Slave3Input.OK_DELIVERED,Slave3Input.OK_CLEAR);
		getModel().setOkBeltModel(okBelt.getModel());
		okBelt.disableAutoFeed();
		okBelt.getModel().addListener(this);

		ConveyorBeltManager notOkManager = new ConveyorBeltManager();
		notOkManager.configure(10, 2);
		notOkBelt = new ConveyorBeltAutomata("NO_OK",this, notOkManager, Slave3Input.NOT_OK_DELIVERED,Slave3Input.NOT_OK_CLEAR);
		getModel().setNotOkBeltModel(notOkBelt.getModel());
		notOkBelt.disableAutoFeed();
		notOkBelt.getModel().addListener(this);

		getModel().setAutomata(this);
		getModel().addListener(this);
	}

	public ConveyorBeltAutomata getOkBelt() {
		return okBelt;
	}

	public void setOkBelt(ConveyorBeltAutomata okBelt) {
		this.okBelt = okBelt;
	}

	public ConveyorBeltAutomata getNotOkBelt() {
		return notOkBelt;
	}

	public void setNotOkBelt(ConveyorBeltAutomata notOkBelt) {
		this.notOkBelt = notOkBelt;
	}

	public QualityStationAutomata getQualityStation() {
		return qualityStation;
	}

	public void setQualityStation(QualityStationAutomata qualityStation) {
		this.qualityStation = qualityStation;
	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			Slave3Input input = (Slave3Input) message.getInputType();
			Message reportMessage;
			switch (input) {
			case QCS_EMPTY:
				sendCommandMessage(CommunicationIds.SLAVE2, Slave2Input.QCS_EMPTY);
				message.consumeMessage();
				break;
			case OK_DELIVERED:
				getOkBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
				message.consumeMessage();
				reportMessage = new Message(input.name(), CommunicationIds.MASTER, false, CommunicationMessageType.REPORT, null);
				reportMessage.addAttribute(ReportValues.OK_PIECES.name(),1);
				sendMessage(reportMessage);
				break;
			case NOT_OK_DELIVERED:
				getOkBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
				message.consumeMessage();
				reportMessage = new Message(input.name(), CommunicationIds.MASTER, false, CommunicationMessageType.REPORT, null);
				reportMessage.addAttribute(ReportValues.NOT_OK_PIECES.name(),1);
				sendMessage(reportMessage);
				break;
			default:
				// Input is a normal state command, use the state.
				message.setConsumed(getModel().getState().execute(input));
				break;
			}
		}
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			message.setConsumed(getModel().getState().execute(Slave3Input.START));
			break;
		case NSTOP:
			message.setConsumed(getModel().getState().execute(Slave3Input.NSTOP));
			break;
		case ESTOP:
			message.setConsumed(getModel().getState().execute(Slave3Input.ESTOP));
			break;
		case RESTART:
			message.setConsumed(getModel().getState().execute(Slave3Input.RESTART));
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

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		getOkBelt().startCommand();
		getNotOkBelt().startCommand();
		getQualityStation().startCommand();
		this.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		ConfigurationParameters parameter = ConfigurationParameters.getEnum(attribute.getName());
		if (parameter != null) {
			try {
				switch (parameter) {
				case CB_OK_LENGTH:
					okBelt.getManager().setValueByName(ConveyorBeltManager.LENGTH, getPinLength((Integer) attribute.getValue()));
					break;
				case CB_WRONG_LENGTH:
					notOkBelt.getManager().setValueByName(ConveyorBeltManager.LENGTH, getPinLength((Integer) attribute.getValue()));
					break;
				case CB_OK_SPEED:
					okBelt.getManager().setValueByName(ConveyorBeltManager.SPEED, getPinSpeed((Integer) attribute.getValue()));
					break;
				case ACTIVATION_TIME_QCS:
					qualityStation.getManager().setValueByName(QualityStationManager.ACTIVATION_TIME, (Integer) attribute.getValue());
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
