package core.sections.QualityStation;

import slave1.Slave1Automata;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;

public class QualityStationState implements State<QualityStationInput> {

	private static final long serialVersionUID = 740645352145252464L;

	private enum states implements AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
				switch (input) {
				case START:
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
				switch (input) {
				case NSTOP:
					return IdleStop;
				case ESTOP:
					return IdleStop;
				case Load:
					return WeldedLoaded;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
				switch (input) {
				case RESTART:
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WeldedLoaded(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
				switch (input) {
				case Load:
					AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
					if (father instanceof Slave1Automata) {
						Slave1Automata slave1 = (Slave1Automata) father;
						slave1.feedInput(Slave1Input.AXIS_IN_AS, false);
					}
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WeldedLoadedStop(ModeEnum.NSTOP) {
			@Override
			public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
				switch (input) {
				case RESTART:
					return WeldedLoaded;
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
		public ModeEnum getMode() {
			return mode;
		}

		@Override
		public AutomataStatesInternalImplementation<QualityStationInput, QualityStationState> executeInternal(QualityStationState currentState, QualityStationInput input) {
			return this;
		}

	}

	private transient QualityStationAutomata automata;
	private states currentState;

	@Override
	public boolean execute(QualityStationInput input) {
		states oldState = currentState;
		currentState = (states) currentState.executeInternal(this, input);
		return oldState != currentState;
	}

	public QualityStationState(QualityStationAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public QualityStationAutomata getAutomata() {
		return automata;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
