package slave1;

import slave2.Slave2Input;
import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.AssembyStation.AssemblyStationInput;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.robot1.Robot1Input;
import core.utilities.log.Logger;

public class Slave1State implements State<Slave1Input> {

	private static final long serialVersionUID = -283016116349309454L;

	public static enum states implements AutomataStatesInternalImplementation<Slave1Input, Slave1State>{
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case START:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.START, true);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.START, true);
					return Idle;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		Idle(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch(input){
				case AS_READY:
					return WAITING_FOR_SPACE_IN_TB;
				case AS_EMPTY:
					return LOADING_AS;
					// OLDCODE
				case NSTOP:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.NSTOP, true);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.NSTOP, true);
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.NSTOP, true);
					return IdleStop;
				case ESTOP:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.ESTOP, true);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.ESTOP, true);
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.ESTOP, true);
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
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case RESTART:
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WAITING_FOR_SPACE_IN_TB(ModeEnum.IDLE){
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case TRANSPORT_READY:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverAssembledPiece, false);
					return LOADING_TB;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		LOADING_TB(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AS_IN_TRANSPORT:
					currentState.getAutomata().sendCommandMessage(CommunicationIds.SLAVE2,Slave2Input.ASSEMBLED_IN_TRANSPORT);
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.ASRemoved, false);
					return Idle;

				default:
					break;
				}

				return super.executeInternal(currentState, input);
			}
		},
		LOADING_AS(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case GEAR_READY:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverGear, false);
					return LOADING_GEAR_IN_AS;
				case AXIS_READY:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverAxis, false);
					return LOADING_AXIS_IN_AS;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		LOADING_GEAR_IN_AS(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case GEAR_IN_AS:
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.GearFeeded, false);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return WAITING_FOR_AXIS;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WAITING_FOR_AXIS(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AXIS_READY:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverAxis, false);
					return LOADING_AXIS_WHEN_GEAR_READY;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		LOADING_AXIS_WHEN_GEAR_READY(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AXIS_IN_AS:
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.AxisFeeded, false);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		LOADING_AXIS_IN_AS(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AXIS_IN_AS:
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.AxisFeeded, false);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return WAITING_FOR_GEAR;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		WAITING_FOR_GEAR(ModeEnum.IDLE) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case GEAR_READY:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverGear, false);
					return LOADING_GEAR_WHEN_AXIS_READY;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		LOADING_GEAR_WHEN_AXIS_READY(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case GEAR_IN_AS:
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.GearFeeded, false);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return Idle;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		};

		@Override
		public states executeInternal(Slave1State currentState, Slave1Input input) {
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
	private transient Slave1Automata slave1;

	public Slave1State(Slave1Automata slave1) {
		this.slave1 = slave1;
		currentState = states.STARTED;
	}

	@Override
	public boolean execute(Slave1Input input) {
		states oldState = currentState;
		currentState = currentState.executeInternal(this, input);
		boolean changedState = oldState != currentState;
		if (changedState){
			Logger.println("ChangedState: " + oldState.name() +"->"+ currentState.name());
		}
		return changedState;
	}

	public Slave1Automata getAutomata() {
		return slave1;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
