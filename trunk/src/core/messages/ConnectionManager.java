package core.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import core.messages.enums.CommunicationIds;
import core.utilities.log.Logger;

public class ConnectionManager extends Thread{

	private Socket socket = null;
	private ObjectOutputStream  _oos = null;
	private ObjectInputStream  _ois = null;
	private Inbox inbox = null;
	private CommunicationManager commManager = null;
	private CommunicationIds owner = null;
	
	public ConnectionManager (Socket socket, CommunicationManager cm, Inbox inbox){
		this.socket = socket;
		this.owner = cm.getOwner();
		createInputAndOutputStreamsFromSocket();
		if (this.inbox == null)
			this.inbox = new Inbox();
		else
			this.inbox = inbox;
		commManager = cm;
	}
	
	public ConnectionManager (Socket socket, CommunicationIds owner, Inbox inbox){
		this.socket = socket;
		this.owner = owner;
		createInputAndOutputStreamsFromSocket();
		this.inbox = inbox;

	}

	private void createInputAndOutputStreamsFromSocket() {
		try {
			_oos = new ObjectOutputStream(this.socket.getOutputStream());
			_ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error creating input and output streams from socket: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run (){
		while (true){
			Message message =readMessageFromStream(); 
			trataMensajeRecibido(message);
		}
	}

	private void trataMensajeRecibido(Message message) {
		if (message.getDestination().equals(owner)){
			Logger.println("RecibidoMensaje en: "+owner);
			this.inbox.add(message);
		}
		else{
			Logger.println(String.format("Reenviando mensaje ID:%s desde %s",message.getID(),owner));
			commManager.sendMessage(message);
		}
	}
	
	public synchronized void writeMessage(Message message){
		message.setOwner(owner);
		try {
			_oos.writeObject(message);
			_oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Special method for naming the Communication interface in the manager so that it can send thoguth the correct 
	 * Comm object the message
	 */
	public synchronized CommunicationIds getPeer(){
		return readMessageFromStream().getOwner();
	}
	
	private Message readMessageFromStream(){
		Object o;
		try {
			o = _ois.readObject();
			if (o instanceof Message){
				System.out.println(o);
				return ((Message)o);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Inbox getInbox(){
		return this.inbox;
	}
	
	public Message readMessage() {
		return this.inbox.getMessage();		
	}

}
