package core.sections.ParallelPort;


public class ParallelPortState implements Cloneable {

	private boolean[] _connectors = new boolean[16];
	/**
	 * Stores the position the last change was made
	 */
	private int _modifiedBit = -1;

	/**
	 * Returns the position in witch the last change was made
	 * 
	 * @return
	 */
	public int getModifiedBit() {
		return _modifiedBit;
	}

	public void setModifiedBit(int modifiedBit) {
		_modifiedBit = modifiedBit;
	}

	public ParallelPortState() {
	}

	/**
	 * We make the Constructor private, this class is not intended to be
	 * instantiated with a constructor;
	 */
	private ParallelPortState(ParallelPortState other) {
		int i = 0;
		for (Boolean value : other._connectors) {
			this._connectors[i] = value.booleanValue();
		}
	}

	/**
	 * Returns the boolean value of the connectors position indicated by
	 * position
	 * 
	 * @param position
	 *            int value
	 * @return
	 */
	public boolean getValue(int position) {
		return _connectors[position];
	}

	public void setValue(int position, boolean value) {
		_modifiedBit = position;
		_connectors[position] = Boolean.valueOf(value);
	}

	public boolean[] getConnectors() {
		return _connectors;
	}

	@Override
	public Object clone() {
		return new ParallelPortState(this);
	}

}
