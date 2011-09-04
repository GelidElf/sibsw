package slave3.states;

import slave3.ATslave3;

public class QCSWorking extends Slave3State {
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new QCSWorking();
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
		return StopQCSWorking.getInstance();
	}
	
	@Override
	public Slave3State Valid() {
		// TODO Auto-generated method stub
		return Storing.getInstance();
	}
	
	@Override
	public Slave3State Invalid() {
		// TODO Auto-generated method stub
		return Trashing.getInstance();
	}

}
