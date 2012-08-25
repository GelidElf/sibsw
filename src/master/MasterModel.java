package master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.model.AutomataModel;
import core.model.ModelListener;

public class MasterModel implements AutomataModel<MasterInput, MasterState> {

	private static final long serialVersionUID = -5649471149764787709L;
	private static MasterModel instance;
	private transient List<ModelListener> modelListeners = new ArrayList<ModelListener>();

	public static synchronized MasterModel getInstance() {
		if (instance == null) {
			instance = new MasterModel();
		}
		return instance;
	}

	public void addListener(ModelListener listener) {
		modelListeners.add(listener);
	}

	public void notifyObservers() {
		for (ModelListener listener : modelListeners) {
			listener.updateOnModelChange();
		}
	}

	private MasterModel() {
		for (CommunicationIds id : CommunicationIds.values()) {
			connected.put(id, false);
		}
	}

	private Map<CommunicationIds, Boolean> connected = new HashMap<CommunicationIds, Boolean>();

	public boolean isConnected(CommunicationIds id) {
		if (connected.get(id) == null) {
			return false;
		} else {
			return connected.get(id);
		}
	}

	private Map<CommunicationIds, AutomataModel<?, ?>> models = new HashMap<CommunicationIds, AutomataModel<?, ?>>();
	private ModeEnum currentMode;

	public void setModel(CommunicationIds id, AutomataModel<?, ?> model) {
		if (model != null) {
			connected.put(id, true);
			models.put(id, model);
		} else {
			connected.put(id, false);
		}
		notifyObservers();
	}

	public Map<CommunicationIds, AutomataModel<?, ?>> getModel() {
		return models;
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

	@Override
	public MasterState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(MasterState state) {
		// TODO Auto-generated method stub

	}

}
