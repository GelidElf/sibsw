package core.messages;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.utilities.log.Logger;

/**
 * Connection manager
 * 
 * This class contains the socket and is responsable for writting and reading
 * objects from a stream This class includes the owner of the message when it
 * writes it to the socket, deals with identification of the other peer,
 * (Messages with type handshake), deals with broadcast messages
 * 
 * @author GelidElf
 * 
 */
public class ConnectionManager extends Thread {

	private static final String CLOSE_MESSAGE_ID = "CLOSE_COMMUNICATION: ";
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private Inbox inbox = null;
	private CommunicationManager commManager = null;
	private CommunicationIds owner = null;
	private CommunicationIds peer = null;
	private boolean keepRunning = true;

	public ConnectionManager(Socket socket, CommunicationManager cm, Inbox inbox) {
		super("ConnectionManagerThread");
		this.socket = socket;
		commManager = cm;
		this.owner = cm.getOwner();
		this.inbox = inbox;
		createInputAndOutputStreamsFromSocket();
	}

	/**
	 * This method waits until a message is received, identifies the other peer
	 * by the messages owner and processes the message
	 * 
	 * @return the identification of the peer
	 */
	public CommunicationIds waitTilMessageReceivedAndReturnPeer() {
		Message firstMessage = readMessageFromStream();
		peer = firstMessage.getOwner();
		trataMensajeRecibido(firstMessage);
		return peer;
	}

	/**
	 * Creates the input and output streams
	 */
	private void createInputAndOutputStreamsFromSocket() {
		try {
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			Logger.println("Error creating input and output streams from socket: " + e.getMessage());
			commManager.clientDisconnected(getPeer());
		}
	}

	/**
	 * Overriden run method, reads a message from stream and processes it.
	 */
	@Override
	public void run() {
		while (keepRunning) {
			Message message = readMessageFromStream();
			trataMensajeRecibido(message);
		}
	}

	/**
	 * Processes the message, prints that a message was received in console, and
	 * depending on the destination of the message is included in the inbox or
	 * resent if we are the master
	 * 
	 * @param message
	 *            the message to process
	 */
	private void trataMensajeRecibido(Message message) {
		if (message == null) {
			return;
		} else {
			if (isClosingMessage(message)){
				terminateConnection();
				Logger.println(message.getID());
				System.exit(1);
			}else{
				//Logger.println("RecibidoMensaje en: " + owner);
				if (somosDestinatariosDelMensaje(message) && noHemosEnviadoElMensaje(message)) {
					this.inbox.add(message);

				} else {
					if (somosElMaster()) {
						reenviamos(message);
					}
				}
			}
		}

	}

	/**
	 * Comprueba si no somos el enviador del mensaje para evitar ciclos en mensajes de broadcast.
	 * @return
	 */
	private boolean noHemosEnviadoElMensaje(Message message) {
		return message.getOwner() != owner;
	}

	/**
	 * This method sends the message through the communication manager, this way
	 * broadcast messages or particular messages will be handled by the
	 * appropiate communication manager
	 * 
	 * @param message
	 *            the message to resend
	 */
	private void reenviamos(Message message) {
		Logger.println(String.format("Reenviando mensaje ID:%s a %s", message.getID(), message.getDestination()));
		commManager.sendMessage(message);
	}

	/**
	 * Method that identifies if the owner of this connection manager is the
	 * master automata, used to allow to resend broadcast or messages intended
	 * for other automatas
	 * 
	 * @return true if this connectionManager is owned by the master, false
	 *         otherwise
	 */
	private boolean somosElMaster() {
		return owner == CommunicationIds.MASTER;
	}

	/**
	 * Method that will check if this connectionManager is the destination of
	 * the message
	 * 
	 * @param message
	 *            that contains the destination to evaluate
	 * @return true if the destination in the message is the owner, or if the
	 *         message is a broadcast
	 */
	private boolean somosDestinatariosDelMensaje(Message message) {
		return message.isBroadcast() || (message.getDestination() == owner);
	}

	/**
	 * Method that writes a message in the socket
	 * 
	 * @param message
	 *            the message to write
	 */
	public synchronized void writeMessage(Message message) {
		message.setOwner(owner);
		try {
			oos.writeObject(message);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			commManager.clientDisconnected(getPeer());
			Logger.println("Error escribiendo el mensaje");
		}
	}

	/**
	 * Special method for naming the Communication interface in the manager so
	 * that it can send thoguth the correct Comm object the message
	 */
	public synchronized CommunicationIds getPeer() {
		return peer;
	}

	/**
	 * Method that reads a message from the socket and handles the disconnect
	 * when speciffic exceptions occurr
	 * 
	 * @return the message read from the socket
	 */
	private Message readMessageFromStream() {
		Object o;
		try {
			o = ois.readObject();
			if (o instanceof Message) {
				return ((Message) o);
			}
		} catch (IOException e) {
			if ((e instanceof EOFException) || (e instanceof SocketException)) {
				commManager.clientDisconnected(getPeer());
				Logger.println("Client has disconnected. Awaiting for reconnection");
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			commManager.clientDisconnected(getPeer());
			Logger.println("Client has disconnected. Awaiting for reconnection");
		}
		return null;
	}

	/**
	 * Returns the inbox of this connection Manager
	 * 
	 * @return the inbox of this connection manager
	 */
	public Inbox getInbox() {
		return this.inbox;
	}

	/**
	 * Returns the message given by the inbox
	 * 
	 * @return the message read in the inbox, null if an error ocurred
	 */
	public Message readMessageFromInbox() {
		try {
			return this.inbox.getMessage();
		} catch (InterruptedException e) {
			Logger.println("Error leyendo mensaje: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Public method that disables the loop condition of the run method
	 */
	public void disable() {
		keepRunning = false;
	}

	/**
	 * Public method that enables the loop condition of the run method
	 */
	public void enable() {
		keepRunning = true;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *            the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @param peer
	 *            the peer to set
	 */
	public void setPeer(CommunicationIds peer) {
		this.peer = peer;
	}

	public void close(String motive){
		sendCloseMessage(motive);
		terminateConnection();
	}

	private void terminateConnection() {
		Logger.unregisterListener(commManager);
		try {
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendCloseMessage(String motive) {
		Message message = new Message(CLOSE_MESSAGE_ID+motive, peer, true, CommunicationMessageType.HANDSHAKE, null);
		writeMessage(message);
	}

	private boolean isClosingMessage(Message message){
		return (message.getType() == CommunicationMessageType.HANDSHAKE) && message.getID().startsWith(CLOSE_MESSAGE_ID);
	}

}
