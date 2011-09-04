package slave3.states;

import slave3.ATslave3;

public class CBReady extends Slave3State {
	static Slave3State instance = null;
	
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new CBReady();
		}
		return instance;
	}

	@Override
	public void execute(ATslave3 master) {
		// TODO Auto-generated method stub

	}
	
	public Slave3State Estop(){
		return StopCBReady.getInstance();
	}
	
	public Slave3State R2Idle(){
		return IdleQCSEmpty.getInstance();
	}

}
