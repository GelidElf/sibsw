package core.sections.AssembyStation;

import slave1.Slave1Automata;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;

public class AssemblyStationState implements State<AssemblyStationInput> {

	private static final long serialVersionUID = 6259939714628916223L;

	private enum states implements AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case START:
					Slave1Automata father = (Slave1Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave1Input.AS_EMPTY, false);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case AxisFeeded:
					currentState.getAutomata().putAxis();
					return WithAxis;
				case GearFeeded:
					currentState.getAutomata().putGear();
					return WithGear;
				case NSTOP:
					return IdleStop;
				case ESTOP:
					return IdleStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case RESTART:
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WithGear(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case AxisFeeded:
					currentState.getAutomata().putAxis();
					return Working;
				case NSTOP:
					return WithGearStop;
				case ESTOP:
					return WithGearStop;
				default:
					break;
				}

				return super.executeInternal(currentState, input);
			}
		},
		WithGearStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case RESTART:
					return WithGear;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WithAxis(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case GearFeeded:
					currentState.getAutomata().putGear();
					return Working;
				case NSTOP:
					return WithAxisStop;
				case ESTOP:
					return WithAxisStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WithAxisStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case RESTART:
					return WithAxis;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Working(ModeEnum.RUNNING) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case JobDone:
					Slave1Automata father = (Slave1Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave1Input.AS_READY, false);
					return IdleLoaded;
				case NSTOP:
					return WorkingStop;
				case ESTOP:
					return WorkingStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WorkingStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case RESTART:
					return Working;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleLoaded(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case ASRemoved:
					Slave1Automata father = (Slave1Automata) currentState.getAutomata().getFather();
					father.feedInput(Slave1Input.AS_EMPTY, false);
					return Idle;
				case NSTOP:
					return IdleLoadedStop;
				case ESTOP:
					return IdleLoadedStop;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleLoadedStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case RESTART:
					return IdleLoaded;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

		@Override
		public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
			return this;
		}

		private ModeEnum mode;

		states(ModeEnum mode) {
			this.mode = mode;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}
	}

	private states currentState;
	private transient AssemblyStationAutomata automata;

	public AssemblyStationState(AssemblyStationAutomata automata) {
		this.automata = automata;
		currentState = states.Started;
	}

	@Override
	public boolean execute(AssemblyStationInput input) {
		states oldState = currentState;
		currentState = (states) currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public AssemblyStationAutomata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
