package slave1;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataModel;
import core.model.ModelListener;
import core.sections.AssembyStation.AssemblyStationModel;
import core.sections.ConveyorBelt.ConveyorBeltModel;

public class Slave1Model implements AutomataModel<Slave1Input, Slave1State> {

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
	private ConveyorBeltModel gearBeltModel;
	private ConveyorBeltModel axisBeltModel;
	private AssemblyStationModel assemblyStationModel;

	public Slave1Model() {
		currentMode = ModeEnum.READY;
		gearBeltModel = new ConveyorBeltModel();
		axisBeltModel = new ConveyorBeltModel();
		assemblyStationModel = new AssemblyStationModel();
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

	public void setGearBeltModel(ConveyorBeltModel model) {
		this.gearBeltModel = model;
	}

	public ConveyorBeltModel getGearBeltModel() {
		return gearBeltModel;
	}

	public void setAxisBeltModel(ConveyorBeltModel model) {
		this.axisBeltModel = model;
	}

	public ConveyorBeltModel getAxisBeltModel() {
		return axisBeltModel;
	}

	public void setAssemblyStationModel(AssemblyStationModel model) {
		this.assemblyStationModel = model;
	}

	public AssemblyStationModel getAssemblyStationModel() {
		return assemblyStationModel;
	}

	@Override
	public Slave1State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(Slave1State state) {
		// TODO Auto-generated method stub

	}

}
