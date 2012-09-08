package master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.gui.satuspanel.ModeEnum;
import core.messages.enums.CommunicationIds;
import core.messages.enums.ReportValues;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;
import core.reports.Report;
import core.sections.robot2.Robot2Model;

public class MasterModel implements AutomataModel<MasterInput, MasterState> {

	private static final long serialVersionUID = -5649471149764787709L;
	private static MasterModel instance;
	private transient List<ModelListener> modelListeners = new ArrayList<ModelListener>();
	private Robot2Model robot2Model;

	public static synchronized MasterModel getInstance() {
		if (instance == null) {
			instance = new MasterModel();
		}
		return instance;
	}

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

	private MasterModel() {
		for (CommunicationIds id : CommunicationIds.values()) {
			connected.put(id, false);
		}
		robot2Model = new Robot2Model();
		// TODO: must read the file of reports to restore old values
		currentReport = new Report();
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
	private Map<CommunicationIds, String> consoles = new HashMap<CommunicationIds, String>();
	private MasterState currentState;
	private transient Report currentReport;

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
		return currentState.getMode();
	}

	@Override
	public MasterState getState() {
		return currentState;
	}

	@Override
	public void setState(MasterState state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<MasterInput, MasterState, ? extends AutomataModel<MasterInput, MasterState>> automata) {
		currentState = new MasterState((MasterAutomata) automata);

	}

	public void setRobot2Model(Robot2Model model) {
		robot2Model = model;
	}

	public Robot2Model getRobo1Model() {
		return robot2Model;
	}

	public void receiveSignal(ReportValues signal){
		currentReport.receiveSignal(signal);
		notifyObservers();
	}

	/**
	 * @return the currentReport
	 */
	public Report getCurrentReport() {
		return currentReport;
	}

	/**
	 * @param currentReport
	 *            the currentReport to set
	 */
	public void setCurrentReport(Report currentReport) {
		this.currentReport = currentReport;
	}

	public void setConsoles(Map<CommunicationIds, String> consoles) {
		this.consoles = consoles;
	}

	public Map<CommunicationIds, String> getConsoles() {
		return consoles;
	}

	public void putTextInBuffer(CommunicationIds id, String text){
		String newText = consoles.get(id)+text;
		consoles.put(id,newText);
		notifyObservers();
	}

	public String getConsoleBuffer(CommunicationIds id){
		String text = consoles.get(id);
		consoles.put(id,"");
		return text;
	}

}
