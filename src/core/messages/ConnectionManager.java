package core.messages;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import core.messages.enums.CommunicationIds;
import core.utilities.log.Logger;

public class ConnectionManager extends Thread {

	private Socket socket = null;
	private ObjectOutputStream _oos = null;
	private ObjectInputStream _ois = null;
	private Inbox inbox = null;
	private CommunicationManager commManager = null;
	private CommunicationIds owner = null;
	private CommunicationIds peer = null;
	private int messagesToRead = 0;
	private boolean keepRunning = true;

	public ConnectionManager(Socket socket, CommunicationManager cm, Inbox inbox) {
		super("ConnectionManagerThread");
		this.socket = socket;
		commManager = cm;
		this.owner = cm.getOwner();
		this.inbox = inbox;
		createInputAndOutputStreamsFromSocket();
	}

	public CommunicationIds waitTilMessageReceivedAndReturnPeer() {
		Message firstMessage = readMessageFromStream();
		peer = firstMessage.getOwner();
		trataMensajeRecibido(firstMessage);
		return peer;
	}

	private void createInputAndOutputStreamsFromSocket() {
		try {
			_oos = new ObjectOutputStream(this.socket.getOutputStream());
			_ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			Logger.println("Error creating input and output streams from socket: " + e.getMessage());
			commManager.clientDisconnected(getPeer());
		}
	}

	@Override
	public void run() {
		while (keepRunning) {
			Message message = readMessageFromStream();
			trataMensajeRecibido(message);
		}
	}

	private void trataMensajeRecibido(Message message) {
		if (message == null) {
			return;
		} else {
			Logger.println("RecibidoMensaje en: " + owner);
			if (somosDestinatariosDelMensaje(message)) {
				this.inbox.add(message);

			} else {
				if (somosElMaster()) {
					reenviamos(message);
				}
			}
		}

	}

	private void reenviamos(Message message) {
		Logger.println(String.format("Reenviando mensaje ID:%s desde %s", message.getID(), owner));
		commManager.sendMessage(message);
	}

	private boolean somosElMaster() {
		return owner == CommunicationIds.MASTER;
	}

	private boolean somosDestinatariosDelMensaje(Message message) {
		return message.isBroadcast() || (message.getDestination() == owner);
	}

	public synchronized void writeMessage(Message message) {
		message.setOwner(owner);
		try {
			_oos.writeObject(message);
			_oos.flush();
			_oos.reset();
		} catch (IOException e) {
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

	private Message readMessageFromStream() {
		Object o;
		try {
			o = _ois.readObject();
			if (o instanceof Message) {
				return ((Message) o);
			}
		} catch (IOException e) {
			if ((e instanceof EOFException) || (e instanceof SocketException)) {
				Logger.println("Client has disconnected. Awaiting for reconnection");
				commManager.clientDisconnected(getPeer());
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			Logger.println("Client has disconnected. Awaiting for reconnection");
			commManager.clientDisconnected(getPeer());
		}
		return null;
	}

	public Inbox getInbox() {
		return this.inbox;
	}

	public Message readMessageFromInbox() {
		try {
			return this.inbox.getMessage();
		} catch (InterruptedException e) {
			Logger.println("Error leyendo mensaje: " + e.getMessage());
			return null;
		}
	}

	/**
	 * @return the messagesToRead
	 */
	public int getMessagesToRead() {
		return messagesToRead;
	}

	/**
	 * @param messagesToRead
	 *            the messagesToRead to set
	 */
	public void setMessagesToRead(int messagesToRead) {
		this.messagesToRead = messagesToRead;
	}

	public void disable() {
		keepRunning = false;
	}

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
	 * @param peer the peer to set
	 */
	public void setPeer(CommunicationIds peer) {
		this.peer = peer;
	}

}
