package core.gui.satuspanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class StatusPanelBlinkTimer implements ActionListener {

	private static Timer timer;
	private static List<StatusPanel> statusPanels = null;
	static {
		statusPanels = new ArrayList<StatusPanel>();
		int speed = 600;
		timer = new Timer(speed, new StatusPanelBlinkTimer());
		timer.setRepeats(true);
		timer.start();
	}

	public static void addStatusPanel(StatusPanel statusPanel) {
		statusPanels.add(statusPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (StatusPanel statusPanel : statusPanels) {
			updateAllRegistered(statusPanel);
		}
	}

	private void updateAllRegistered(StatusPanel statusPanel) {
		ModeEnum modo = statusPanel.getModo();
		if (modo == null) {
			statusPanel.setBackground(Color.BLACK);
		} else {
			if (!modo.isBlink()) {
				statusPanel.setBackground(modo.getColor());
			} else {
				Color currentColor = statusPanel.isBlinkingOn() ? modo.getColor() : Color.BLACK;
				statusPanel.setBackground(currentColor);
				statusPanel.setBlinkingOn(!statusPanel.isBlinkingOn());
			}
		}
	}

}
