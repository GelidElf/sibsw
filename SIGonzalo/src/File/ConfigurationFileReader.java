package File;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import Aplication.Configuration;

public class ConfigurationFileReader {

	private HashMap<String, String> contents = null;
	private Configuration conf = null;

	public ConfigurationFileReader(String fileName) {
		contents = new HashMap<String, String>();
		File file = new File(fileName);
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
			System.err.println("Error durante la lectura del fichero de configuracion \""+fileName+"\": " + e.getMessage());
		}
	}

	private void parseLine (String linea) throws Exception{
		Class <Configuration> configurationClass = Configuration.class;
		
		String attribute = null;
		String value = null;
		
		attribute = linea.substring(0,linea.indexOf(':'));
		int lastValueCharacterPosition = linea.length();
		if (linea.charAt(linea.length()-1) ==';')
			lastValueCharacterPosition--;
		value = linea.substring(linea.indexOf(':')+1, lastValueCharacterPosition);
		configurationClass.getField(attribute).set(conf, value);
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


