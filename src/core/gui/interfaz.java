package core.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import master.MasterAutomata;
import master.MasterInput;
import master.MasterModel;
import slave1.Slave1Model;
import core.file.ConfigurationFileReader;
import core.gui.mainview.MainView;
import core.gui.satuspanel.StatusPanel;
import core.messages.enums.CommunicationIds;
import core.model.ModelListener;

public class interfaz implements ModelListener {

	private MasterAutomata master;

	private JFrame frame;
	private Map<CommunicationIds, StatusPanel> statusPanels;
	private MainView mainView;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private final Action action = new SwingAction();

	private StatusPanel axisStatusPanel;
	private StatusPanel gearStatusPanel;
	private StatusPanel assemblyStatusPanel;
	private StatusPanel assembledStatusPanel;
	private StatusPanel finisedStatusPanel;
	private StatusPanel robot2StatusPanel;
	private StatusPanel qualityStatusPanel;
	private StatusPanel weldingStatusPanel;
	private StatusPanel robot1StatusPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationFileReader confReader = new ConfigurationFileReader("master.ini");
					interfaz window = new interfaz(new MasterAutomata(confReader.readConfiguration()));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public interfaz(MasterAutomata master) {
		this.master = master;
		initialize();
		MasterModel.getInstance().addListener(this);
		updateOnModelChange();
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1091, 802);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		mainView = new MainView();
		mainView.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainView.setBounds(10, 11, 909, 501);
		frame.getContentPane().add(mainView);
		mainView.setLayout(null);

		axisStatusPanel = new StatusPanel();
		gearStatusPanel = new StatusPanel();
		assemblyStatusPanel = new StatusPanel();
		assembledStatusPanel = new StatusPanel();
		finisedStatusPanel = new StatusPanel();
		robot2StatusPanel = new StatusPanel();
		qualityStatusPanel = new StatusPanel();
		weldingStatusPanel = new StatusPanel();
		robot1StatusPanel = new StatusPanel();

		axisStatusPanel.setBounds(731, 372, 14, 10);
		mainView.add(axisStatusPanel);

		gearStatusPanel.setBounds(731, 466, 14, 10);
		mainView.add(gearStatusPanel);

		assemblyStatusPanel.setBounds(814, 142, 14, 10);
		mainView.add(assemblyStatusPanel);

		assembledStatusPanel.setBounds(468, 254, 14, 10);
		mainView.add(assembledStatusPanel);

		finisedStatusPanel.setBounds(207, 444, 14, 10);
		mainView.add(finisedStatusPanel);

		robot2StatusPanel.setBounds(303, 390, 14, 10);
		mainView.add(robot2StatusPanel);

		qualityStatusPanel.setBounds(138, 279, 14, 10);
		mainView.add(qualityStatusPanel);

		weldingStatusPanel.setBounds(387, 171, 14, 10);
		mainView.add(weldingStatusPanel);

		robot1StatusPanel.setBounds(654, 390, 14, 10);
		mainView.add(robot1StatusPanel);

