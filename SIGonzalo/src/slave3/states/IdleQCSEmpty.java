package slave3.states;

import slave3.ATslave3;

public class IdleQCSEmpty extends Slave3State {
	
	static Slave3State instance = null;
	
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new IdleQCSEmpty();
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
		return StopIdleQCSEmpty.getInstance();
	}


	@Override
	public Slave3State LoadQCS() {
		// TODO Auto-generated method stub
		return QCSWorking.getInstance();
	}


}
