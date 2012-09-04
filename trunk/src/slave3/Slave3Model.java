package slave3;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;
import core.sections.ConveyorBelt.ConveyorBeltModel;
import core.sections.QualityStation.QualityStationModel;

public class Slave3Model implements AutomataModel<Slave3Input, Slave3State> {

	private static final long serialVersionUID = -7386586777088206957L;
	
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

	private Slave3State currentState;
	private ConveyorBeltModel okBeltModel;
	private ConveyorBeltModel notOkBeltModel;
	private QualityStationModel qualityStationModel;

	public Slave3Model() {
		okBeltModel = new ConveyorBeltModel();
		notOkBeltModel = new ConveyorBeltModel();
		qualityStationModel = new QualityStationModel();
	}

	@Override
	public ModeEnum getCurrentMode() {
		return currentState.getMode();
	}

	public void setOkBeltModel(ConveyorBeltModel model) {
		this.okBeltModel = model;
	}

	public ConveyorBeltModel getOkBeltModel() {
		return okBeltModel;
	}

	public void setNotOkBeltModel(ConveyorBeltModel model) {
		this.notOkBeltModel = model;
	}

	public ConveyorBeltModel getNotOkBeltModel() {
		return notOkBeltModel;
	}

	public void setQualityStationModel(QualityStationModel model) {
		this.qualityStationModel = model;
	}

	public QualityStationModel getQualityStationModel() {
		return qualityStationModel;
	}

	@Override
	public Slave3State getState() {
		return currentState;
	}

	@Override
	public void setState(Slave3State state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<Slave3Input, Slave3State, ? extends AutomataModel<Slave3Input, Slave3State>> automata) {
		currentState = new Slave3State((Slave3Automata) automata);
	}

}
