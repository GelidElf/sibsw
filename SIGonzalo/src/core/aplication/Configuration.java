package core.aplication;

public class Configuration {

	public String configurationFileName = "conf.ini";
	public String autoDiscovery = "no";
	public String address;
	public String port;
	public String logFile;
	public String serverPort;
	
	public boolean getAutoDiscovery(){
		return autoDiscovery.equals("yes");
	}
	
	public int getPortAsInt(){
		return Integer.parseInt(port);
	}
	
	public int getServerPortAsInt(){
		return Integer.parseInt(serverPort);
	}
	
	
}
