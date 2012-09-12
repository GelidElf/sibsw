package core.gui.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import core.sections.ConveyorBelt.ConveyorBeltManager;
import core.sections.ConveyorBelt.ConveyorBeltSimulator;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class ConveyorBeltTest extends JFrame {

	private static final long serialVersionUID = -2412052089713542223L;
	private static ConveyorBeltManager manager;

	public static void main(String[] args) {
		new ConveyorBeltTest();
	}

	public ConveyorBeltTest() {
		createModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		add(creaBotonDeMeter());
		add(creaBotonDeSacar());
		pack();
		setVisible(true);

	}

	public void createModel() {
		manager = new ConveyorBeltManager();
		ParallelPortState state = new ParallelPortState();
		manager.setState(state);
		try {
			manager.setValueByName(ConveyorBeltManager.RUNNING, 1);
			// manager.setValueByName(ConveyorBeltManager.SENSOR_INITIAL, 1);
			manager.setValueByName(ConveyorBeltManager.CAPACITY, 10);
			manager.setValueByName(ConveyorBeltManager.SPEED, 2);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator("bleh",manager);
		cbs.start();
		Logger.println("ConveyorBelLanzado");
	}

	private JButton creaBotonDeMeter() {
		JButton jButton = new JButton("Mete");
		jButton.addActionListener(new FeedActionListener(jButton, manager));
		return jButton;
	}

	private JButton creaBotonDeSacar() {
		JButton jButton = new JButton("Saca");
		jButton.addActionListener(new RemoveActionListener(jButton, manager));
		return jButton;
	}

	private class FeedActionListener implements ActionListener, ParallelPortManagerObserver {

		private ConveyorBeltManager manager;
		private JButton jButton;

		public FeedActionListener(JButton jButton, ConveyorBeltManager manager) {
			this.manager = manager;
			manager.registerObserver(this);
			this.jButton = jButton;
			updateFromPortManager(manager);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				manager.setValueByName(ConveyorBeltManager.SENSOR_LOAD, 1);
				jButton.setEnabled(false);
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void updateFromPortManager(ParallelPortManager manager) {
			try {
				if (manager.getValueByName(ConveyorBeltManager.SENSOR_LOAD) == 1) {
					jButton.setEnabled(true);
				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private class RemoveActionListener implements ActionListener, ParallelPortManagerObserver {

		private ConveyorBeltManager manager;
		private JButton jButton;

		public RemoveActionListener(JButton jButton, ConveyorBeltManager manager) {
			this.manager = manager;
			manager.registerObserver(this);
			this.jButton = jButton;
			updateFromPortManager(manager);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				manager.setValueByName(ConveyorBeltManager.SENSOR_UNLOAD, 1);
				jButton.setEnabled(false);
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void updateFromPortManager(ParallelPortManager manager) {
			try {
				if (manager.getValueByName(ConveyorBeltManager.SENSOR_UNLOAD) == 1) {
					jButton.setEnabled(true);
				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
