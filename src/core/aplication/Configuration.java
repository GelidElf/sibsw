package core.aplication;

public class Configuration {

	public String configurationFileName = "conf.ini";
	public String autoDiscovery = "no";
	public String address;
	public String logFile;
	public String serverPort;
	public String remotePort;
	public String blinkPeriod = "500";
	
	public boolean getAutoDiscovery(){
		return autoDiscovery.equals("yes");
	}
	
	public int getRemotePortAsInt(){
		return Integer.parseInt(remotePort);
	}
	
	public int getServerPortAsInt(){
		return Integer.parseInt(serverPort);
	}
	
	public int getBlinkPeriodAsInt(){
		return Integer.parseInt(blinkPeriod);
	}
	
}
