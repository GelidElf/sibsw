package core.messages;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private static final long serialVersionUID = -8585464412008833912L;
	private ArrayList<Attribute> contents = null;
	private String messageId = null;
	private String destination = null;
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	private Boolean urgent = null;

	public Message(String messageID, String destination, boolean isUrgent){
		this.messageId = messageID;
		this.destination = destination;
		this.urgent = isUrgent;
	}
	
	public void addAttribute(Attribute att){
		if (contents == null)
			contents = new ArrayList<Attribute>();
		contents.add(att);
	}
	
	public void addAttribute (String name, String value){
		if (contents == null)
			contents = new ArrayList<Attribute>();
		contents.add(new Attribute(name, value));	
	}
	
	public String getID(){
		return messageId;
		
	}
	
	public String getOwner(){
		return messageId.substring(0,messageId.indexOf("."));
	}
	
	public Attribute getAttribute(int i){
		return contents.get(i);
	}
	
	public String getAttributeValue(String key){
		for (Attribute att:contents){
			if (att.getName().equals(key))
				return att.getValue();
		}
		return null;
	}
	
	public Attribute getAttribute (String key){
		for (Attribute att:contents){
			if (att.getName().equals(key))
				return att;
		}
		return null;
		
	}
	
	public Boolean isUrgent(){
		return urgent;
	}
	
	public void setUrgent(){
		urgent = true;
	}
	
	public void unsetUrgent(){
		urgent = false;
	}

	public ArrayList<Attribute> getAttributes() {
		return contents;		
	}
	
}
