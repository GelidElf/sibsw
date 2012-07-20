package slave1;

import core.model.AutomataContainer;
import core.model.State;

public class Slave1State implements State<Slave1Input> {

	public static enum states {
		STARTED{
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case START:
					return IDDLE;
				}
				return super.executeInternal(currentState, input);
			}
		}, IDDLE{
			@Override
			public states executeInternal(Slave1State currentState, Slave1Input input) {
				switch (input) {
				case AS_EMPTY:
					
					break;

				default:
					break;
				}
				return super.executeInternal(currentState, input);
			}
		}, AS_UNLOAD, LOADING_TRANSFER_CB, UNLOADING_AS, LOADING_AS, GEAR_UNLOAD, GEAR_LOADING, AXIS_UNLOAD, AXIS_LOADING;
		
		public states executeInternal(Slave1State currentState,Slave1Input input){
			return this;
		}
	}
	
	private states currentState;
	private ATSlave1 slave1;
	
	public Slave1State(ATSlave1 slave1) {
		this.slave1 = slave1;
		currentState = states.STARTED;
	}

	@Override
	public void execute(Slave1Input input) {
		currentState = currentState.executeInternal(this, input);
	}

}
