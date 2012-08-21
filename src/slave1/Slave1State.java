package slave1;

import core.model.State;

public class Slave1State implements State<Slave1Input> {

	public static enum states {
		STARTED{
			@Override
			protected states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case START:
					currentState.getAutomata().getGearBelt();
					return IDDLE;
				}
				return super.executeInternal(currentState, input);
			}
		}, IDDLE{
			@Override
			protected states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AS_EMPTY:
					
					break;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		}, AS_UNLOAD, LOADING_TRANSFER_CB, UNLOADING_AS, LOADING_AS, GEAR_UNLOAD, GEAR_LOADING, AXIS_UNLOAD, AXIS_LOADING;
		
		protected states executeInternal(Slave1State currentState,Slave1Input input){
			return this;
		}
	}
	
	private states currentState;
	private Slave1Automata slave1;
	
	public Slave1State(Slave1Automata slave1) {
		this.slave1 = slave1;
		currentState = states.STARTED;
	}

	@Override
	public void execute(Slave1Input input) {
		currentState = currentState.executeInternal(this, input);
	}
	
	public Slave1Automata getAutomata(){
		return slave1;
	}

}
