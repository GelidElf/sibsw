package core.sections.weldingstation;

import slave2.Slave2Automata;
import slave2.Slave2Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class WeldingState implements State<WeldingInput> {

	private static final long serialVersionUID = -7702717114957704622L;

	private enum states implements AutomataStatesInternalImplementation<WeldingInput, WeldingState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case START:
					Slave2Automata father = (Slave2Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave2Input.WS_EMPTY, false);
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case AssemblyLoaded:
					try {
						currentState.getAutomata().getManager().setValueByNameAsBoolean(WeldingManager.DELIVER_WELDMENT, false);
					} catch (ParallelPortException e) {
						e.printStackTrace();
					}
					return DeliveringWeldment;
				case NSTOP:
					return IdleStop;
				case ESTOP:
					return IdleStop;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case RESTART:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringWeldment(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case JobDone:
					Slave2Automata father = (Slave2Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave2Input.WELDMENT_READY, false);
					return Idle;
				case NSTOP:
					return DeliveringWeldmentStop;
				case ESTOP:
					return DeliveringWeldmentStop;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringWeldmentStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case RESTART:
					return DeliveringWeldment;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForWeldmentToBeRemoved(ModeEnum.IDLE){
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case WeldmentRemoved:
					Slave2Automata father = (Slave2Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave2Input.WS_EMPTY, false);
					return Idle;
				case NSTOP:
					return WaitingForWeldmentToBeRemovedStop;
				case ESTOP:
					return WaitingForWeldmentToBeRemovedStop;
				}
				return super.executeInternal(currentState, input);
			}
		},WaitingForWeldmentToBeRemovedStop(ModeEnum.NSTOP){
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case RESTART:
					return WaitingForWeldmentToBeRemoved;
				}
				return super.executeInternal(currentState, input);
			}
		};

		private ModeEnum mode;

		private states(ModeEnum mode) {
			this.mode = mode;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}

		@Override
		public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
			return this;
		}
	}

	private transient WeldingAutomata automata;
	private states currentState;

	@Override
	public boolean execute(WeldingInput input) {
		states oldState = currentState;
		currentState = (states) currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public WeldingState(WeldingAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public WeldingAutomata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
