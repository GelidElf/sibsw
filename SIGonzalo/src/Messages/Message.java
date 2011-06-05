package Messages;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private ArrayList<Attribute> _contents = null;
	private String _ID = null;
	private Boolean _priority = null;

	public Message(String ID){
		_ID = ID;
		_priority = false;
	}
	
	public void addAttribute(Attribute att){
		if (_contents == null)
			_contents = new ArrayList<Attribute>();
		_contents.add(att);
	}
	
	public void addAttribute (String name, String value){
		if (_contents == null)
			_contents = new ArrayList<Attribute>();
		_contents.add(new Attribute(name, value));	
	}
	
	public String getID(){
		return _ID;
		
	}
	
	public String getOwner(){
		return _ID.substring(0,_ID.indexOf(MessageFactory.SEPARATOR));
	}
	
	public Attribute getAttribute(int i){
		return _contents.get(i);
	}
	
	public String getAttributeValue(String key){
		for (Attribute att:_contents){
			if (att.getName().equals(key))
				return att.getValue();
		}
		return null;
	}
	
	public Attribute getAttribute (String key){
		for (Attribute att:_contents){
			if (att.getName().equals(key))
				return att;
		}
		return null;
		
	}
	
	public Boolean getPriority(){
		return _priority;
	}
	
	public void setPriority(){
		_priority = true;
	}
	
}
