package core.reports;

import java.util.HashMap;

import core.messages.enums.ReportValues;

public class Report {
	
	private HashMap<ReportValues, Integer> map = new HashMap<ReportValues, Integer>();
	private HashMap<ReportValues, Integer> mapTotales = new HashMap<ReportValues, Integer>();
		
	public Report(){
		for (ReportValues parameter : ReportValues.values()) {
			map.put(parameter, 0);
			mapTotales.put(parameter, 0);
		}
	}
	
	/**
	 * Store report values
	 */
	public void receiveSignal(ReportValues signal) {
		map.put(signal, map.get(signal) + 1);
		mapTotales.put(signal, mapTotales.get(signal) + 1);
	}
	
	public HashMap<ReportValues, Integer> getMap(){
		return map;
	}
	
	public HashMap<ReportValues, Integer> getMapTotales(){
		return mapTotales;
	}


}
