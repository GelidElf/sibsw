package slave1;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataModel;
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

	public Slave1Model() {
		currentMode = ModeEnum.READY;
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

}
