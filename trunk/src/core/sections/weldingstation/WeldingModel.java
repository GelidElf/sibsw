package core.sections.weldingstation;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class WeldingModel implements AutomataModel<WeldingInput, WeldingState> {

	private static final long serialVersionUID = -2355793003691298996L;

	private transient List<ModelListener> listeners;
	private WeldingState state;

	public WeldingModel() {
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
	public WeldingState getState() {
		return state;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(WeldingState state) {
		this.state = state;
	}

	@Override
	public void setAutomata(AutomataContainer<WeldingInput, WeldingState, ? extends AutomataModel<WeldingInput, WeldingState>> automata) {
		state = new WeldingState((WeldingAutomata) automata);
	}

}
