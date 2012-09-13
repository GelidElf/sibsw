package core.messages;

import core.messages.enums.CommunicationIds;
import core.utilities.log.LoggerListener;

/**
 * This interface has the public accesible methods that all implementations of
 * the communicaction managers must implement.
 * 
 * A communicacion manager is the element that will handle communication between
 * automatas.
 * 
 * @author GelidElf
 * 
 */
public interface CommunicationManager extends LoggerListener{

	/**
	 * This method sends the message to the destination specified on the message
	 * 
	 * @param message
	 *            the message to be sent
	 */
	public void sendMessage(Message message);

	/**
	 * Returns the inbox where this object is storing the messages received
	 * 
	 * @return the inbox for this communication manager
	 */
	public Inbox getInbox();

	/**
	 * Setter for the inbox in this communication manager, used mainly to
	 * provide several communication managers with the same shared inbox
	 * 
	 * @param inbox
	 *            the inbox to be setted
	 */
	public void setInbox(Inbox inbox);

	/**
	 * Returns the owner of this Communication element, this should be always
	 * the Automata that created the object. This method is used to identify
	 * messages for broadcast, identify ownership of received messages, create
	 * speciffic behavoiur in the communication manager depending on who is the
	 * father
	 * 
	 * @return the owner of this communication manager
	 */
	public CommunicationIds getOwner();

	/**
	 * This method must contain the initializing code for the manager. This
	 * usually means, waiting for clients to connect, or trying to connect to
	 * the server
	 */
	public void initialize();

	/**
	 * Places the message in the parameter in the inbox used by the
	 * communication manager.
	 * 
	 * @param message
	 *            the message to be inserted into the inbox
	 */
	public void feed(Message message);

	/**
	 * This method is called when a client is disconnected, it must implement
	 * proper notification, code to wait for client to reconnect, or codeto try
	 * to reconnect
	 * 
	 * @param commId
	 */
	public void clientDisconnected(CommunicationIds commId);

	/**
	 * Informs this communicationManager that the disconection proces has started
	 * Used in the loops to connect or reconnect
	 * @param disconnectInProgress
	 */
	public void setDisconnectInProgress(boolean disconnectInProgress);
}
