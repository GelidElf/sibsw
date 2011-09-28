package core.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionManager extends Thread{

	private Socket socket = null;
	private ObjectOutputStream  _oos = null;
	private ObjectInputStream  _ois = null;
	private Inbox inbox = null;
	private CommunicationManager commManager = null;
	private String applicationId = null;
	
	public ConnectionManager (Socket socket, CommunicationManager cm, Inbox inbox){
		this.socket = socket;
		applicationId = cm.getApplicationId();
		createInputAndOutputStreamsFromSocket();
		if (this.inbox == null)
			this.inbox = new Inbox();
		else
			this.inbox = inbox;
		commManager = cm;
	}
	
	public ConnectionManager (Socket socket, String applicationId, Inbox inbox){
		this.socket = socket;
		this.applicationId = applicationId;
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
		if (message.getDestination().equals(applicationId)){
			System.out.println("RecibidoMensaje en: "+applicationId);
			this.inbox.add(message);
		}
		else{
			System.out.println(String.format("Reenviando mensaje ID:%s desde %s",message.getID(),applicationId));
			commManager.sendMessage(message);
		}
	}
	
	public synchronized void writeMessage(Message message){
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
	public synchronized String getNameOfPeer(){
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
