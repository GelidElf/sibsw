package slave3;

import master.MasterInput;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.QualityStation.QualityStationInput;
import core.utilities.log.Logger;

public class Slave3State implements State<Slave3Input> {


	private static final long serialVersionUID = 5032185310071082226L;

	public static enum states implements AutomataStatesInternalImplementation<Slave3Input, Slave3State> {
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case START:
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
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER, MasterInput.QCS_LOADED);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.Load, true);
					return WaitingForQCSToFinish; //cambiar por un nuevo estado!
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
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.RESTART, true);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForQCSToFinish(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case QCS_FINISHED_OK:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER, MasterInput.MOVE_OK_FROM_QCS_TO_OKB);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.Empty, false);
					return WaitintForOkPieceToBeDelivered;
				case QCS_FINISHED_NOT_OK:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER, MasterInput.MOVE_NO_OK_FROM_QCS_TO_NO_OKB);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.Empty, false);
					return WaitintForNotOkPieceToBeDelivered;
				case NSTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.NSTOP, true);
					return WaitingForQCSToFinishStop;
				case ESTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.ESTOP, true);
					return WaitingForQCSToFinishStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForQCSToFinishStop(ModeEnum.NSTOP){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case RESTART:
					return WaitingForQCSToFinish;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitintForOkPieceToBeDelivered(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case OK_LOADED:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.loadSensorTrue, true);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.NSTOP, true);
					return WaitintForOkPieceToBeDeliveredStop;
				case ESTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.ESTOP, true);
					return WaitintForOkPieceToBeDeliveredStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}

		},
		WaitintForOkPieceToBeDeliveredStop(ModeEnum.NSTOP){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.RESTART, true);
					return WaitintForOkPieceToBeDelivered;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitintForNotOkPieceToBeDelivered(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case NOT_OK_LOADED:
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.loadSensorTrue, true);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.NSTOP, true);
					return WaitintForNotOkPieceToBeDeliveredStop;
				case ESTOP:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.ESTOP, true);
					return WaitintForNotOkPieceToBeDeliveredStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitintForNotOkPieceToBeDeliveredStop(ModeEnum.NSTOP){
			@Override
			public states executeInternal(Slave3State currentState, Slave3Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getNotOkBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getQualityStation().feedInput(QualityStationInput.RESTART, true);
					return WaitintForNotOkPieceToBeDelivered;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

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
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public Slave3Automata getAutomata() {
		return slave3;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
