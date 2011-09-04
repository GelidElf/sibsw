package slave3.states;

import slave3.ATslave3;

public class Storing extends Slave3State {
	
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new Storing();
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
		return StopStoring.getInstance();
	}
	
	@Override
	public Slave3State NotFull() {
		// TODO Auto-generated method stub
		return CBReady.getInstance();
	}

}