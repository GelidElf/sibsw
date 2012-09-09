package core.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import master.MasterAutomata;
import master.MasterInput;
import master.MasterModel;
import slave1.Slave1Model;
import core.file.ConfigurationFileReader;
import core.file.ConfigurationParametersFileReader;
import core.gui.mainview.MainView;
import core.gui.satuspanel.StatusPanel;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.model.ModelListener;

public class interfaz implements ModelListener {

	private MasterAutomata master;

	private Map<ConfigurationParameters, Integer> map = new HashMap<ConfigurationParameters, Integer>();
	public ConfigurationParametersFileReader confIniRead = new ConfigurationParametersFileReader("ConfigurationParameters.ini");

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

	private StatusPanel axisStatusPanel;
	private StatusPanel gearStatusPanel;
	private StatusPanel assemblyStatusPanel;
	private StatusPanel assembledStatusPanel;
	private StatusPanel finisedStatusPanel;
	private StatusPanel robot2StatusPanel;
	private StatusPanel qualityStatusPanel;
	private StatusPanel weldingStatusPanel;
	private StatusPanel robot1StatusPanel;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_19;

	private JTextArea mainConsoleView;
	private JTextArea masterConsoleView;
	private JTextArea slave1ConsoleView;
	private JTextArea slave2ConsoleView;
	private JTextArea slave3ConsoleView;

	private JScrollPane mainConsoleScroll;
	private JScrollPane masterConsoleScroll;
	private JScrollPane slave1ConsoleScroll;
	private JScrollPane slave2ConsoleScroll;
	private JScrollPane slave3ConsoleScroll;

	private ReportWindow ventanaReports;

	/**
	 * Fill the map with the configuration parameter and its value from the
	 * interface
	 */
	private void fillMap(ConfigurationParameters parameter, Integer newValue) {
		this.map.put(parameter, newValue);
		Message message = new Message("ConfigurationParameterChange", CommunicationIds.BROADCAST, true, CommunicationMessageType.CONFIGURATION, null);
		message.addAttribute(parameter.name(), newValue);
		master.sendMessage(message);
	}

	public Map<ConfigurationParameters, Integer> getMap(){
		return map;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		frame.setBounds(100, 100, 1122, 802);
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

		gearStatusPanel.setBounds(715, 464, 14, 10);
		mainView.add(gearStatusPanel);

		assemblyStatusPanel.setBounds(814, 142, 14, 10);
		mainView.add(assemblyStatusPanel);

		assembledStatusPanel.setBounds(468, 254, 14, 10);
		mainView.add(assembledStatusPanel);

		finisedStatusPanel.setBounds(223, 444, 14, 10);
		mainView.add(finisedStatusPanel);

		robot2StatusPanel.setBounds(303, 390, 14, 10);
		mainView.add(robot2StatusPanel);

		qualityStatusPanel.setBounds(138, 279, 14, 10);
		mainView.add(qualityStatusPanel);

		weldingStatusPanel.setBounds(387, 171, 14, 10);
		mainView.add(weldingStatusPanel);

		robot1StatusPanel.setBounds(655, 405, 14, 10);
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
		btnEmergencyStop.setBounds(944, 236, 152, 51);
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

		JTabbedPane configuration = new JTabbedPane(JTabbedPane.TOP);
		configuration.setBounds(10, 541, 390, 212);
		frame.getContentPane().add(configuration);

		JPanel Master = new JPanel();
		configuration.addTab("Master", null, Master, null);
		Master.setLayout(null);

		JLabel lblNewLabel = new JLabel("Time to pick assembled piece (sec.)");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 51, 215, 28);
		Master.add(lblNewLabel);

