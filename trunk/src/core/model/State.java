package core.model;

import core.gui.satuspanel.ModeEnum;

public interface State<AS extends Enum<AS>> {

	void execute(AS input);

	ModeEnum getMode();

}
