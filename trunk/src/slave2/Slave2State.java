package slave2;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.weldingstation.WeldingInput;
import core.utilities.log.Logger;

public class Slave2State implements State<Slave2Input> {

	private static final long serialVersionUID = 3510029429322490323L;

	public static enum states implements AutomataStatesInternalImplementation<Slave2Input, Slave2State> {
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case START:
					//FIXME: Make the feed input execute the start command
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.START, true);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				//TODO
				// !!!!!!!!!!!!!!!!!!!!!!!!!!! COMPLETAR Y MODIFICAR EL RESTO
				case NSTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.NSTOP, true);
					return IdleStop;
				case ESTOP:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.ESTOP, true);
					return IdleStop;

					// Complete
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		IdleStop(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case RESTART:
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

		@Override
		public states executeInternal(Slave2State currentState, Slave2Input input) {
			return this;
		}

		private ModeEnum mode;

		private states(ModeEnum mode) {
			this.mode = mode;
		}

		@Override
		public ModeEnum getMode() {
			return mode;
		}
	}

	private states currentState;
	private transient Slave2Automata slave2;

	public Slave2State(Slave2Automata slave2) {
		this.slave2 = slave2;
		currentState = states.STARTED;
	}

	@Override
	public boolean execute(Slave2Input input) {
		states oldState = currentState;
		currentState = currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public Slave2Automata getAutomata() {
		return slave2;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
