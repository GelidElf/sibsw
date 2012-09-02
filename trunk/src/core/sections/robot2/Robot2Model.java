package core.sections.robot2;

import java.util.ArrayList;
import java.util.List;
import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;


public class Robot2Model implements AutomataModel<Robot2Input, core.sections.robot2.Robot2State> {

	private static final long serialVersionUID = 1258560192891249135L;

	private List<ModelListener> listeners;
	private Robot2State state;

	public Robot2Model() {
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
	public Robot2State getState() {
		return state;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(Robot2State state) {
		this.state = state;
	}

	@Override
	public void setAutomata(AutomataContainer<Robot2Input, Robot2State, ? extends AutomataModel<Robot2Input, Robot2State>> automata) {
		state = new Robot2State((Robot2Automata) automata);
	}


}
