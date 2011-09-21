package core.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionManager extends Thread{

	private Socket _socket = null;
	private ObjectOutputStream  _oos = null;
	private ObjectInputStream  _ois = null;
	private Inbox _inbox = null;
	private CommunicationManager _cm = null;
	
	public ConnectionManager (Socket socket, CommunicationManager cm, Inbox inbox){
		_socket = socket;
		createInputAndOutputStreamsFromSocket();
		if (_inbox == null)
			_inbox = new Inbox();
		else
			_inbox = inbox;
		_cm = cm;
	}
	
	public ConnectionManager (Socket socket, CommunicationManager cm){
		_socket = socket;
		createInputAndOutputStreamsFromSocket();
		_inbox = new Inbox();
		_cm = cm;
	}
	
	public ConnectionManager (Socket socket, String name){
		_socket = socket;
		createInputAndOutputStreamsFromSocket();
		_inbox = new Inbox();

	}

	private void createInputAndOutputStreamsFromSocket() {
		try {
			_oos = new ObjectOutputStream(_socket.getOutputStream());
			_ois = new ObjectInputStream(_socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error creating input and output streams from socket: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run (){
		while(true){
			try {
				
				_cm.sendMessage((Message)_ois.readObject());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void writeMessage(Message message){
		try {
			_oos.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Special method for naming the Communication interface in the manager so that it can send thoguth the correct 
	 * Comm object the message
	 */
	public synchronized String getOwner(){
		Message mes;
		try {
			mes = (Message)_ois.readObject();
			return mes.getOwner();
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
		return _inbox;
	}

	public Message readMessage() {
		return _inbox.getMessage();		
	}

}
