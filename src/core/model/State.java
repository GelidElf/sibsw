package core.model;

import java.io.Serializable;

import core.gui.satuspanel.ModeEnum;

public interface State<AS extends Enum<AS>> extends Serializable{

	/**
	 * Executes the command input
	 * 
	 * @param input
	 *            the input to react to
	 * @return true if there was a change in the state, false if the state was
	 *         kept the same
	 */
	boolean execute(AS input);

	ModeEnum getMode();

}
