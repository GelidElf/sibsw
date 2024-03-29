package master;


import slave2.Slave2Input;
import slave3.Slave3Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.robot2.Robot2Input;
import core.utilities.log.Logger;

public class MasterState implements State<MasterInput> {

	private static final long serialVersionUID = 4405083677911400643L;

	private enum states implements AutomataStatesInternalImplementation<MasterInput, MasterState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(
					MasterState currentState, MasterInput input) {
				switch (input) {
				case START:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.START, true);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case MOVE_AS_FROM_TCB_TO_WS:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE2,Slave2Input.ASSEMBLED_REMOVED_FROM_TCB);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverAssembledPiece, false);
					return MovingASToWS;
				case MOVE_WP_FROM_WS_TO_QCS:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE2,Slave2Input.WS_EMPTY);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverWeldedPiece, false);
					return MovingWPToQCS;
				case MOVE_OK_FROM_QCS_TO_OKB:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE3,Slave3Input.QCS_EMPTY);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverCheckedOkPiece, false);
					return MovingOk;
				case MOVE_NO_OK_FROM_QCS_TO_NO_OKB:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE3,Slave3Input.QCS_EMPTY);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverCheckedNokPiece, false);
					return MovingNoOk;
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.NSTOP, true);
					return IdleStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.ESTOP, true);
					return IdleStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.RESTART, true);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingASToWS(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case AP_IN_WS:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE2,Slave2Input.WELDING_STATION_LOADED);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.NSTOP, true);
					return MovingASToWSStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.ESTOP, true);
					return MovingASToWSStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingASToWSStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.RESTART, true);
					return MovingASToWS;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingWPToQCS(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case WP_IN_QS:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE2,Slave2Input.WELDMENT_REMOVED);
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE3,Slave3Input.QCS_LOADED);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.NSTOP, true);
					return MovingWPToQCSStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.ESTOP, true);
					return MovingWPToQCSStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingWPToQCSStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.RESTART, true);
					return MovingWPToQCS;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		}, MovingOk(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case DeliveredChecked:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE3,Slave3Input.OK_LOADED);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.NSTOP, true);
					return MovingOkStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.ESTOP, true);
					return MovingOkStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		}, MovingOkStop(ModeEnum.NSTOP){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.RESTART, true);
					return MovingOk;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingNoOk(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case DeliveredChecked:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE3,Slave3Input.NOT_OK_LOADED);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.NSTOP, true);
					return MovingOkStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.ESTOP, true);
					return MovingOkStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		MovingNoOkStop(ModeEnum.NSTOP){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.RESTART, true);
					return MovingOk;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

		private ModeEnum mode;

		private states(ModeEnum mode) {
			this.mode = mode;
		}

		@Override
		public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState,
				MasterInput input) {
			return this;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}

	}

	private states currentState;
	private transient MasterAutomata automata;

	public MasterState(MasterAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public MasterAutomata getAutomata() {
		return automata;
	}

	@Override
	public boolean execute(MasterInput input) {
		states oldState = currentState;
		currentState = (states) currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
