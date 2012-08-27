package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface AutomataModel<INPUT extends Enum<INPUT>, STATE extends State<INPUT>> extends Serializable {

	void setState(STATE state);

	STATE getState();

	ModeEnum getCurrentMode();

	void addListener(ModelListener listener);

	void notifyObservers();

	void setAutomata(AutomataContainer<INPUT, STATE, ? extends AutomataModel<INPUT, STATE>> automata);

}
