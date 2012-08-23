package core.gui.satuspanel;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * Tipo de modo de funcionamiento del indicador de vista.
 * 
 * @author gonzalo
 */
public enum ModeEnum {

	READY("Listo", Color.blue, false), RUNNING("Corriendo", Color.green, true), ESTOP("Parada de emergencia", Color.red, true), NSTOP("Parada normal", Color.red, false), IDLE("Esperando",
			Color.green, false);

	private String literal;
	private Color color;
	private boolean blink;

	ModeEnum(String literal, ImageIcon image) {

	}

	ModeEnum(String literal, Color color, boolean blink) {
		this.literal = literal;
		this.color = color;
		this.blink = blink;
	}

	public String getLiteral() {
		return literal;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isBlink() {
		return blink;
	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

}
