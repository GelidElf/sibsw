package core.model;

import core.gui.satuspanel.ModeEnum;

public interface AutomataStatesInternalImplementation<INPUT extends Enum<INPUT>, STATE extends State<INPUT>> {

	AutomataStatesInternalImplementation<INPUT, STATE> executeInternal(STATE currentState, INPUT input);

	ModeEnum getMode();

}
