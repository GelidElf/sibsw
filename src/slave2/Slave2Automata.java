package slave2;

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
		ConveyorBeltManager transferManager = new ConveyorBeltManager();
		transferManager.configure(10, 2);
		transferBelt = new ConveyorBeltAutomata(this, transferManager, Slave2Input.ASSEMBLED_READY);
		getModel().setTransferBeltModel(transferBelt.getModel());
		
		weldingStation = new WeldingAutomata(this, new WeldingModel(), new OfflineCommunicationManager());
		weldingStation.getModel().addListener(this);
		getModel().setWeldingModel(weldingStation.getModel());
		
		getModel().setAutomata(this);
		getModel().addListener(this);
	}

	public void setInitialSettings(String settings) {

		//TODO: po hacerlo >.<
		//robot.setSpeed......
		//gearBelt.setLength,speed, capacity....
		//AS.setAssemblyDelay....

	}

	public ConveyorBeltAutomata getTransferBelt() {
		return transferBelt;
	}

	public void setTransferBelt(ConveyorBeltAutomata transferBelt) {
		this.transferBelt = transferBelt;
	}

	public WeldingAutomata getAssemblyStation() {
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
			//If input is a shortcut, try it here
			switch (input) {
			case LOAD_ASSEMBLED_TCB:
				transferBelt.disableAutoFeed();
				message.consumeMessage();
				break;
			case UNLOAD_ASSEMBLED_TCB:
				transferBelt.disableAutoFeed();
				message.consumeMessage();
				break;
			case ASSEMBLED_READY:
				//!!!!!!!!!!!!!!!!!!!!! COMPLETAR
				break;
			case WELDED_PIECE:
				//!!!!!!!!!!!!!!!!!!!!! COMPLETAR
				break;
			default:
				getModel().getState().execute(input);
				break;
			}
		}

	}

	private void reaccionaPorTipoDeMensaje(Message message) {
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
		default:
			break;
		}

	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	@Override
	public void startCommand() {
		getCommunicationManager().initialize();
		start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
			
		ConfigurationParameters parameter = ConfigurationParameters.getEnum(attribute.getName());
		if (parameter != null) {
			try {
				
				/*
				 * Falta: CB_TRANSFER_LENGTH!!!!!!
				 */
				switch (parameter) {
				case CB_TRANSFER_SPEED:
					transferBelt.getManager().setBitGroupValue(ConveyorBeltManager.SPEED, (Integer) attribute.getValue());
					break;
				case ACTIVATION_TIME_WS:
					transferBelt.getManager().setBitGroupValue(WeldingManager.TIME_TO_WELD, (Integer) attribute.getValue());
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

}
