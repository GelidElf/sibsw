package core.configurationParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.messages.Attribute;
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

	public List<Attribute> getAsAttributeList(){
		List<Attribute> lista = new ArrayList<Attribute>();
		for (ConfigurationParameters parameter :map.keySet()){
			lista.add(new Attribute(parameter.name(),map.get(parameter)));
		}
		return lista;
	}

}
