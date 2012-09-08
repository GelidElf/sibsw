package slave1;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;
import core.sections.AssembyStation.AssemblyStationModel;
import core.sections.ConveyorBelt.ConveyorBeltModel;
import core.sections.robot1.Robot1Model;

public class Slave1Model implements AutomataModel<Slave1Input, Slave1State> {

	private static final long serialVersionUID = -7533088185929981996L;

	private transient List<ModelListener> modelListeners = new ArrayList<ModelListener>();

	@Override
	public void addListener(ModelListener listener) {
		modelListeners.add(listener);
	}

	public void notifyObservers() {
		for (ModelListener listener : modelListeners) {
			listener.updateOnModelChange();
		}
	}

	private Slave1State currentState;
	private ConveyorBeltModel gearBeltModel;
	private ConveyorBeltModel axisBeltModel;
	private AssemblyStationModel assemblyStationModel;
	private Robot1Model robot1Model;

	public Slave1Model() {
		gearBeltModel = new ConveyorBeltModel();
		axisBeltModel = new ConveyorBeltModel();
		assemblyStationModel = new AssemblyStationModel();
		robot1Model = new Robot1Model();
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentState.getMode();
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

	public void setRobot1Model(Robot1Model model) {
		robot1Model = model;
	}

	public Robot1Model getRobo1Model() {
		return robot1Model;
	}

	@Override
	public Slave1State getState() {
		return currentState;
	}

	@Override
	public void setState(Slave1State state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<Slave1Input, Slave1State, ? extends AutomataModel<Slave1Input, Slave1State>> automata) {
		currentState = new Slave1State((Slave1Automata) automata);
	}

}
