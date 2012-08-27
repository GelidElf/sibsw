package core.sections.robot1;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class Robot1Model implements AutomataModel<Robot1Input, Robot1State> {

	private static final long serialVersionUID = -9139889456417544981L;

	private List<ModelListener> listeners;
	private Robot1State state;

	public Robot1Model() {
		listeners = new ArrayList<ModelListener>();
	}

	@Override
	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public ModeEnum getCurrentMode() {
		return getState().getMode();
	}

	@Override
	public Robot1State getState() {
		return state;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(Robot1State state) {
		this.state = state;
	}

	@Override
	public void setAutomata(AutomataContainer<Robot1Input, Robot1State, ? extends AutomataModel<Robot1Input, Robot1State>> automata) {
		state = new Robot1State((Robot1Automata) automata);
	}

}
