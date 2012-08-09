package core.gui.satuspanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

/**
 * Panel que indica el color del fondo
 * 
 * @author gonzalo
 *
 */
public class StatusPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -9053998826495892562L;
	private static int LONGITUD_LADO = 10;
	private ModeEnum modo = null;
	private boolean blinkingOn = true;
	private Timer timer;
	
	public StatusPanel(){
		super();
		setSize(LONGITUD_LADO, LONGITUD_LADO);
		setMaximumSize(new Dimension(LONGITUD_LADO, LONGITUD_LADO));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		int speed = 500;
		timer = new Timer(speed, this);
		timer.start();
	}

	public ModeEnum getModo() {
		return modo;
	}

	public void setModo(ModeEnum modo) {
		this.modo = modo;
		if (modo != null){
			setBackground(modo.getColor());
			setToolTipText(modo.getLiteral());
		}else{
			setBackground(Color.BLACK);
			setToolTipText("Elemento desconectado");
		}
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (modo == null){
			this.setBackground(Color.BLACK);
		}else {
			if (!modo.isBlink()){
				this.setBackground(modo.getColor());
			}else{
				Color currentColor =this.blinkingOn?modo.getColor():Color.BLACK; 
				this.setBackground(currentColor);
				this.blinkingOn = !this.blinkingOn;
				timer.start();
			}
		}
	}
	
	
}
