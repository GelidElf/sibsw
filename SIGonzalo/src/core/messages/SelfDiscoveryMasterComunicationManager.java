package core.messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import core.messages.MessageFactory.MasterAtomataMessageFactory;

public class SelfDiscoveryMasterComunicationManager extends CommunicationManager {

	protected HashMap <String,ConnectionManager> conections = null;
	private int cantidad;

	public SelfDiscoveryMasterComunicationManager(String ID,int cantidad) {
		super(ID);
		this.cantidad = cantidad;
	}

	@Override
	protected void initialize() {
		try {
			loadBroadcastConfiguration();
			createAndSendMulticastMessage();
			waitFor4SocketsToConnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initializeVariables(){
		setUpVariablesForMaster();
	}

	private void setUpVariablesForMaster(){
		conections = new HashMap<String,ConnectionManager>(cantidad);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	private void loadBroadcastConfiguration() {
		serverPort = 40000;
	}

	private void createAndSendMulticastMessage() throws IOException, SocketException,
			UnknownHostException {
		Message messageConnect = MessageFactory.createMessage(MasterAtomataMessageFactory.BROADCAST_MESSAGE);
		messageConnect.addAttribute(MasterAtomataMessageFactory.SERVER_PORT, ""+serverPort);
		
		ByteArrayOutputStream b_out = new ByteArrayOutputStream();
		ObjectOutputStream o_out = new ObjectOutputStream(b_out);

		o_out.writeObject(messageConnect);

		byte[] b = b_out.toByteArray();
		DatagramSocket multicastSocket = new DatagramSocket(DATAGRAM_LENGTH);
		DatagramPacket packet = new DatagramPacket(b,b.length,
				InetAddress.getByName("255.255.255.255"),serverPort);
		multicastSocket.send(packet);
	}


	private void waitFor4SocketsToConnect() throws IOException {
		while (conections.size() < cantidad){
			Socket newSocket = serverSocket.accept();
			ConnectionManager c = new ConnectionManager(newSocket, this);
			String s = c.getOwner();
			conections.put(s, c);
			conections.get(conections.size()-1).run();
		}
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
