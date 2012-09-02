package core.messages;

import java.io.Serializable;
import java.util.ArrayList;

import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataModel;
import core.model.State;

/**
 * Object read and written in the Socket for communication is paremetrized with
 * the owner of the message, the destination, the type of message, the input
 * type (when Type is COMMAND), if it's urgent, and id for the message, and the
 * contents
 * 
 * @author GelidElf
 * 
 */
public class Message implements Serializable {

	private CommunicationMessageType type = null;
	private static final long serialVersionUID = -8585464412008833912L;
	private static final String MODEL_KEY = "MODEL_KEY";
	private Enum<?> inputType;
	private ArrayList<Attribute> contents = null;
	private String messageId = null;
	private CommunicationIds destination = null;
	private CommunicationIds owner = null;
	private transient Inbox container;
	

	private Boolean urgent = null;

	public Message(String messageID, CommunicationIds destination, boolean isUrgent, CommunicationMessageType type, Enum<?> inputType) {
		this.messageId = messageID;
		this.destination = destination;
		this.urgent = isUrgent;
		this.inputType = inputType;
		this.type = type;
	}

	/**
	 * Adds an attribute to the message, if contents was null, creates the
	 * storage
	 * 
	 * @param att
	 *            the attribute
	 */
	public void addAttribute(Attribute att) {
		if (contents == null) {
			contents = new ArrayList<Attribute>();
		}
		contents.add(att);
	}

	/**
	 * creates a new attribute with the parametros and includes it in the
	 * message
	 * 
	 * @param name
	 *            the name and key of the attribute
	 * @param value
	 *            the value of the attribute
	 */
	public void addAttribute(String name, Object value) {
		addAttribute(new Attribute(name, value));
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

	/**
	 * Returns the value of the attribute whose key maches the key passed in
	 * parameter
	 * 
	 * @param key
	 *            the key of the attribute
	 * @return the value of the attribute with maching key, or null if there was
	 *         none
	 */
	public Object getAttributeValue(String key) {
		Attribute attribute = getAttribute(key);
		if (attribute == null) {
			return null;
		} else {
			return attribute.getValue();
		}
	}

	/**
	 * Returns the attribute whose key maches the key passed in parameter
	 * 
	 * @param key
	 *            the key of the attribute
	 * @return the attribute with maching key, or null if there was none
	 */
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
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(CommunicationIds owner) {
		this.owner = owner;
	}

	public boolean isBroadcast() {
		return (this.destination == null) || (this.destination == CommunicationIds.BROADCAST);
	}

	/**
	 * @return the currentModel from the message contents, if it exist
	 */
	public AutomataModel<? extends Enum<?>,? extends State<? extends Enum<?>>> getCurrentModel() {
		return (AutomataModel<?,?>) getAttributeValue(MODEL_KEY);
	}

	/**
	 * @param currentModel
	 *            the currentModel to set is added to the contents of the
	 *            message
	 */
	public void setCurrentModel(AutomataModel<?,?> currentModel) {
		addAttribute(MODEL_KEY, currentModel);
	}

	/**
	 * Sets the inbox 
	 * @param container
	 */
	public void setContainer(Inbox container){
		this.container = container;
	}
		
	/**
	 * Removes de message from the inbox Used when the message has been used.
	 */
	public void consume(){
		container.remove(this);
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
