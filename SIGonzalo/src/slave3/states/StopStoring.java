package slave3.states;

import slave3.ATslave3;

public class StopStoring extends Slave3State {
	public static Slave3State getInstance(){
		if (instance == null){
			instance = new StopStoring();
		}
		return instance;
	}

	@Override
	public void execute(ATslave3 master) {
		// TODO Auto-generated method stub

	}

	@Override
	public Slave3State Restart() {
		// TODO Auto-generated method stub
		return Storing.getInstance();
	}
	


}
