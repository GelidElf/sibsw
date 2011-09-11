package core.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import core.aplication.Configuration;



public class ConfigurationFileReader {

	private HashMap<String, String> contents = null;
	private Configuration conf = null;

	public ConfigurationFileReader(String fileName) {
		contents = new HashMap<String, String>();
		conf = new Configuration();
		try{

			FileInputStream fstream = new FileInputStream(fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				parseLine(strLine);
			}

			in.close();
		}catch (Exception e){
			System.err.println("Error: Unable to parse configuration file \""+fileName+"\": " + e.getMessage());
		}
	}

	private void parseLine (String linea){
		Class <Configuration> configurationClass = Configuration.class;
		
		String attribute = null;
		String value = null;
		
		attribute = linea.substring(0,linea.indexOf(':'));
		int lastValueCharacterPosition = linea.length();
		if (linea.charAt(linea.length()-1) ==';')
			lastValueCharacterPosition--;
		value = linea.substring(linea.indexOf(':')+1, lastValueCharacterPosition);
		try {
			configurationClass.getField(attribute).set(conf, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			System.out.println(String.format("Warning: Property %s does not exist.",attribute));
			e.printStackTrace();
		}
	}

	public String get (String key){
		if (contents.keySet().contains(key))
			return contents.get(key);
		else
			return null;
	}

	public Configuration readConfiguration() {
		return conf;
	}
	

}


