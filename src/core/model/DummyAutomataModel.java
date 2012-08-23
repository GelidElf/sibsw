package core.model;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;

public class DummyAutomataModel implements AutomataModel {

	private static final long serialVersionUID = -3041854356947485337L;

	private ModeEnum currentMode;
	private List<ModelListener> listeners;

	public DummyAutomataModel() {
		listeners = new ArrayList<ModelListener>();
		currentMode = ModeEnum.READY;
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentMode;
	}

	@Override
	public void setCurrentMode(ModeEnum currentMode) {
		this.currentMode = currentMode;
	}

	@Override
	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}
}
