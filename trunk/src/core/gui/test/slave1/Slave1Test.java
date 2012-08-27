package core.gui.test.slave1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import slave1.Slave1Automata;
import slave1.Slave1Input;

public class Slave1Test extends JFrame {

	private static final long serialVersionUID = -2412052089713542223L;
	private static Slave1Automata slave1;

	public static void main(String[] args) {
		new Slave1Test();
	}

	public Slave1Test() {
		createModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 3));
		JPanel panelControlCintaEjes = creaPanelEje();
		JPanel panelControlCintaEngranaje = creaPanelEngranaje();
		JPanel panelControlPrincipal = creaPanelPrincipal();
		add(panelControlPrincipal);
		add(panelControlCintaEjes);
		add(panelControlCintaEngranaje);
		pack();
		setVisible(true);
	}

	private JPanel creaPanelPrincipal() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(BorderFactory.createBevelBorder(1));
		panelPrincipal.setLayout(new GridLayout(3, 1));
		panelPrincipal.add(creaBoton("START", Slave1Input.START));
		panelPrincipal.add(creaBoton("ESTOP", Slave1Input.ESTOP));
		panelPrincipal.add(creaBoton("NSTOP", Slave1Input.NSTOP));
		panelPrincipal.add(creaBoton("RESTART", Slave1Input.RESTART));
		return panelPrincipal;
	}

	private JPanel creaPanelEje() {
		JPanel panelControlCintaEjes = new JPanel();
		panelControlCintaEjes.setBorder(BorderFactory.createBevelBorder(1));
		panelControlCintaEjes.setLayout(new GridLayout(3, 1));
		panelControlCintaEjes.add(new JLabel("Eje"));
		panelControlCintaEjes.add(creaBotonDeMeterEje());
		panelControlCintaEjes.add(creaCheckAutoMeterEje());
		return panelControlCintaEjes;
	}

	private JPanel creaPanelEngranaje() {
		JPanel panelControlCintaEngranaje = new JPanel();
		panelControlCintaEngranaje.setBorder(BorderFactory.createBevelBorder(1));
		panelControlCintaEngranaje.setLayout(new GridLayout(3, 1));
		panelControlCintaEngranaje.add(new JLabel("Eje"));
		panelControlCintaEngranaje.add(creaBotonDeMeterEngranaje());
		panelControlCintaEngranaje.add(creaCheckAutoMeterEngranage());
		return panelControlCintaEngranaje;
	}

	public void createModel() {
		slave1 = new Slave1Automata(null);
	}

	private JButton creaBoton(String title, Slave1Input input) {
		JButton jButton = new JButton(title);
		jButton.addActionListener(new AlimentaActionListener(slave1, input));
		return jButton;
	}

	private JButton creaBotonDeMeterEje() {
		JButton jButton = new JButton("Mete Eje");
		jButton.addActionListener(new AlimentaActionListener(slave1, Slave1Input.MOVE_AXIS));
		return jButton;
	}

	private JButton creaBotonDeMeterEngranaje() {
		JButton jButton = new JButton("Mete Engranaje");
		jButton.addActionListener(new AlimentaActionListener(slave1, Slave1Input.MOVE_GEAR));
		return jButton;
	}

	private JCheckBox creaCheckAutoMeterEje() {
		JCheckBox jChechBox = new JCheckBox("Auto");
		jChechBox.addItemListener(new AlimentaAutomaticoActionListener(slave1, Slave1Input.AUTO_FEED_AXIS_ON, Slave1Input.AUTO_FEED_AXIS_OFF));
		return jChechBox;
	}

	private JCheckBox creaCheckAutoMeterEngranage() {
		JCheckBox jChechBox = new JCheckBox("Auto");
		jChechBox.addItemListener(new AlimentaAutomaticoActionListener(slave1, Slave1Input.AUTO_FEED_GEAR_ON, Slave1Input.AUTO_FEED_GEAR_OFF));
		return jChechBox;
	}

	private class AlimentaAutomaticoActionListener implements ItemListener {

		private Slave1Automata slave1;
		private Slave1Input cierto;
		private Slave1Input falso;

		public AlimentaAutomaticoActionListener(Slave1Automata slave1, Slave1Input cierto, Slave1Input falso) {
			this.slave1 = slave1;
			this.cierto = cierto;
			this.falso = falso;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getItemSelectable();
			JCheckBox jcb = (JCheckBox) source;
			if (jcb.isSelected()) {
				slave1.feedInput(cierto, false);
			} else {
				slave1.feedInput(falso, false);
			}
		}
	}

	private class AlimentaActionListener implements ActionListener {

		private Slave1Automata slave1;
		private Slave1Input input;

		public AlimentaActionListener(Slave1Automata manager, Slave1Input input) {
			this.slave1 = manager;
			this.input = input;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			slave1.feedInput(input, false);
		}

	}

}
