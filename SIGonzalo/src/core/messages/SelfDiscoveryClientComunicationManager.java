package core.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import core.aplication.Configuration;
import core.messages.MessageFactory.MasterAtomataMessageFactory;

public class SelfDiscoveryClientComunicationManager extends CommunicationManager{

	private ConnectionManager connection;
	
	public SelfDiscoveryClientComunicationManager(String ID,
			Configuration conf) {
		super(ID, conf);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		try {
			setUpVariablesForNormal();
			
			byte[] b = new byte[32003];
			DatagramPacket dgram = new DatagramPacket(b, b.length);
			MulticastSocket socket = new MulticastSocket(DEFAULT_PORT); // must bind receive side
			socket.joinGroup(InetAddress.getByName("255.255.255.255"));

			ByteArrayInputStream b_in = new ByteArrayInputStream(b);
			
			socket.receive(dgram); // blocks
			ObjectInputStream o_in = new ObjectInputStream(b_in);
			Message multicastMessage = (Message)o_in.readObject();
			dgram.setLength(b.length); // must reset length field!
			b_in.reset();
			
			serverPort = parseServerPort(serverPort, multicastMessage);
			
			_socket = new Socket();
			_socket.bind(new InetSocketAddress(serverPort));
			connection = new ConnectionManager(_socket,ID);
			connection.run();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void initializeVariables() {
		// TODO Auto-generated method stub
		
	}

	private void setUpVariablesForNormal() throws IOException {
	}
	

	private int parseServerPort(int serverPort, Message multicastMessage) {
		if (multicastMessage.getID().contains(MasterAtomataMessageFactory.BROADCAST_MESSAGE)){
			Attribute attribute = multicastMessage.getAttribute(MasterAtomataMessageFactory.SERVER_PORT);
			if (attribute != null);
				serverPort = Integer.parseInt(attribute.getValue());
		}
		return serverPort;
	}

	@Override
	public Inbox getInboxByName(String commName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}
	
}
