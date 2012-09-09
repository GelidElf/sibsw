package core.sections.ConveyorBelt;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;

public class ConveyorBeltState implements State<ConveyorBeltInput> {

	private static final long serialVersionUID = 3002955261150992615L;

	private enum states implements AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case START:
					currentState.getAutomata().getManager().setRunning(true);
					return Running;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Running(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case NSTOP:
					return RunningStop;
				case ESTOP:
					return RunningStop;
				case unloadSensorTrue:
					currentState.getAutomata().getManager().setRunning(false);
					Enum<?> jobDoneInput = currentState.getAutomata().getJobDoneInput();
					currentState.getAutomata().getFather().feedInputObject(jobDoneInput, false);
					return WaitingForPickup;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		RunningStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case RESTART:
					return Running;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForPickup(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case unloadSensorFalse:
					currentState.getAutomata().getManager().setSensorFinish(false);
					currentState.getAutomata().getManager().setRunning(true);
					return Running;
				case NSTOP:
					return WaitingForPickupStop;
				case ESTOP:
					return WaitingForPickupStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForPickupStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case RESTART:
					return WaitingForPickup;
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
		public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
				ConveyorBeltState currentState, ConveyorBeltInput input) {
			return this;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}

	}

	private states currentState;
	private transient ConveyorBeltAutomata automata;

	public ConveyorBeltState(ConveyorBeltAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public ConveyorBeltAutomata getAutomata() {
		return automata;
	}

	@Override
	public boolean execute(ConveyorBeltInput input) {
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
