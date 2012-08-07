package slave1;

import core.gui.satuspanel.ModeEnum;
import core.model.AutomataModel;

public class Slave1Model implements AutomataModel{

	private static final long serialVersionUID = -7533088185929981996L;

	private ModeEnum currentMode;
	
	public Slave1Model() {
		currentMode = ModeEnum.DESCONEXION;
	}
	
	@Override
	public ModeEnum getCurrentMode() {
		return currentMode;
	}

	@Override
	public void setCurrentMode(ModeEnum currentMode) {
		this.currentMode = currentMode;
		
	}

}
