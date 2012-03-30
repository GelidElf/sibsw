package core.sections.ParallelPort;

import core.sections.ParallelPort.Utils.ParallelPortException;

/**
 * Class for assigning a name to a group of bits and the methods to operate with
 * numbers with that group instead of boolean values
 * 
 * @author GelidElf
 * 
 */
public class ParallelPortPinGroup implements Cloneable {

	private ParallelPortManager _manager = null;
	private String _name = null;
	private int _start = -1;
	private int _end = -1;

	/**
	 * Constructor
	 * 
	 * @param manager
	 * @param name
	 * @param start
	 * @param end
	 */
	public ParallelPortPinGroup(ParallelPortManager manager, String name,
			int start, int end) {
		_manager = manager;
		_name = name;
		_start = start;
		_end = end;
	}

	/**
	 * Returns the integer value of the segment of parallel port asigned to this
	 * group
	 * 
	 * @return the integer representation of the section read as binary
	 */
	public int intValue() {
		int result = 0;
		int value = 0;
		if (_manager != null) {
			if (_start == _end) {
				return _manager.getIntValue(_start);
			}
			for (int i = _start; i <= _end; i++) {
				value = new Double(_manager.getIntValue(i)
						* Math.pow(2, _end - i - 1)).intValue();
				result += value;
			}
		}
		return result;
	}

	/**
	 * Sets in binary the value in the section asigned to this group
	 * 
	 * @param value
	 *            the value
	 * @throws ParallelPortException
	 *             if the value to add is too big for the section assigned to
	 *             this group
	 */
	public void setValue(int value) throws ParallelPortException {
		if (value > Math.pow(2, this.size())) {
			throw new ParallelPortException(
					"Tried to set a value to big for the group");
		}
		if (_manager != null) {
			String binaryString = Integer.toBinaryString(value);
			for (int i = 0; i < binaryString.length(); i++) {
				_manager.setBitTo(_start + i, (binaryString.charAt(i) == '1'));
			}
		}
	}

	private int size() {
		return _end - _start + 1;
	}

	public int getStart() {
		return _start;
	}

	public int getEnd() {
		return _end;
	}

	@Override
	public Object clone() {
		return new ParallelPortPinGroup(_manager, _name, _start, _end);
	}

	public String getName() {
		return _name;
	}

}
