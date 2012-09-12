package slave2;

import master.MasterInput;
import slave1.Slave1Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.weldingstation.WeldingInput;
import core.utilities.log.Logger;

public class Slave2State implements State<Slave2Input> {

	private static final long serialVersionUID = 7025769611332442118L;

	public static enum states implements AutomataStatesInternalImplementation<Slave2Input, Slave2State> {
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case START:
					currentState.getAutomata().getTransferBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.START, true);
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE1, Slave1Input.TRANSPORT_READY);
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
				case ASSEMBLED_READY_FOR_PICKUP:

					return WaitingForWeldingToBeEmpty;

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
		},WaitingForWeldingToBeEmpty(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WS_EMPTY:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER,MasterInput.MOVE_AS_FROM_TCB_TO_WS);
					return WaitingForWSToBeLoaded;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},WaitingForWSToBeLoaded(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WELDING_STATION_LOADED:
					currentState.getAutomata().getWeldingStation().feedInput(WeldingInput.AssemblyLoaded, false);
					return WaitingForWSToFinish;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},WaitingForWSToFinish(ModeEnum.RUNNING){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				switch (input) {
				case WELDMENT_READY:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.MASTER, MasterInput.MOVE_WP_FROM_WS_TO_QCS);
					return WaitForNOtificationOfWSEmpty;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},WaitForNOtificationOfWSEmpty(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave2State currentState, Slave2Input input) {
				// TODO Auto-generated method stub
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
