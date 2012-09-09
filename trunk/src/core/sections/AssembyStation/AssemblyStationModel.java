package core.sections.AssembyStation;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class AssemblyStationModel implements AutomataModel<AssemblyStationInput, AssemblyStationState> {

	private static final long serialVersionUID = -9137179190722641534L;

	private transient List<ModelListener> listeners;
	private AssemblyStationState currentState;

	public AssemblyStationModel() {
		listeners = new ArrayList<ModelListener>();
	}

	@Override
	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentState.getMode();
	}

	@Override
	public AssemblyStationState getState() {
		return currentState;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(AssemblyStationState state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<AssemblyStationInput, AssemblyStationState, ? extends AutomataModel<AssemblyStationInput, AssemblyStationState>> automata) {
		currentState = new AssemblyStationState((AssemblyStationAutomata) automata);
		notifyObservers();
	}

}
