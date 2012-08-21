package core.messages;

import java.io.Serializable;
import java.util.ArrayList;

import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataModel;

public class Message implements Serializable {

	private CommunicationMessageType type = null;
	private static final long serialVersionUID = -8585464412008833912L;
	private static final String MODEL_KEY = "MODEL_KEY";
	private Enum<?> inputType;
	private ArrayList<Attribute> contents = null;
	private String messageId = null;
	private CommunicationIds destination = null;
	private CommunicationIds owner = null;

	private Boolean urgent = null;

	public Message(String messageID, CommunicationIds destination, boolean isUrgent, CommunicationMessageType type, Enum<?> inputType) {
		this.messageId = messageID;
		this.destination = destination;
		this.urgent = isUrgent;
		this.inputType = inputType;
		this.type = type;
	}

	public void addAttribute(Attribute att) {
		if (contents == null) {
			contents = new ArrayList<Attribute>();
		}
		contents.add(att);
	}

	public void addAttribute(String name, Object value) {
		if (contents == null) {
			contents = new ArrayList<Attribute>();
		}
		contents.add(new Attribute(name, value));
	}

	public String getID() {
		return messageId;

	}

	public CommunicationIds getDestination() {
		return destination;
	}

	public void setDestination(CommunicationIds destination) {
		this.destination = destination;
	}

	public CommunicationIds getOwner() {
		return owner;
	}

	public Attribute getAttribute(int i) {
		return contents.get(i);
	}

	public Object getAttributeValue(String key) {
		Attribute attribute = getAttribute(key);
		if (attribute == null) {
			return null;
		} else {
			return attribute.getValue();
		}
	}

	public Attribute getAttribute(String key) {
		if (contents == null) {
			return null;
		} else {
			for (Attribute att : contents) {
				if (att.getName().equals(key)) {
					return att;
				}
			}
			return null;
		}

	}

	public Boolean isUrgent() {
		return urgent;
	}

	public void setUrgent() {
		urgent = true;
	}

	public void unsetUrgent() {
		urgent = false;
	}

	public ArrayList<Attribute> getAttributes() {
		return contents;
	}

	public Enum<?> getInputType() {
		return inputType;
	}

	public void setInputType(Enum<?> inputType) {
		this.inputType = inputType;
	}

	public CommunicationMessageType getType() {
		return type;
	}

	public void setType(CommunicationMessageType type) {
		this.type = type;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(CommunicationIds owner) {
		this.owner = owner;
	}

	public boolean isBroadcast() {
		return (this.destination == null) || (this.destination == CommunicationIds.BROADCAST);
	}

	/**
	 * @return the currentModel
	 */
	public AutomataModel getCurrentModel() {
		return (AutomataModel) getAttributeValue(MODEL_KEY);
	}

	/**
	 * @param currentModel the currentModel to set
	 */
	public void setCurrentModel(AutomataModel currentModel) {
		addAttribute(MODEL_KEY, currentModel);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(" ID: " + messageId);
		builder.append(" ORG: " + owner);
		builder.append(" DST: " + destination);
		builder.append(" Urgent: " + urgent);
		builder.append(" Type: " + type);
		builder.append(" InputType: " + inputType);
		return builder.toString();
	}
}