		textField = new JTextField();
		textField.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.PICK_TIME_ASSEMBLED).toString());
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.PICK_TIME_ASSEMBLED, Integer.parseInt(textField.getText()));
			}
		});

		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(307, 55, 42, 20);
		Master.add(textField);
		textField.setColumns(10);

		JLabel lblTimeToTransport = new JLabel("Time to transport and place assembled piece (sec.)");
		lblTimeToTransport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport.setBounds(10, 90, 287, 28);
		Master.add(lblTimeToTransport);

		textField_1 = new JTextField();
		textField_1.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS).toString());
		textField_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS, Integer.parseInt(textField_1.getText()));
			}
		});
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(307, 94, 42, 20);
		Master.add(textField_1);

		JLabel lblTimeToTransport_1 = new JLabel("Time to transport and place welded piece (sec.)");
		lblTimeToTransport_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport_1.setBounds(10, 129, 287, 28);
		Master.add(lblTimeToTransport_1);

		textField_2 = new JTextField();
		textField_2.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_WELDED).toString());
		textField_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_WELDED, Integer.parseInt(textField_2.getText()));
			}
		});
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(307, 133, 42, 20);
		Master.add(textField_2);

		JLabel lblRobot = new JLabel("Robot 2");
		lblRobot.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobot.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRobot.setBounds(110, 11, 115, 29);
		Master.add(lblRobot);

		JPanel Slave1 = new JPanel();
		configuration.addTab("Slave 1", null, Slave1, null);
		Slave1.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 385, 168);
		Slave1.add(tabbedPane_1);

		JPanel Robot1 = new JPanel();
		tabbedPane_1.addTab("Robot 1", null, Robot1, null);
		Robot1.setLayout(null);

		JLabel lblTimeToPick = new JLabel("Time to pick axis/gear (sec.)");
		lblTimeToPick.setBounds(5, 39, 161, 15);
		lblTimeToPick.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToPick);

		textField_3 = new JTextField();
		textField_3.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.PICK_TIME_AXIS_GEAR).toString());
		textField_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.PICK_TIME_AXIS_GEAR, Integer.parseInt(textField_3.getText()));
			}
		});
		textField_3.setBounds(319, 36, 51, 21);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_3.setColumns(10);
		Robot1.add(textField_3);

		JLabel lblTimeToTransport_2 = new JLabel("Time to transport and place axis/gear (sec.)");
		lblTimeToTransport_2.setBounds(5, 71, 246, 15);
		lblTimeToTransport_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_2);

		textField_4 = new JTextField();
		textField_4.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_AXIS_GEAR).toString());
		textField_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_AXIS_GEAR, Integer.parseInt(textField_4.getText()));
			}
		});
		textField_4.setBounds(319, 68, 51, 21);
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_4.setColumns(10);
		Robot1.add(textField_4);

		JLabel lblTimeToTransport_3 = new JLabel("Time to transport and place assembled piece (sec.)");
		lblTimeToTransport_3.setBounds(5, 109, 290, 15);
		lblTimeToTransport_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_3);

		textField_5 = new JTextField();
		textField_5.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED).toString());
		textField_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED, Integer.parseInt(textField_5.getText()));
			}
		});
		textField_5.setBounds(319, 106, 51, 21);
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
		textField_6.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_AXIS_LENGTH).toString());
		textField_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_LENGTH, Integer.parseInt(textField_6.getText()));
			}
		});
		textField_6.setBounds(185, 26, 47, 20);
		CBAxis.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_AXIS_SPEED).toString());
		textField_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_SPEED, Integer.parseInt(textField_7.getText()));
			}
		});
		textField_7.setColumns(10);
		textField_7.setBounds(185, 63, 47, 20);
		CBAxis.add(textField_7);

		textField_8 = new JTextField();
		textField_8.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_AXIS_CAPACITY).toString());
		textField_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_CAPACITY, Integer.parseInt(textField_8.getText()));
			}
		});
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
		textField_10.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_GEARS_CAPACITY).toString());
		textField_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_CAPACITY, Integer.parseInt(textField_10.getText()));
			}
		});
		textField_10.setBounds(198, 98, 45, 20);
		textField_10.setColumns(10);
		CBGears.add(textField_10);

		textField_9 = new JTextField();
		textField_9.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_GEARS_SPEED).toString());
		textField_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_SPEED, Integer.parseInt(textField_9.getText()));
			}
		});
		textField_9.setBounds(198, 67, 45, 20);
		textField_9.setColumns(10);
		CBGears.add(textField_9);

		textField_11 = new JTextField();
		textField_11.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_GEARS_LENGTH).toString());
		textField_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_LENGTH, Integer.parseInt(textField_11.getText()));
			}
		});
		textField_11.setBounds(198, 36, 45, 20);
		textField_11.setColumns(10);
		CBGears.add(textField_11);

		JPanel AssemblingStation = new JPanel();
		AssemblingStation.setLayout(null);
		tabbedPane_1.addTab("Assembling Station", null, AssemblingStation, null);

		JLabel lblActivationTimeMounting = new JLabel("Activation time mounting hydraulic cylinder valve (sec.)");
		lblActivationTimeMounting.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblActivationTimeMounting.setBounds(10, 22, 319, 15);
		AssemblingStation.add(lblActivationTimeMounting);

		textField_17 = new JTextField();
		textField_17.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.ACTIVATION_TIME_AS).toString());
		textField_17.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_AS, Integer.parseInt(textField_17.getText()));
			}
		});
		textField_17.setColumns(10);
		textField_17.setBounds(30, 48, 45, 20);
		AssemblingStation.add(textField_17);

		JPanel Slave2 = new JPanel();
		configuration.addTab("Slave 2", null, Slave2, null);
		Slave2.setLayout(null);

		JLabel lblCbLength = new JLabel("CB transport length");
		lblCbLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCbLength.setBounds(10, 40, 123, 15);
		Slave2.add(lblCbLength);

		JLabel lblCbSpeed = new JLabel("CB transport speed");
		lblCbSpeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCbSpeed.setBounds(10, 78, 123, 15);
		Slave2.add(lblCbSpeed);

		textField_12 = new JTextField();
		textField_12.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_TRANSFER_LENGTH).toString());
		textField_12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_TRANSFER_LENGTH, Integer.parseInt(textField_12.getText()));
			}
		});
		textField_12.setBounds(157, 38, 46, 20);
		Slave2.add(textField_12);
		textField_12.setColumns(10);

		textField_13 = new JTextField();
		textField_13.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_TRANSFER_SPEED).toString());
		textField_13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_TRANSFER_SPEED, Integer.parseInt(textField_12.getText()));
			}
		});
		textField_13.setColumns(10);
		textField_13.setBounds(157, 76, 46, 20);
		Slave2.add(textField_13);

		JLabel lblActivationTimeAnd = new JLabel("Activation time welding station(sec.)");
		lblActivationTimeAnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblActivationTimeAnd.setBounds(10, 115, 324, 15);
		Slave2.add(lblActivationTimeAnd);

		textField_18 = new JTextField();
		textField_18.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.ACTIVATION_TIME_WS).toString());
		textField_18.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_WS, Integer.parseInt(textField_18.getText()));
			}
		});
		textField_18.setColumns(10);
		textField_18.setBounds(233, 113, 46, 20);
		Slave2.add(textField_18);

		JPanel Slave3 = new JPanel();
		configuration.addTab("Slave 3", null, Slave3, null);
		Slave3.setLayout(null);

		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setBounds(0, 0, 385, 168);
		Slave3.add(tabbedPane_3);

		JPanel QCS = new JPanel();
		tabbedPane_3.addTab("Quality Control Station", null, QCS, null);

		JLabel lblActivationTimeQuality = new JLabel("Activation Time Quality Control Station (sec.)");
		lblActivationTimeQuality.setHorizontalAlignment(SwingConstants.LEFT);
		lblActivationTimeQuality.setFont(new Font("Tahoma", Font.PLAIN, 12));
		QCS.add(lblActivationTimeQuality);

		textField_14 = new JTextField();
		textField_14.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.ACTIVATION_TIME_QCS).toString());
		textField_14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_QCS, Integer.parseInt(textField_14.getText()));
			}
		});
		QCS.add(textField_14);
		textField_14.setColumns(10);

		JPanel CBOk = new JPanel();
		FlowLayout flowLayout = (FlowLayout) CBOk.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane_3.addTab("CB Ok", null, CBOk, null);
		tabbedPane_3.setEnabledAt(1, true);

		JLabel lblSpeedmetersminute = new JLabel("      Speed (meters/minute)  ");
		lblSpeedmetersminute.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBOk.add(lblSpeedmetersminute);

		textField_15 = new JTextField();
		textField_15.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_OK_SPEED).toString());
		textField_15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_OK_SPEED, Integer.parseInt(textField_15.getText()));
			}
		});
		textField_15.setColumns(10);
		CBOk.add(textField_15);

		JLabel lblLengthmeters = new JLabel("            Length (meters)      ");
		lblLengthmeters.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBOk.add(lblLengthmeters);

		textField_16 = new JTextField();
		textField_16.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_OK_LENGTH).toString());
		textField_16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_OK_LENGTH, Integer.parseInt(textField_16.getText()));
			}
		});
		textField_16.setColumns(10);
		CBOk.add(textField_16);

		JPanel CBWrong = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) CBWrong.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		tabbedPane_3.addTab("CB Wrong", null, CBWrong, null);

		JLabel label_5 = new JLabel("Length (meters)");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBWrong.add(label_5);

		textField_19 = new JTextField();
		textField_19.setText(confIniRead.readConfiguration().getMap().get(ConfigurationParameters.CB_WRONG_LENGTH).toString());
		textField_19.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillMap(ConfigurationParameters.CB_WRONG_LENGTH, Integer.parseInt(textField_19.getText()));
			}
		});
		textField_19.setHorizontalAlignment(SwingConstants.CENTER);
		textField_19.setColumns(10);
		CBWrong.add(textField_19);

		frame.getContentPane().add(createConsolePanel());

		ventanaReports = new ReportWindow(frame);

		// MALDITO BOTON PARA ABRIR VENTANA DE INFORMES
		JButton buttonReports = new JButton("Reports");
		buttonReports.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ventanaReports.validate();
				ventanaReports.setVisible(true);
			}
		});
		buttonReports.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonReports.setBounds(961, 497, 94, 34);
		frame.getContentPane().add(buttonReports);

		JButton btnNewButton = new JButton("Send conf");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message mensaje = new Message("Cambio de configuracion", CommunicationIds.BROADCAST, true, CommunicationMessageType.CONFIGURATION, null);
				for(ConfigurationParameters value: ConfigurationParameters.values()){
					mensaje.addAttribute(value.name(), getMap().get(value));
					master.sendMessage(mensaje);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(296, 525, 104, 34);
		frame.getContentPane().add(btnNewButton);

	}

	private JTabbedPane createConsolePanel() {
		JTabbedPane console = new JTabbedPane(JTabbedPane.TOP);
		console.setBounds(432, 541, 625, 212);

		mainConsoleView = new JTextArea("");
		mainConsoleView.setEditable(false);
		mainConsoleScroll = new JScrollPane(mainConsoleView);
		console.addTab("Main", null, mainConsoleScroll, null);

		masterConsoleView = new JTextArea("");
		masterConsoleView.setEditable(false);
		masterConsoleScroll = new JScrollPane(masterConsoleView);
		console.addTab("Master", null, masterConsoleScroll, null);

		slave1ConsoleView = new JTextArea("");
		slave1ConsoleView.setEditable(false);
		slave1ConsoleScroll = new JScrollPane(slave1ConsoleView);
		console.addTab("Slave 1", null, slave1ConsoleScroll, null);

		slave2ConsoleView = new JTextArea("");
		slave2ConsoleView.setEditable(false);
		slave2ConsoleScroll = new JScrollPane(slave2ConsoleView);
		console.addTab("Slave 2", null, slave2ConsoleScroll, null);

		slave3ConsoleView = new JTextArea("");
		slave3ConsoleView.setEditable(false);
		slave3ConsoleScroll = new JScrollPane(slave3ConsoleView);
		console.addTab("Slave 3", null, slave3ConsoleScroll, null);
		return console;
	}

	@Override
	public void updateOnModelChange() {
		MasterModel model = MasterModel.getInstance();
		setStatusPanelFor(CommunicationIds.SLAVE1, model);
		updateStatusSlave1Sections(model);
		setStatusPanelFor(CommunicationIds.SLAVE2, model);
		setStatusPanelFor(CommunicationIds.SLAVE3, model);
		ventanaReports.updateData(model.getCurrentReport());
		updateConsoles();
		frame.repaint();
	}

	private void updateConsoles() {
		MasterModel model = MasterModel.getInstance();
		String masterConsoleText = model.getConsoleBuffer(CommunicationIds.MASTER);
		String slave1ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE1);
		String slave2ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE2);
		String slave3ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE3);

		if ((masterConsoleText != null) && !masterConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.MASTER.name()+"-->"+masterConsoleText);
			masterConsoleView.append(masterConsoleText);
			autoScrollIfNeccesary(masterConsoleScroll, masterConsoleView);
		}
		if ((slave1ConsoleText != null) && !slave1ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE1.name()+"-->"+slave1ConsoleText);
			slave1ConsoleView.append(slave1ConsoleText);
			autoScrollIfNeccesary(slave1ConsoleScroll, slave1ConsoleView);
		}
		if ((slave2ConsoleText != null) && !slave2ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE2.name()+"-->"+slave2ConsoleText);
			slave2ConsoleView.append(slave2ConsoleText);
			autoScrollIfNeccesary(slave2ConsoleScroll, slave2ConsoleView);
		}
		if ((slave3ConsoleText != null) && !slave3ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE3.name()+"-->"+slave3ConsoleText);
			slave3ConsoleView.append(slave3ConsoleText);
			autoScrollIfNeccesary(slave3ConsoleScroll, slave3ConsoleView);
		}

		autoScrollIfNeccesary(mainConsoleScroll, mainConsoleView);
	}

	private void autoScrollIfNeccesary(JScrollPane scroll, JTextArea area) {
		// JScrollBar vbar = scroll.getVerticalScrollBar();
		// boolean autoScroll = ((vbar.getValue() + vbar.getVisibleAmount()) ==
		// vbar.getMaximum());
		// if (autoScroll) {
			area.setCaretPosition(area.getDocument().getLength());
		// }
	}

	private void updateStatusSlave1Sections(MasterModel model) {
		Slave1Model slave1Model = (Slave1Model) model.getModel().get(CommunicationIds.SLAVE1);
		if ((slave1Model != null) && model.isConnected(CommunicationIds.SLAVE1)) {
			gearStatusPanel.setModo(slave1Model.getGearBeltModel().getCurrentMode());
			axisStatusPanel.setModo(slave1Model.getAxisBeltModel().getCurrentMode());
			assemblyStatusPanel.setModo(slave1Model.getAssemblyStationModel().getCurrentMode());
			robot1StatusPanel.setModo(slave1Model.getRobo1Model().getCurrentMode());
		} else {
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

