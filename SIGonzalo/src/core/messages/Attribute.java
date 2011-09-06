package core.messages;

import java.io.Serializable;

public class Attribute implements Serializable{

	private String _name = null;
	private String _value = null;
	
	public Attribute (){
		_name = new String("");
		_value = new String("");
	}
	
	public Attribute (String name, String value){
		_name = name;
		_value = value;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getValue(){
		return _value;
	}
	
}
