	package Messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import aplication2.Configuration;

import File.ConfigurationFileReader;
import Messages.MessageFactory.MasterAtomataMessageFactory;

public class CommunicationManager {
	
	private ServerSocket _serverSocket = null;
	private Socket _socket = null;
	private HashMap <String,Comm> _comms = null;
	private Inbox _inbox = null;
	private boolean _isMaster = false;
	
	public CommunicationManager(boolean isMaster,String ID) {
		_isMaster = isMaster;
		_inbox = new Inbox();
		if (_isMaster){
			try {
				_comms = new HashMap<String,Comm>(4);
				_serverSocket = new ServerSocket();
				_serverSocket.bind(new InetSocketAddress(40000));
				

				Message messageConnect = MessageFactory.createMessage(MasterAtomataMessageFactory.BROADCAST_MESSAGE);
				messageConnect.addAttribute(MasterAtomataMessageFactory.SERVER_PORT, "40000");
				
				ByteArrayOutputStream b_out = new ByteArrayOutputStream();
				ObjectOutputStream o_out = new ObjectOutputStream(b_out);

				o_out.writeObject(messageConnect);

				byte[] b = b_out.toByteArray();
				DatagramSocket multicastSocket = new DatagramSocket(20000);
				DatagramPacket packet = new DatagramPacket(b,b.length,
						InetAddress.getByName("255.255.255.255"),40000);
				multicastSocket.send(packet);
				
				while (_comms.size() < 4){
					Comm c = new Comm(_serverSocket.accept(), this, _inbox);
					String s = c.getOwner();
					_comms.put(s, c);
					_comms.get(_comms.size()-1).run();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			try {
				_comms = new HashMap<String,Comm>(1);
				int serverPort = -1;
				
				byte[] b = new byte[32003];
				DatagramPacket dgram = new DatagramPacket(b, b.length);
				MulticastSocket socket =
				  new MulticastSocket(40000); // must bind receive side
				socket.joinGroup(InetAddress.getByName("255.255.255.255"));
	
				ByteArrayInputStream b_in = new ByteArrayInputStream(b);
				
				socket.receive(dgram); // blocks
				ObjectInputStream o_in = new ObjectInputStream(b_in);
				Message multicastMessage = (Message)o_in.readObject();
				dgram.setLength(b.length); // must reset length field!
				b_in.reset();
				
				if (multicastMessage.getID().contains(MasterAtomataMessageFactory.BROADCAST_MESSAGE)){
					Attribute attribute = multicastMessage.getAttribute(MasterAtomataMessageFactory.SERVER_PORT);
					if (attribute != null);
						serverPort = Integer.parseInt(attribute.getValue());
				}
				
				_socket = new Socket();
				_socket.bind(new InetSocketAddress(serverPort));
				_comms.put("unknown",new Comm(_socket,ID));
				_comms.get(_comms.size()-1).run();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public CommunicationManager(boolean isMaster, String ID, Configuration conf) {
		_isMaster = isMaster;
		_inbox = new Inbox();
		
		if (_isMaster){
			try {
				_comms = new HashMap<String,Comm>(4);
				_serverSocket = new ServerSocket();
				_serverSocket.bind(new InetSocketAddress(Integer.parseInt(conf.serverPort)));
				
				while (_comms.size() < 4){
					Comm c = new Comm(_serverSocket.accept(), this, _inbox);
					String s = c.getOwner();
					_comms.put(s, c);
					_comms.get(_comms.size()-1).run();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			try {
				_comms = new HashMap<String,Comm>(1);
			
				_socket = new Socket();
				_socket.bind(new InetSocketAddress(InetAddress.getByName(conf.address),Integer.parseInt(conf.port)));
				_comms.put("unknown",new Comm(_socket,ID));
				_comms.get(_comms.size()-1).run();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(Message message){
		if (_isMaster){
			if (message.get_destination().equals(MasterAtomataMessageFactory.ID))
				_inbox.add(message);
			else
				_comms.get(message.get_destination()).writeMessage(message);
		}else{
			_comms.get("unknown").writeMessage(message);
		}
	}
	
	public Inbox getInbox(String commName){
		if (commName == null)
			return null;
		else if (!_comms.containsKey(commName))
			return null;
		else
		return _comms.get(commName).getInbox();
	}
	
}
