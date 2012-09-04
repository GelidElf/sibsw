package slave1;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataStatesInternalImplementation;
import core.model.State;
import core.sections.AssembyStation.AssemblyStationInput;
import core.sections.ConveyorBelt.ConveyorBeltInput;
import core.sections.robot1.Robot1Input;

public class Slave1State implements State<Slave1Input> {

	private static final long serialVersionUID = 3510029429322490323L;

	public static enum states implements AutomataStatesInternalImplementation<Slave1Input, Slave1State>{
		STARTED(ModeEnum.READY) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case START:
					//FIXME: Make the feed input execute the start command
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
			
			//!!!!!!!!!!!!! FALTA MOVE_ASSEMBLED
			
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch(input){
				case MOVE_AXIS:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverAxis, false);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return AXIS_LOADING;
				case MOVE_GEAR:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverGear, false);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return GEAR_LOADING;
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
		GEAR_LOADING(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case GEAR_IN_AS:
					currentState.getAutomata().getAssemblyStation()
							.feedInput(AssemblyStationInput.gearDetectedTrue, false);
					return Idle;
				case NSTOP:
					return GEAR_LOADING_STOP;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		GEAR_LOADING_STOP(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case RESTART:
					return GEAR_LOADING;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		AXIS_LOADING(ModeEnum.RUNNING) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AXIS_IN_AS:
					currentState.getAutomata().getAssemblyStation()
							.feedInput(AssemblyStationInput.axisDetectedTrue, false);
					return Idle;
				case NSTOP:
					return AXIS_LOADING_STOP;
				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		},
		AXIS_LOADING_STOP(ModeEnum.NSTOP) {
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case RESTART:
					return AXIS_LOADING;
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
		return oldState != currentState;
	}

	public Slave1Automata getAutomata() {
		return slave1;
	}

	@Override
	public ModeEnum getMode() {
		return currentState.getMode();
	}

}
