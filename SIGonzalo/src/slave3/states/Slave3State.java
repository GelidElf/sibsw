package slave3.states;

import java.io.Serializable;

import slave3.ATslave3;

public abstract class Slave3State implements Serializable {
	
	static Slave3State instance = null;
	
	public abstract void execute(ATslave3 master);

	public Slave3State Start(){
		return this;
	}
	public Slave3State EStop(){
		return this;
	}
	public Slave3State NStop(){
		return this;
	}
	public Slave3State R2Idle(){
		return this;
	}
	public Slave3State LoadQCS(){
		return this;
	}
	public Slave3State Invalid(){
		return this;
	}
	public Slave3State Valid(){
		return this;
	}
	public Slave3State NotFull(){
		return this;
	}
	public Slave3State Full(){
		return this;
	}
	public Slave3State Restart(){
		return this;
	}
	
}