		JPanel panel = new JPanel();
		panel.setBounds(961, 11, 104, 124);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		statusPanels = new HashMap<CommunicationIds, StatusPanel>();
		StatusPanel slave1StatusPanel = new StatusPanel();
		slave1StatusPanel.setBounds(66, 31, 22, 20);
		slave1StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE1, slave1StatusPanel);
		panel.add(slave1StatusPanel);

		StatusPanel slave2StatusPanel = new StatusPanel();
		slave2StatusPanel.setBounds(66, 62, 22, 20);
		slave2StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE2, slave2StatusPanel);
		panel.add(slave2StatusPanel);

		StatusPanel slave3StatusPanel = new StatusPanel();
		slave3StatusPanel.setBounds(66, 93, 22, 20);
		slave3StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE3, slave3StatusPanel);
		panel.add(slave3StatusPanel);

		JLabel lblSlave = new JLabel("Slave 1");
		lblSlave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave.setBounds(10, 37, 46, 14);
		panel.add(lblSlave);

		JLabel lblSlave_1 = new JLabel("Slave 2");
		lblSlave_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave_1.setBounds(10, 68, 46, 14);
		panel.add(lblSlave_1);

		JLabel lblSlave_2 = new JLabel("Slave 3");
		lblSlave_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave_2.setBounds(10, 99, 46, 14);
		panel.add(lblSlave_2);

		JLabel lblComponents = new JLabel("Components");
		lblComponents.setBounds(10, 5, 78, 15);
		panel.add(lblComponents);
		lblComponents.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblComponents.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnEmergencyStop = new JButton("Emergency Stop");
		btnEmergencyStop.setBounds(926, 236, 139, 51);
		frame.getContentPane().add(btnEmergencyStop);
		btnEmergencyStop.setForeground(Color.RED);
		btnEmergencyStop.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEmergencyStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.feedInput(MasterInput.ESTOP, true);
			}
		});

		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(971, 191, 86, 34);
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.feedInput(MasterInput.NSTOP, true);
			}
		});
		btnStop.setForeground(Color.RED);
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnStop);

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(971, 146, 86, 34);
		btnStart.setForeground(new Color(0, 128, 0));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				master.feedInput(MasterInput.START, true);
			}
		});
		frame.getContentPane().add(btnStart);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 557, 349, 196);
		frame.getContentPane().add(tabbedPane);

		JPanel Master = new JPanel();
		tabbedPane.addTab("Master", null, Master, null);
		Master.setLayout(null);

		JLabel lblNewLabel = new JLabel("Time to pick assembled piece");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 51, 176, 28);
		Master.add(lblNewLabel);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(271, 55, 42, 20);
		Master.add(textField);
		textField.setColumns(10);

		JLabel lblTimeToTransport = new JLabel("Time to transport and place assembled piece");
		lblTimeToTransport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport.setBounds(10, 90, 253, 28);
		Master.add(lblTimeToTransport);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(271, 94, 42, 20);
		Master.add(textField_1);

		JLabel lblTimeToTransport_1 = new JLabel("Time to transport and place welded piece");
		lblTimeToTransport_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport_1.setBounds(10, 129, 253, 28);
		Master.add(lblTimeToTransport_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(271, 134, 42, 20);
		Master.add(textField_2);

		JLabel lblRobot = new JLabel("Robot 2");
		lblRobot.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobot.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRobot.setBounds(110, 11, 115, 29);
		Master.add(lblRobot);

		JPanel Slave1 = new JPanel();
		tabbedPane.addTab("Slave 1", null, Slave1, null);
		Slave1.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 344, 168);
		Slave1.add(tabbedPane_1);

		JPanel Robot1 = new JPanel();
		tabbedPane_1.addTab("Robot 1", null, Robot1, null);
		Robot1.setLayout(null);

		JLabel lblRobot_1 = new JLabel("Robot 1");
		lblRobot_1.setBounds(149, 11, 51, 15);
		lblRobot_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobot_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		Robot1.add(lblRobot_1);

		JLabel lblTimeToPick = new JLabel("Time to pick axis/gear");
		lblTimeToPick.setBounds(5, 39, 161, 15);
		lblTimeToPick.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToPick);

		textField_3 = new JTextField();
		textField_3.setBounds(278, 36, 51, 21);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_3.setColumns(10);
		Robot1.add(textField_3);

		JLabel lblTimeToTransport_2 = new JLabel("Time to transport and place axis/gear");
		lblTimeToTransport_2.setBounds(5, 71, 246, 15);
		lblTimeToTransport_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_2);

		textField_4 = new JTextField();
		textField_4.setBounds(278, 68, 51, 21);
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_4.setColumns(10);
		Robot1.add(textField_4);

		JLabel lblTimeToTransport_3 = new JLabel("Time to transport and place assembled piece");
		lblTimeToTransport_3.setBounds(5, 109, 246, 15);
		lblTimeToTransport_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_3);

		textField_5 = new JTextField();
		textField_5.setBounds(278, 106, 51, 21);
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_5.setColumns(10);
		Robot1.add(textField_5);

		JPanel CBAxis = new JPanel();
		tabbedPane_1.addTab("CB Axis", null, CBAxis, null);
		CBAxis.setLayout(null);

		JLabel lblLength = new JLabel("Length (meters)");
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLength.setBounds(10, 28, 153, 22);
		CBAxis.add(lblLength);

		JLabel lblSpeed = new JLabel("Speed (meters/minute)");
		lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSpeed.setBounds(10, 61, 153, 22);
		CBAxis.add(lblSpeed);

		JLabel lblCapacity = new JLabel("Capacity");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCapacity.setBounds(10, 94, 73, 22);
		CBAxis.add(lblCapacity);

		textField_6 = new JTextField();
		textField_6.setBounds(185, 26, 47, 20);
		CBAxis.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(185, 63, 47, 20);
		CBAxis.add(textField_7);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(185, 96, 47, 20);
		CBAxis.add(textField_8);

		JPanel CBGears = new JPanel();
		tabbedPane_1.addTab("CB Gears", null, CBGears, null);
		CBGears.setLayout(null);

		JLabel label = new JLabel("Length (meters)");
		label.setBounds(10, 38, 91, 15);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBGears.add(label);

		JLabel label_1 = new JLabel("Speed (meters/minute)");
		label_1.setBounds(10, 72, 130, 15);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBGears.add(label_1);

		JLabel label_2 = new JLabel("Capacity");
		label_2.setBounds(10, 100, 45, 15);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBGears.add(label_2);

		textField_10 = new JTextField();
		textField_10.setBounds(198, 98, 45, 20);
		textField_10.setColumns(10);
		CBGears.add(textField_10);

		textField_9 = new JTextField();
		textField_9.setBounds(198, 67, 45, 20);
		textField_9.setColumns(10);
		CBGears.add(textField_9);

		textField_11 = new JTextField();
		textField_11.setBounds(198, 36, 45, 20);
		textField_11.setColumns(10);
		CBGears.add(textField_11);

		JPanel Slave2 = new JPanel();
		tabbedPane.addTab("Slave 2", null, Slave2, null);
		Slave2.setLayout(null);

		JLabel lblCbLength = new JLabel("CB length");
		lblCbLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCbLength.setBounds(10, 40, 79, 15);
		Slave2.add(lblCbLength);

		JLabel lblCbSpeed = new JLabel("CB speed");
		lblCbSpeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCbSpeed.setBounds(10, 78, 79, 15);
		Slave2.add(lblCbSpeed);

		textField_12 = new JTextField();
		textField_12.setBounds(101, 38, 46, 20);
		Slave2.add(textField_12);
		textField_12.setColumns(10);

		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(99, 76, 46, 20);
		Slave2.add(textField_13);

		JPanel Slave3 = new JPanel();
		tabbedPane.addTab("Slave 3", null, Slave3, null);
		Slave3.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Activation time (seconds)");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 55, 152, 14);
		Slave3.add(lblNewLabel_1);

		JLabel lblQualityControlStation = new JLabel("Quality control station and OK Conveyor Belt");
		lblQualityControlStation.setHorizontalAlignment(SwingConstants.CENTER);
		lblQualityControlStation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQualityControlStation.setBounds(33, 11, 282, 29);
		Slave3.add(lblQualityControlStation);

		JLabel lblLength_1 = new JLabel("Length (meters)");
		lblLength_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLength_1.setBounds(10, 80, 152, 14);
		Slave3.add(lblLength_1);

		JLabel lblSpeed_1 = new JLabel("Speed (meters/minute)");
		lblSpeed_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSpeed_1.setBounds(10, 106, 152, 14);
		Slave3.add(lblSpeed_1);

		JButton btnReports = new JButton("Reports");
		btnReports.setAction(action);
		btnReports.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReports.setBounds(10, 523, 139, 23);
		frame.getContentPane().add(btnReports);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(430, 557, 635, 196);
		frame.getContentPane().add(tabbedPane_2);

		JPanel panel_3 = new JPanel();
		tabbedPane_2.addTab("Main", null, panel_3, null);

		JPanel masterConsoleView = new JPanel();
		tabbedPane_2.addTab("Master", null, masterConsoleView, null);

		JPanel slave1ConsoleView = new JPanel();
		tabbedPane_2.addTab("Slave 1", null, slave1ConsoleView, null);

		JPanel panel_1 = new JPanel();
		tabbedPane_2.addTab("Slave 2", null, panel_1, null);

		JPanel panel_2 = new JPanel();
		tabbedPane_2.addTab("Slave 3", null, panel_2, null);
		btnReports.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame reports = new JFrame();
				reports.setBounds(100, 100, 751, 646);
				reports.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				reports.getContentPane().setLayout(null);

			}
		});
	}

	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = 7656906617014037779L;

		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	@Override
	public void updateOnModelChange() {
		MasterModel model = MasterModel.getInstance();
		setStatusPanelFor(CommunicationIds.SLAVE1, model);
		updateStatusSlave1Sections(model);
		setStatusPanelFor(CommunicationIds.SLAVE2, model);
		setStatusPanelFor(CommunicationIds.SLAVE3, model);
		frame.repaint();
	}

	private void updateStatusSlave1Sections(MasterModel model) {
		Slave1Model slave1Model = (Slave1Model) model.getModel().get(CommunicationIds.SLAVE1);
		if (slave1Model != null && model.isConnected(CommunicationIds.SLAVE1)) {
			gearStatusPanel.setModo(slave1Model.getGearBeltModel().getCurrentMode());
			axisStatusPanel.setModo(slave1Model.getAxisBeltModel().getCurrentMode());
			assemblyStatusPanel.setModo(slave1Model.getAssemblyStationModel().getCurrentMode());
			robot1StatusPanel.setModo(slave1Model.getRobo1Model().getCurrentMode());
		}else{
			gearStatusPanel.setModo(null);
			axisStatusPanel.setModo(null);
			assemblyStatusPanel.setModo(null);
			robot1StatusPanel.setModo(null);
		}
	}

	private void setStatusPanelFor(CommunicationIds commID, MasterModel model) {
		statusPanels.get(commID).setModo(model.isConnected(commID) ? model.getModel().get(commID).getCurrentMode() : null);
	}
}
