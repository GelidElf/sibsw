package master;

import slave2.Slave2Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.utilities.log.Logger;

public class MasterState implements State<MasterInput> {

	private static final long serialVersionUID = 4405083677911400643L;

	private enum states implements AutomataStatesInternalImplementation<MasterInput, MasterState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(
					MasterState currentState, MasterInput input) {
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
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {

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
		public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState,
				MasterInput input) {
			return this;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}

	}

	private states currentState;
	private transient MasterAutomata automata;

	public MasterState(MasterAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public MasterAutomata getAutomata() {
		return automata;
	}

	@Override
	public boolean execute(MasterInput input) {
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