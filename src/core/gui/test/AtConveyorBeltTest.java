package core.gui.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import core.sections.ConveyorBelt.ConveyorBeltAutomata;
import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class AtConveyorBeltTest extends JFrame implements ParallelPortManagerObserver{

	private static final long serialVersionUID = -2412052089713542223L;
	private ConveyorBeltAutomata atcb;
	private JButton jButton;

	public static void main(String[] args) {
		new AtConveyorBeltTest();
	}

	public AtConveyorBeltTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		createModel();
		add(creaBotonDeSacar());
		pack();
		setVisible(true);

	}

	public void createModel() {
		ConveyorBeltManager manager = new ConveyorBeltManager();
		manager.configure(10, 2);
		manager.registerObserver(this);
		atcb = new ConveyorBeltAutomata(null, manager, null);
		atcb.startCommand();
	}

	private JButton creaBotonDeSacar() {
		jButton = new JButton("Saca");
		jButton.addActionListener(new RemoveActionListener(jButton, atcb));
		return jButton;
	}

	private class RemoveActionListener implements ActionListener {

		private ConveyorBeltAutomata atcb;
		private JButton jButton;

		public RemoveActionListener(JButton jButton, ConveyorBeltAutomata atcb) {
			this.atcb = atcb;
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			atcb.piecePicked();
		}

	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(ConveyorBeltManager.SENSOR_UNLOAD)){
			try {
				boolean value = manager.getValueByNameAsBoolean(ConveyorBeltManager.SENSOR_UNLOAD);
				jButton.setEnabled(value);
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
		
	}

}
