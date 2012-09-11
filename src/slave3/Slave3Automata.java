package slave3;

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

		qualityStation = new QualityStationAutomata(this, new QualityStationModel(), new OfflineCommunicationManager());
		qualityStation.getModel().addListener(this);
		getModel().setQualityStationModel(qualityStation.getModel());

		ConveyorBeltManager okManager = new ConveyorBeltManager();
		okManager.configure(10, 2);
		okBelt = new ConveyorBeltAutomata("OK",this, okManager, null,null);
		getModel().setOkBeltModel(okBelt.getModel());

		ConveyorBeltManager notOkManager = new ConveyorBeltManager();
		notOkManager.configure(10, 2);
		notOkBelt = new ConveyorBeltAutomata("NO_OK",this, notOkManager, null,null);
		getModel().setOkBeltModel(notOkBelt.getModel());

	}

	public void setInitialSettings(String settings) {

		//TODO: po hacerlo >.<
		//robot.setSpeed......
		//gearBelt.setLength,speed, capacity....
		//AS.setAssemblyDelay....

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
			// Input is a normal state command, use the state.
			message.setConsumed(getModel().getState().execute(input));
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
					okBelt.getManager().setBitGroupValue(ConveyorBeltManager.LENGTH, getPinLength((Integer) attribute.getValue()));
					break;
				case CB_WRONG_LENGTH:
					notOkBelt.getManager().setBitGroupValue(ConveyorBeltManager.LENGTH, getPinLength((Integer) attribute.getValue()));
					break;
				case CB_OK_SPEED:
					okBelt.getManager().setBitGroupValue(ConveyorBeltManager.SPEED, getPinSpeed((Integer) attribute.getValue()));
					break;
				case ACTIVATION_TIME_QCS:
					qualityStation.getManager().setBitGroupValue(QualityStationManager.ACTIVATION_TIME, (Integer) attribute.getValue());
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
