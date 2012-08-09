package core.messages;

import java.io.Serializable;

public class Attribute implements Serializable{

	private static final long serialVersionUID = 4891814832682990272L;
	private String _name = null;
	private Object _value = null;
	
	public Attribute (){
		_name = new String("");
		_value = new String("");
	}
	
	public Attribute (String name, Object value){
		_name = name;
		_value = value;
	}
	
	public String getName(){
		return _name;
	}
	
	public Object getValue(){
		return _value;
	}
	
}
