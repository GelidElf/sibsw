package slave3;

import master.MasterInput;
import core.gui.satuspanel.ModeEnum;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.QualityStation.QualityStationInput;

public class Slave3State implements State<Slave3Input> {

	private static final long serialVersionUID = 3510029429322490323L;

	public static enum states implements AutomataStatesInternalImplementation<Slave3Input, Slave3State> {
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case START:
					//FIXME: Make the feed input execute the start command
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.START, true);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case QCS_LOADED:
					currentState.getAutomata().sendMessage(new Message("ObjetoEnTransfer", CommunicationIds.MASTER, false, CommunicationMessageType.COMMAND, MasterInput.CP_IN_CB));
					return SOMETHING; //cambiar por un nuevo estado!
				case NSTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.NSTOP, true);
					return IdleStop;
				case ESTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.ESTOP, true);
					return IdleStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case RESTART:
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		SOMETHING(ModeEnum.NSTOP);

		@Override
		public states executeInternal(Slave3State currentState, Slave3Input input) {
			return this;
		}

		private ModeEnum mode;

		private states(ModeEnum mode) {
			this.mode = mode;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}
	}

	private states currentState;
	private transient Slave3Automata slave3;

	public Slave3State(Slave3Automata slave3) {
		this.slave3 = slave3;
		currentState = states.STARTED;
	}

	@Override
	public boolean execute(Slave3Input input) {
		states oldState = currentState;
		currentState = currentState.executeInternal(this, input);
		return oldState != currentState;
	}

	public Slave3Automata getAutomata() {
		return slave3;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
