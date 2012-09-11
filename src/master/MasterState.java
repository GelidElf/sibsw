package master;

import slave1.Slave1Input;
import slave2.Slave2Input;
import slave3.Slave3Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ReportValues;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.robot2.Robot2Input;
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
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(
					MasterState currentState, MasterInput input) {
				switch (input) {
				case AS_READY: //Ciclo de comunicacion entre slave1 y 2 para colocar el assembled en la transport, si esta puede aceptar elementos.
					return AssembledForTranferReady;
				case FEED_WS: //ciclo de control para enviar un assembled a la welding station
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverAssembledPiece, false);
					return MovingAssemblyFromTransferToWeldingStation;
				case AS_READY_TO_PICKUP:
					return WaitingForEmptyQCS;
				case PIECE_OK:
					MasterModel.getInstance().getCurrentReport().receiveSignal(ReportValues.OK_PIECES);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverCheckedOkPiece, false);
					return MovingOkPiece;
				case PIECE_NOK:
					MasterModel.getInstance().getCurrentReport().receiveSignal(ReportValues.NOT_OK_PIECES);
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverCheckedNokPiece, false);
					return MovingNokPiece;
				default:
					break;
				}

				return super.executeInternal(currentState, input);
			}
		},
		AssembledForTranferReady(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case EMPTY_TRANSFER_CB:
					Message message = new Message("TRANSPORT_EMPTY", CommunicationIds.SLAVE1, false,
							CommunicationMessageType.COMMAND, Slave1Input.TRANSPORT_READY);
					currentState.getAutomata().sendMessage(message);
					return WaitingForTransportToReceiveElement;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WaitingForTransportToReceiveElement(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case AS_IN_TCB:
					Message message = new Message("PIECE_IN_TRANSPORT", CommunicationIds.SLAVE2, false,
							CommunicationMessageType.COMMAND, Slave2Input.ASSEMBLED_IN_TRANSPORT);
					currentState.getAutomata().sendMessage(message);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},//END AS to transfer
		//transfer to WS
		MovingAssemblyFromTransferToWeldingStation(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case AP_IN_WS:
					Message message = new Message("WELDING_STATION_LOADED", CommunicationIds.SLAVE2, false,
							CommunicationMessageType.COMMAND, Slave2Input.WELDING_STATION_LOADED);
					currentState.getAutomata().sendMessage(message);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},//END AS from transfer to WS
		//welded from ws to qcs
		WaitingForEmptyQCS(ModeEnum.IDLE){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case QCS_EMPTY:
					currentState.getAutomata().getRobot().feedInput(Robot2Input.DeliverWeldedPiece, false);
					return WaitingForQCSToBeLoaded;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},WaitingForQCSToBeLoaded(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case QCS_LOADED:
					Message message = new Message("QCS_LOADED", CommunicationIds.SLAVE3, false,
							CommunicationMessageType.COMMAND, Slave3Input.QCS_LOADED);
					currentState.getAutomata().sendMessage(message);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},//End ws to wcb
		//start ok
		MovingOkPiece(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case OK_LOADED:
					Message message = new Message("OK_LOADED", CommunicationIds.SLAVE3, false,
							CommunicationMessageType.COMMAND, Slave3Input.OK_LOADED);
					currentState.getAutomata().sendMessage(message);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		//end ok
		//start NOK
		MovingNokPiece(ModeEnum.RUNNING){
			@Override
			public AutomataStatesInternalImplementation<MasterInput, MasterState> executeInternal(MasterState currentState, MasterInput input) {
				switch (input) {
				case NOK_LOADED:
					Message message = new Message("NOK_LOADED", CommunicationIds.SLAVE3, false,
							CommunicationMessageType.COMMAND, Slave3Input.NOT_OK_LOADED);
					currentState.getAutomata().sendMessage(message);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};
		//end NOK

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
