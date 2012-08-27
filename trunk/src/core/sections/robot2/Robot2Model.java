package core.sections.robot2;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataContainer;
import core.model.AutomataModel;
import core.model.ModelListener;

public class Robot2Model implements AutomataModel<Robot2Input, core.sections.robot2.Robot2State> {

	private static final long serialVersionUID = 1258560192891249135L;

	@Override
	public void addListener(ModelListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public ModeEnum getCurrentMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Robot2State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(Robot2State state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutomata(AutomataContainer<Robot2Input, Robot2State, ? extends AutomataModel<Robot2Input, Robot2State>> automata) {
		// TODO Auto-generated method stub

	}

}
