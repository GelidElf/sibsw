package core.gui.satuspanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class StatusPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -9053998826495892562L;
	private static int LONGITUD_LADO = 10;
	private ModeEnum modo = null;
	
	public StatusPanel(){
		super();
		setSize(LONGITUD_LADO, LONGITUD_LADO);
		int speed = 300;
		Timer timer = new Timer(speed, this);
		timer.start();
	}

	public ModeEnum getModo() {
		return modo;
	}

	public void setModo(ModeEnum modo) {
		this.modo = modo;
		setBackground(modo.getColor());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (modo != null){
			this.setBackground(modo.getColor());
		}
	}
	
	
}
