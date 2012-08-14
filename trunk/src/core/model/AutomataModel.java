package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface AutomataModel extends Serializable{

	ModeEnum getCurrentMode();
	void setCurrentMode(ModeEnum currentMode);
	void addListener(ModelListener listener);
	void notifyObservers();
	
}
