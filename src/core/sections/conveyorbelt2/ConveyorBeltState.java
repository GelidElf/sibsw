package core.sections.conveyorbelt2;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;

public class ConveyorBeltState implements State<ConveyorBeltInput> {

	private static final long serialVersionUID = 5264201109006201874L;

	private enum states implements AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> {
		Started(ModeEnum.READY) {
			@Override
			public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(ConveyorBeltState currentState, ConveyorBeltInput input) {
				switch (input) {
				case START:
					return Moving;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Moving(ModeEnum.RUNNING);

		private ModeEnum mode;

		@Override
		public AutomataStatesInternalImplementation<ConveyorBeltInput, ConveyorBeltState> executeInternal(ConveyorBeltState currentState, ConveyorBeltInput input) {
			return this;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}

		private states(ModeEnum mode) {
			this.mode = mode;
		}
	}

	private ConveyorBeltAutomata automata;
	private states currentState;

	public ConveyorBeltState(ConveyorBeltAutomata automata) {
		this.automata = automata;
	}

	public ConveyorBeltAutomata getAutomata() {
		return automata;
	}

	@Override
	public void execute(ConveyorBeltInput input) {
		currentState.executeInternal(this, input);
	}

	@Override
	public ModeEnum getMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
