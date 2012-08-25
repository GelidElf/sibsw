package core.gui.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.sections.AssembyStation.AssemblyStationAutomata;
import core.sections.AssembyStation.AssemblyStationManager;

public class AtAssemblyStationTest extends JFrame {

	private static final long serialVersionUID = -2412052089713542223L;
	private AssemblyStationAutomata atas;

	public static void main(String[] args) {
		new AtAssemblyStationTest();
	}

	public AtAssemblyStationTest() {
		createModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		add(creaBotonDeEje());
		add(creaBotonDeEngranaje());
		add(creaBotonDePiezaCompleta());
		pack();
		setVisible(true);

	}

	public void createModel() {
		AssemblyStationManager manager = new AssemblyStationManager();
		manager.configure(10);
		atas = new AssemblyStationAutomata(null, manager);
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(AssemblyStationManager.ASSEMBLING_TIME,"32"));
		atas.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atas.injectMessage(mess2);
	}

	private JButton creaBotonDeEje() {
		JButton jButton = new JButton("Eje");
		jButton.addActionListener(new EjeActionListener(jButton, atas));
		return jButton;
	}

	private JButton creaBotonDeEngranaje() {
		JButton jButton = new JButton("Engranaje");
		jButton.addActionListener(new EngranajeActionListener(jButton, atas));
		return jButton;
	}
	
	private JButton creaBotonDePiezaCompleta() {
		JButton jButton = new JButton("Pieza Completa");
		jButton.addActionListener(new PiezaCompletaActionListener(jButton, atas));
		return jButton;
	}
	
	private class EjeActionListener implements ActionListener {

		private AssemblyStationAutomata atas;
		private JButton jButton;

		public EjeActionListener(JButton jButton, AssemblyStationAutomata atas) {
			this.atas = atas;
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			atas.putAxis();
		}

	}

	private class EngranajeActionListener implements ActionListener {

		private AssemblyStationAutomata atas;
		private JButton jButton;

		public EngranajeActionListener(JButton jButton, AssemblyStationAutomata atas) {
			this.atas = atas;
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			atas.putEngranaje();
		}

	}
	
	private class PiezaCompletaActionListener implements ActionListener {

		private AssemblyStationAutomata atas;
		private JButton jButton;

		public PiezaCompletaActionListener(JButton jButton, AssemblyStationAutomata atas) {
			this.atas = atas;
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			atas.sacaPieza();
		}

	}
	
}
