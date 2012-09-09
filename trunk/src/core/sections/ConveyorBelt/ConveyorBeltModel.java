package core.sections.ConveyorBelt;

import java.util.ArrayList;
import java.util.List;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class ConveyorBeltModel implements AutomataModel<ConveyorBeltInput, ConveyorBeltState> {

	private static final long serialVersionUID = 695493474794614418L;

	private transient List<ModelListener> listeners;
	private ConveyorBeltState currentState;

	public ConveyorBeltModel() {
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
	public ConveyorBeltState getState() {
		return currentState;
	}

	@Override
	public void notifyObservers() {
		for (ModelListener listener : listeners) {
			listener.updateOnModelChange();
		}
	}

	@Override
	public void setState(ConveyorBeltState state) {
		currentState = state;
	}

	@Override
	public void setAutomata(AutomataContainer<ConveyorBeltInput, ConveyorBeltState, ? extends AutomataModel<ConveyorBeltInput, ConveyorBeltState>> automata) {
		currentState = new ConveyorBeltState((ConveyorBeltAutomata) automata);
		notifyObservers();
	}

}
