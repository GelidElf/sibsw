package ParallelPort;

import java.util.BitSet;

public class ParallelPortState implements Cloneable{

	
	private BitSet _connectors = new BitSet(16);
	/**
	 * Stores the position the last change was made
	 */
	private int _modifiedBit = -1;
		
	/**
	 * Returns the position in witch the last change was made
	 * @return
	 */
	public int getModifiedBit() {
		return _modifiedBit;
	}

	public void setModifiedBit(int modifiedBit) {
		_modifiedBit = modifiedBit;
	}

	public ParallelPortState(){
		
	}
	
	/**
	 * We make the Constructor private, this class is not intended to be instantiated with a constructor;
	 */
	private ParallelPortState(ParallelPortState other){
		this._connectors = (BitSet)other._connectors.clone();
	}
	
	/**
	 * Returns the boolean value of the connectors position indicated by position
	 * @param position int value 
	 * @return
	 */
	public boolean getValue(int position){
		return _connectors.get(position);
	}

	public void setValue(int position, boolean value){
		_modifiedBit = position;
		_connectors.set(position,value);
	}
		
	public BitSet getConnectors(){
		return _connectors;
	}
	
	public Object clone(){
		return new ParallelPortState(this);
	}
	
}
