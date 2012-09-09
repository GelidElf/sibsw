package core.sections.weldingstation;

import slave1.Slave1Automata;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;

public class WeldingState implements State<WeldingInput> {

	private enum states implements AutomataStatesInternalImplementation<WeldingInput, WeldingState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case START:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<WeldingInput, WeldingState> executeInternal(WeldingState currentState, WeldingInput input) {
				switch (input) {
				case DeliverWeldment:
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
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof Slave1Automata) {
						Slave1Automata slave1 = (Slave1Automata) father;
						slave1.feedInput(Slave1Input.AXIS_IN_AS, false);
					}
					return Idle;
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

	private WeldingAutomata automata;
	private states currentState;

	@Override
	public boolean execute(WeldingInput input) {
		states oldState = currentState;
		currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public WeldingState(WeldingAutomata automata) {
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
