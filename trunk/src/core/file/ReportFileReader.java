package core.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import core.messages.enums.ReportValues;
import core.reports.Report;

public class ReportFileReader {

	private HashMap<String, String> contents = null;
	private Report conf = null;

	public ReportFileReader(String fileName) {
		contents = new HashMap<String, String>();
		conf = new Report();
		try {

			FileInputStream fstream = new FileInputStream(fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				parseLine(strLine);
			}

			in.close();
		} catch (Exception e) {
			System.err.println("Error: Unable to parse configuration file \"" + fileName + "\": " + e.getMessage());
		}
	}

	
	private void parseLine(String linea) {
		String attribute = null;
		String value = null;

		attribute = linea.substring(0, linea.indexOf(':'));
		int lastValueCharacterPosition = linea.length();
		if (linea.charAt(linea.length() - 1) == ';') {
			lastValueCharacterPosition--;
		}
		value = linea.substring(linea.indexOf(':') + 1, lastValueCharacterPosition);
		conf.getMapTotales().put(ReportValues.getEnum(attribute), Integer.parseInt(value));
	}

	public String get(String key) {
		if (contents.keySet().contains(key)) {
			return contents.get(key);
		} else {
			return null;
		}
	}

	public Report readConfiguration() {
		return conf;
	}

}
