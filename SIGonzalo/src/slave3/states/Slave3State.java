package slave3.states;

import java.io.Serializable;

import slave3.ATslave3;

public interface Slave3State extends Serializable {
	public void execute(ATslave3 master);

	public Slave3State Start();
	public Slave3State R2Idle();
	public Slave3State WeldingPieceNeeded();
	
}
