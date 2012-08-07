package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface AutomataModel extends Serializable{

	public ModeEnum getCurrentMode();
	public void setCurrentMode(ModeEnum currentMode);
	
}
