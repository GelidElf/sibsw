package core.gui.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import core.sections.ConveyorBelt.ATConveyorBelt;
import core.sections.ConveyorBelt.ConveyorBeltManager;

public class AtConveyorBeltTest extends JFrame {

	private static final long serialVersionUID = -2412052089713542223L;
	private ATConveyorBelt atcb;

	public static void main(String[] args) {
		new AtConveyorBeltTest();
	}

	public AtConveyorBeltTest() {
		createModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		add(creaBotonDeSacar());
		pack();
		setVisible(true);

	}

	public void createModel() {
		ConveyorBeltManager manager = new ConveyorBeltManager();
		manager.configure(10, 2);
		atcb = new ATConveyorBelt(null, manager);
	}

	private JButton creaBotonDeSacar() {
		JButton jButton = new JButton("Saca");
		jButton.addActionListener(new RemoveActionListener(jButton, atcb));
		return jButton;
	}

	private class RemoveActionListener implements ActionListener {

		private ATConveyorBelt atcb;
		private JButton jButton;

		public RemoveActionListener(JButton jButton, ATConveyorBelt atcb) {
			this.atcb = atcb;
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			atcb.piecePicked();
		}

	}

}
