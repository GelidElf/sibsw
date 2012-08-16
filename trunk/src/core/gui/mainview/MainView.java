package core.gui.mainview;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainView extends JPanel {

	private static final long serialVersionUID = -1370803844314118482L;
	private static final String BACKGROUND_IMAGE = "/images/VistaCompleta2.png";
	private Image origBackGroundImage;
	private Image backGroundImage;

	public MainView() {
		origBackGroundImage = new ImageIcon(MainView.class.getResource(BACKGROUND_IMAGE)).getImage();
		backGroundImage = origBackGroundImage;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				backGroundImage = origBackGroundImage.getScaledInstance(getWidth(), getHeight(), 5);
			}
		});
		setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
	}

}
