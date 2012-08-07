package core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.messages.enums.CommunicationIds;

public class MasterModel {

	private static List<ModelListener> modelListeners = new ArrayList<ModelListener>(); 
	private static MasterModel instance;
	
	public static synchronized MasterModel getInstance(){
		if (instance == null){
			instance = new MasterModel();
		}
		return instance;
	}
	
	public static void addListener (ModelListener listener){
		modelListeners.add(listener);
	}
	
	public void notifyObservers(){
		for (ModelListener listener: modelListeners){
			listener.update();
		}
	}
	
	private MasterModel(){
		for (CommunicationIds id: CommunicationIds.values()){
			connected.put(id,false);
		}
	}
	
	private Map<CommunicationIds, Boolean> connected = new HashMap<CommunicationIds, Boolean>();
	public void setConnected(CommunicationIds id, Boolean value){
		connected.put(id,value);
		notifyObservers();
	}
	public Map<CommunicationIds, Boolean> getConnected(){
		return connected;
	}
	
	private Map<CommunicationIds, AutomataModel> models = new HashMap<CommunicationIds, AutomataModel>();
	public void setModel(CommunicationIds id, AutomataModel model){
		models.put(id, model);
		notifyObservers();
	}
	public Map<CommunicationIds, AutomataModel> getModel(){
		return models;
	}
	
	
}
