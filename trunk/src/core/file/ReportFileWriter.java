package core.file;

import java.io.FileWriter;

import core.messages.enums.ReportValues;
import core.reports.Report;

public class ReportFileWriter {
	
	public void readConfiguration(String fileName, Report informe) {
		try {
			FileWriter writer = new FileWriter(fileName);
			for (ReportValues value: ReportValues.values()){
				writer.write(new String(value.name() + ":" + informe.getMapTotales().get(value) + ";\n"));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.println("Error: Unable to parse configuration file \"" + fileName + "\": " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		ReportFileWriter writer = new ReportFileWriter();
		writer.readConfiguration("ax.txt", new Report());
	}

}
