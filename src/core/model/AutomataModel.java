package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface AutomataModel<INPUT extends Enum<INPUT>, STATE extends State<INPUT>> extends Serializable {

	void setState(STATE state);

	STATE getState();

	ModeEnum getCurrentMode();

	@Deprecated
	void setCurrentMode(ModeEnum currentMode);

	void addListener(ModelListener listener);

	void notifyObservers();

}
