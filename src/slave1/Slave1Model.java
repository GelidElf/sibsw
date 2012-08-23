package slave1;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataModel;
import core.model.DummyAutomataModel;
import core.model.ModelListener;

public class Slave1Model implements AutomataModel {

	private static final long serialVersionUID = -7533088185929981996L;

	private transient List<ModelListener> modelListeners = new ArrayList<ModelListener>();
	private static Slave1Model instance;

	public static synchronized Slave1Model getInstance() {
		if (instance == null) {
			instance = new Slave1Model();
		}
		return instance;
	}

	@Override
	public void addListener(ModelListener listener) {
		modelListeners.add(listener);
	}

	public void notifyObservers() {
		for (ModelListener listener : modelListeners) {
			listener.updateOnModelChange();
		}
	}

	private ModeEnum currentMode;
	private AutomataModel gearBeltModel;
	private AutomataModel axisBeltModel;
	private AutomataModel assemblyStationModel;

	public Slave1Model() {
		currentMode = ModeEnum.READY;
		gearBeltModel = new DummyAutomataModel();
		axisBeltModel = new DummyAutomataModel();
		assemblyStationModel = new DummyAutomataModel();
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentMode;
	}

	@Override
	public void setCurrentMode(ModeEnum currentMode) {
		this.currentMode = currentMode;
		notifyObservers();
	}

	public void setGearBeltModel(AutomataModel model) {
		this.gearBeltModel = model;
	}

	public AutomataModel getGearBeltModel(){
		return gearBeltModel;
	}
	
	public void setAxisBeltModel(AutomataModel model) {
		this.axisBeltModel = model;
	}

	public AutomataModel getAxisBeltModel(){
		return axisBeltModel;
	}
	
	public void setAssemblyStationModel(AutomataModel model) {
		this.assemblyStationModel = model;
	}
	
	public AutomataModel getAssemblyStationModel() {
		return assemblyStationModel;
	}
	
}
