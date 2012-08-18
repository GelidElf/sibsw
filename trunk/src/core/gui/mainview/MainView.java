package core.gui.mainview;

import core.gui.imagepanel.JImagePanel;

public class MainView extends JImagePanel {

	private static final long serialVersionUID = -1370803844314118482L;
	private static final String BACKGROUND_IMAGE = "/images/VistaCompleta2.png";

	public MainView() {
		super(BACKGROUND_IMAGE);
		setVisible(true);
	}
	
}
