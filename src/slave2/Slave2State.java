package slave2;

import master.MasterInput;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.weldingstation.WeldingInput;
import core.utilities.log.Logger;

public class Slave2State implements State<Slave2Input> {

	private static final long serialVersionUID = 7025769611332442118L;

	public static enum states implements AutomataStatesInternalImplementation<Slave2Input, Slave2State> {
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case START:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.START, true);
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE1, Slave1Input.TRANSPORT_READY);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case ASSEMBLED_READY_FOR_PICKUP:

					return WaitingForWeldingToBeEmpty;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return IdleStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return IdleStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWeldingToBeEmpty(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WS_EMPTY:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER,MasterInput.MOVE_AS_FROM_TCB_TO_WS);
					return WaitingForWSToBeLoaded;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return WaitingForWeldingToBeEmptyStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return WaitingForWeldingToBeEmptyStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWeldingToBeEmptyStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return WaitingForWeldingToBeEmpty;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWSToBeLoaded(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WELDING_STATION_LOADED:
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.AssemblyLoaded, false);
					return WaitingForWSToFinish;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return WaitingForWSToBeLoadedStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return WaitingForWSToBeLoadedStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWSToBeLoadedStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return WaitingForWSToBeLoaded;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWSToFinish(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WELDMENT_READY:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER, MasterInput.MOVE_WP_FROM_WS_TO_QCS);
					return WaitingForQCSToBeEmpty;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return WaitingForWSToFinishStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return WaitingForWSToFinishStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWSToFinishStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return WaitingForWSToFinish;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForQCSToBeEmpty(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case QCS_EMPTY:
					return WaitForNOtificationOfWSEmpty;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return WaitingForQCSToBeEmptyStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return WaitingForQCSToBeEmptyStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForQCSToBeEmptyStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return WaitingForQCSToBeEmpty;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitForNOtificationOfWSEmpty(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WELDMENT_REMOVED:
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.WeldmentRemoved, false);
					return Idle;
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return WaitForNOtificationOfWSEmptyStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return WaitForNOtificationOfWSEmptyStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitForNOtificationOfWSEmptyStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.RESTART, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.RESTART, true);
					return WaitForNOtificationOfWSEmpty;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

		@Override
		public states executeInternal(Slave2State currentState, Slave2Input input) {
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
	private transient Slave2Automata slave2;

	public Slave2State(Slave2Automata slave2) {
		this.slave2 = slave2;
		currentState = states.STARTED;
	}

	@Override
	public boolean execute(Slave2Input input) {
		states oldState = currentState;
		currentState = currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public Slave2Automata getAutomata() {
		return slave2;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
