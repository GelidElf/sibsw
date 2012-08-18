package core.gui.imagepanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import core.gui.mainview.MainView;

public class JImagePanel extends JPanel {

	private static final long serialVersionUID = 1896163022027817042L;
	private Image origBackGroundImage;
	private Image backGroundImage;

	public JImagePanel(String imagePath) {
		origBackGroundImage = new ImageIcon(MainView.class.getResource(imagePath)).getImage();
		backGroundImage = origBackGroundImage;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				backGroundImage = origBackGroundImage.getScaledInstance(getWidth(), getHeight(), 5);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
	}
	
}
