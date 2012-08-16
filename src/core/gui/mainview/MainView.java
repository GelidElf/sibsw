package core.gui.mainview;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MainView extends JPanel {

	private static final long serialVersionUID = -1370803844314118482L;
	private static final String BACKGROUND_IMAGE = "images/VistaCompleta1.png";
	private Image backGroundImage;

	public MainView() {
		backGroundImage = Toolkit.getDefaultToolkit().createImage(BACKGROUND_IMAGE);
		setSize(449, 327);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}
