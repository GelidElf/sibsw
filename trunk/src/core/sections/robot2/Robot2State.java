package core.sections.robot2;

import master.MasterAutomata;
import master.MasterInput;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;


public class Robot2State implements State<Robot2Input> {

	private static final long serialVersionUID = 4242177858690525739L;

	private enum states implements AutomataStatesInternalImplementation<Robot2Input, Robot2State> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case START:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case DeliverAssembledPiece:
					return DeliveringAssembled;
				case DeliverWeldedPiece:
					return DeliveringWelded;
				case DeliverCheckedOkPiece:
					return DeliveringChecked;
				case DeliverCheckedNokPiece:
					return DeliveringChecked;
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
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case RESTART:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringAssembled(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case JobDone:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof MasterAutomata) {
						MasterAutomata master = (MasterAutomata) father;
						master.feedInput(MasterInput.AP_IN_WS, false);
					}
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringAssembledStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case RESTART:
					return DeliveringAssembled;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringWelded(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case JobDone:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof MasterAutomata) {
						MasterAutomata master = (MasterAutomata) father;
						master.feedInput(MasterInput.WP_IN_QS, false);
					}
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringWeldedStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case RESTART:
					return DeliveringWelded;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringChecked(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case JobDone:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof MasterAutomata) {
						MasterAutomata master = (MasterAutomata) father;
						master.feedInput(MasterInput.DeliveredChecked, false);
					}
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringCheckedStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
				switch (input) {
				case RESTART:
					return DeliveringChecked;
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
		public AutomataStatesInternalImplementation<Robot2Input, Robot2State> executeInternal(Robot2State currentState, Robot2Input input) {
			return this;
		}

	}

	private transient Robot2Automata automata;
	private states currentState;

	@Override
	public boolean execute(Robot2Input input) {
		states oldState = currentState;
		currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public Robot2State(Robot2Automata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public Robot2Automata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
