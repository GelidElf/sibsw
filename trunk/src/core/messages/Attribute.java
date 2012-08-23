package core.messages;

import java.io.Serializable;

/**
 * Element class for each element in the contents of a message
 * 
 * Has a name, or identifier, that must be unique within a Message, and the value that could be any object
 * 
 * @author GelidElf
 *
 */
public class Attribute implements Serializable {

	private static final long serialVersionUID = 4891814832682990272L;
	private String name = null;
	private Object value = null;

	public Attribute() {
		name = new String("");
		value = new String("");
	}

	public Attribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
