package core.sections.ConveyorBelt;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;

public class ConveyorBeltState implements State<ConveyorBeltInput> {

	private static final long serialVersionUID = 3002955261150992615L;

	private enum states implements AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
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
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(
					ConveyorBeltState currentState, ConveyorBeltInput input) {
				// TODO
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
	private ConveyorBeltAutomata automata;

	public ConveyorBeltState(ConveyorBeltAutomata automata) {
		currentState = states.Started;
		this.automata = automata;
	}

	public ConveyorBeltAutomata getAutomata() {
		return automata;
	}

	@Override
	public void execute(ConveyorBeltInput input) {
		currentState = (states) currentState.executeInternal(this, input);
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
