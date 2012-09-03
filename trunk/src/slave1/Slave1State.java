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
					//Tested code above
					
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.START, true);
					currentState.getAutomata().getAssemblyStation().feedInput(AssemblyStationInput.START, true);
					//startCommand should start the sections on ready so that the start command is heard
					currentState.getAutomata().getRobot().startCommand();
					currentState.getAutomata().getGearBelt().startCommand();
					currentState.getAutomata().getAxisBelt().startCommand();
					currentState.getAutomata().getAssemblyStation().startCommand();
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
				case MOVE_AXIS:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverAxis, false);
					currentState.getAutomata().getAxisBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return AXIS_LOADING;
				case MOVE_GEAR:
					currentState.getAutomata().getRobot().feedInput(Robot1Input.DeliverGear, false);
					currentState.getAutomata().getGearBelt().feedInput(ConveyorBeltInput.unloadSensorFalse, false);
					return GEAR_LOADING;
				case GEAR_IN_AS:
					currentState.getAutomata().getAssemblyStation()
							.feedInput(AssemblyStationInput.gearDetectedTrue, false);
					return LOADING_AS;
				case AXIS_IN_AS:
					currentState.getAutomata().getAssemblyStation()
							.feedInput(AssemblyStationInput.axisDetectedTrue, false);
					return LOADING_AS;
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
		AS_UNLOAD(ModeEnum.RUNNING), LOADING_TRANSFER_CB(ModeEnum.RUNNING), UNLOADING_AS(ModeEnum.RUNNING), LOADING_AS(ModeEnum.RUNNING), GEAR_UNLOAD(ModeEnum.RUNNING), GEAR_LOADING(ModeEnum.RUNNING), AXIS_UNLOAD(ModeEnum.RUNNING), AXIS_LOADING(ModeEnum.RUNNING);

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
