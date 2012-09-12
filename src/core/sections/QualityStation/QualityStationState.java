package core.sections.QualityStation;

import slave3.Slave3Automata;
import slave3.Slave3Input;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;

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

					// TODO: AQUI FALTAN MOVIDAS

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
				AutomataContainer<?, ?, ?> father = currentState.getAutomata().getFather();
				switch (input) {
				case JobDoneOK:
					if (father instanceof Slave3Automata) {
						Slave3Automata slave3 = (Slave3Automata) father;
						slave3.feedInput(Slave3Input.QCS_FINISHED_OK, false);
					}
					return Idle;
				case JobDoneKO:
					if (father instanceof Slave3Automata) {
						Slave3Automata slave3 = (Slave3Automata) father;
						slave3.feedInput(Slave3Input.QCS_FINISHED_NOT_OK, false);
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
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
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
