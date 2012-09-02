package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface State<AS extends Enum<AS>> extends Serializable{

	void execute(AS input);

	ModeEnum getMode();

}
