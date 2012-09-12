package core.sections.ParallelPort;

public class ParallelPortState implements Cloneable {

	private boolean[] connectors = new boolean[16];
	/**
	 * Stores the position the last change was made
	 */
	private int modifiedBit = -1;

	/**
	 * Returns the position in witch the last change was made
	 * 
	 * @return
	 */
	public int getModifiedBit() {
		return modifiedBit;
	}

	public void setModifiedBit(int modifiedBit) {
		this.modifiedBit = modifiedBit;
	}

	public ParallelPortState() {
		for (int i = 0; i < 16; i++){
			connectors[i] = false;
		}
	}

	/**
	 * We make the Constructor private, this class is not intended to be
	 * instantiated with a constructor;
	 */
	private ParallelPortState(ParallelPortState other) {
		for (int i = 0; i < 16; i++){
			this.connectors[i] = other.connectors[i];
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
		return connectors[position];
	}

	public void setValue(int position, boolean value) {
		modifiedBit = position;
		connectors[position] = Boolean.valueOf(value);
	}

	public boolean[] getConnectors() {
		return connectors;
	}

	@Override
	public Object clone() {
		return new ParallelPortState(this);
	}

}
