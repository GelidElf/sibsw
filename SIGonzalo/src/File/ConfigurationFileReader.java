package File;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ConfigurationFileReader {

	private HashMap<String, String> _contents = null;

	public ConfigurationFileReader(String fileName) {
		_contents = new HashMap<String, String>();
		File file = new File(fileName);
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
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void parseLine (String linea){
		String attribute = null;
		String value = null;
		
		attribute = linea.substring(0,linea.indexOf(':'));
		value = linea.substring(linea.indexOf(':'), linea.length());
		_contents.put(attribute, value);
		
	}

	public String get (String key){
		if (_contents.keySet().contains(key))
			return _contents.get(key);
		else
			return null;
	}
	

}
