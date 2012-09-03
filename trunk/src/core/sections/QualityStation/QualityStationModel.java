package core.sections.QualityStation;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class QualityStationModel implements AutomataModel<QualityStationInput, QualityStationState> {

	private static final long serialVersionUID = -9139889456417544981L;

	private transient List<ModelListener> listeners;
	private QualityStationState state;

	public QualityStationModel() {
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
	public QualityStationState getState() {
		return state;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(QualityStationState state) {
		this.state = state;
	}

	@Override
	public void setAutomata(AutomataContainer<QualityStationInput, QualityStationState, ? extends AutomataModel<QualityStationInput, QualityStationState>> automata) {
		state = new QualityStationState((QualityStationAutomata) automata);
		notifyObservers();
	}

}
