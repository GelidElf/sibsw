package core.sections.robot1;

import slave1.Slave1Automata;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;

public class Robot1State implements State<Robot1Input> {

	private enum states implements AutomataStatesInternalImplementation<Robot1Input, Robot1State> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case START:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case DeliverAxis:
					return DeliveringAxis;
				case DeliverGear:
					return DeliveringGear;
				case DeliverAssembledPiece:
					return DeliveringAssembled;
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
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case RESTART:
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringAxis(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
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
		DeliveringAxisStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case RESTART:
					return DeliveringAxis;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringGear(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case JobDone:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof Slave1Automata) {
						Slave1Automata slave1 = (Slave1Automata) father;
						slave1.feedInput(Slave1Input.GEAR_IN_AS, false);
					}
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringGearStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case RESTART:
					return DeliveringGear;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringAssembled(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case JobDone:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof Slave1Automata) {
						Slave1Automata slave1 = (Slave1Automata) father;
						slave1.feedInput(Slave1Input.AS_IN_TRANSPORT, false);
					}
					return Idle;
				}
				return super.executeInternal(currentState, input);
			}
		},
		DeliveringAssembledStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
				switch (input) {
				case RESTART:
					return DeliveringAssembled;
				}
				return super.executeInternal(currentState, input);
			}
		};

		private ModeEnum mode;

		private states(ModeEnum mode) {
			this.mode = mode;
		}

		public ModeEnum getMode() {
			return mode;
		}

		@Override
		public AutomataStatesInternalImplementation<Robot1Input, Robot1State> executeInternal(Robot1State currentState, Robot1Input input) {
			return this;
		}

	}

	private Robot1Automata automata;
	private states currentState;

	@Override
	public void execute(Robot1Input input) {
		currentState.executeInternal(this, input);
	}

	public Robot1State(Robot1Automata automata) {
		this.automata = automata;
	}

	public Robot1Automata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
