package core.gui.satuspanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Panel que indica el color del fondo
 * 
 * @author gonzalo
 *
 */
public class StatusPanel extends JPanel {

	private static final long serialVersionUID = -9053998826495892562L;
	private static int LONGITUD_LADO = 10;
	private ModeEnum modo = null;
	private boolean blinkingOn = true;

	public StatusPanel() {
		super();
		setSize(LONGITUD_LADO, LONGITUD_LADO);
		setMaximumSize(new Dimension(LONGITUD_LADO, LONGITUD_LADO));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		StatusPanelBlinkTimer.addStatusPanel(this);
	}

	public ModeEnum getModo() {
		return modo;
	}

	public void setModo(ModeEnum modo) {
		this.modo = modo;
		if (modo != null) {
			setBackground(modo.getColor());
			setToolTipText(modo.getLiteral());
		} else {
			setBackground(Color.BLACK);
			setToolTipText("Elemento desconectado");
		}
		validate();
	}

	public void setBlinkingOn(boolean blinkingOn) {
		this.blinkingOn = blinkingOn;
	}

	public boolean isBlinkingOn() {
		return blinkingOn;
	}

}
