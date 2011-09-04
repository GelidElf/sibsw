package slave3.states;

import slave3.ATslave3;

public class Trashing extends Slave3State {

	public static Slave3State getInstance(){
		if (instance == null){
			instance = new Trashing();
		}
		return instance;
	}

	@Override
	public void execute(ATslave3 master) {
		// TODO Auto-generated method stub

	}

	@Override
	public Slave3State EStop() {
		// TODO Auto-generated method stub
		return StopTrashing.getInstance();
	}
	
	@Override
	public Slave3State R2Idle() {
		// TODO Auto-generated method stub
		return IdleQCSEmpty.getInstance();
	}

}
