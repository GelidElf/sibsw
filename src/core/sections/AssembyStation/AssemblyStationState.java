package core.sections.AssembyStation;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;

public class AssemblyStationState implements State<AssemblyStationInput> {

	private static final long serialVersionUID = 6259939714628916223L;

	private enum states implements AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case START:
					currentState.getAutomata().startCommand();
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
				case axisDetectedTrue:
					return WithAxis;
				case gearDetectedTrue:
					return WithGear;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WithGear(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case axisDetectedTrue:
					return Working;

				default:
					break;
				}

				return super.executeInternal(currentState, input);
			}
		},
		WithAxis(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<AssemblyStationInput, AssemblyStationState> executeInternal(AssemblyStationState currentState, AssemblyStationInput input) {
				switch (input) {
				case gearDetectedTrue:
					return Working;

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
				case jobDone:
					return IdleLoaded;

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
				case apDetectedFalse:
					return Idle;

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
		return oldState != currentState;
	}

	public AssemblyStationAutomata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
