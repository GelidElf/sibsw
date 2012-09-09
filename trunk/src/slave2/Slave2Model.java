package slave2;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;
import core.sections.ConveyorBelt.ConveyorBeltModel;
import core.sections.weldingstation.WeldingModel;

public class Slave2Model implements AutomataModel<Slave2Input, Slave2State> {

	private static final long serialVersionUID = 7295893442823312716L;

	private transient List<ModelListener> modelListeners = new ArrayList<ModelListener>();

	@Override
	public void addListener(ModelListener listener) {
		modelListeners.add(listener);
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : modelListeners) {
			listener.updateOnModelChange();
		}
	}

	private Slave2State currentState;
	private ConveyorBeltModel transferBeltModel;
	private WeldingModel weldingModel;

	public Slave2Model() {
		transferBeltModel = new ConveyorBeltModel();
		weldingModel = new WeldingModel();
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentState.getMode();
	}

	public void setTransferBeltModel(ConveyorBeltModel model) {
		this.transferBeltModel = model;
	}

	public ConveyorBeltModel getTransferBeltModel() {
		return transferBeltModel;
	}

	public void setWeldingModel (WeldingModel model) {
		this.weldingModel = model;
	}

	public WeldingModel getWeldingModel() {
		return weldingModel;
	}

	@Override
	public Slave2State getState() {
		return currentState;
	}

	@Override
	public void setState(Slave2State state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<Slave2Input, Slave2State, ? extends AutomataModel<Slave2Input, Slave2State>> automata) {
		currentState = new Slave2State((Slave2Automata) automata);
		notifyObservers();
	}

}
