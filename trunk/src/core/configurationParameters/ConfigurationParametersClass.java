package core.configurationParameters;

import java.util.HashMap;
import core.messages.enums.ConfigurationParameters;

public class ConfigurationParametersClass {
	
	private HashMap<ConfigurationParameters, Integer> map = new HashMap<ConfigurationParameters, Integer>();
			
	public ConfigurationParametersClass(){
		for (ConfigurationParameters parameter : ConfigurationParameters.values()) {
			map.put(parameter, 0);
		}
	}
	
	/**
	 * Store report values
	 */
	private void receiveSignal(ConfigurationParameters signal) {
		map.put(signal, map.get(signal) + 1);
	}
	
	public HashMap<ConfigurationParameters, Integer> getMap(){
		return map;
	}



}
