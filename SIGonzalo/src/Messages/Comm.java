package Messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Comm extends Thread{

	private Socket _socket = null;
	private ObjectOutputStream  _oos = null;
	private ObjectInputStream  _ois = null;
	private Inbox _inbox = null;
	
	public Comm (Socket socket, Inbox inbox){
		_socket = socket;
		try {
			_oos = new ObjectOutputStream(_socket.getOutputStream());
			_ois = new ObjectInputStream(_socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (_inbox == null)
			_inbox = new Inbox();
		else
			_inbox = inbox;
	}
	
	public Comm (Socket socket){
		_socket = socket;
		try {
			_oos = new ObjectOutputStream(_socket.getOutputStream());
			_ois = new ObjectInputStream(_socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_inbox = new Inbox();

	}
	
	public void run (){
		while(true){
			try {
				_inbox.add((Message)_ois.readObject());
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
	
}
