package core.file;

import java.io.FileWriter;

import core.configurationParameters.ConfigurationParametersClass;
import core.messages.enums.ConfigurationParameters;

public class ConfigurationParametersFileWriter {
	
	public void readConfiguration(String fileName, ConfigurationParametersClass parameters) {
		try {
			FileWriter writer = new FileWriter(fileName);
			for (ConfigurationParameters value: ConfigurationParameters.values()){
				writer.write(new String(value.name() + ":" + parameters.getMap().get(value) + ";\n"));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.println("Error: Unable to parse configuration file \"" + fileName + "\": " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		ConfigurationParametersFileWriter writer = new ConfigurationParametersFileWriter();
		writer.readConfiguration("ax.txt", new ConfigurationParametersClass());
	}

}
