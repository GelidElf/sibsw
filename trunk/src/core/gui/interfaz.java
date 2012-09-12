package core.gui;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import master.MasterAutomata;
import master.MasterInput;
import master.MasterModel;
import slave1.Slave1Model;
import slave2.Slave2Model;
import slave3.Slave3Model;
import core.configurationParameters.ConfigurationParametersClass;
import core.file.ConfigurationFileReader;
import core.gui.mainview.MainView;
import core.gui.satuspanel.StatusPanel;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.messages.enums.ConfigurationParameters;
import core.model.ModelListener;

public class interfaz implements ModelListener {

	private MasterAutomata master;

	private ConfigurationParametersClass currentScadaConfiguration;

	private JFrame frame;
	private Map<CommunicationIds, StatusPanel> statusPanels;
	private MainView mainView;

	private StatusPanel axisStatusPanel;
	private StatusPanel gearStatusPanel;
	private StatusPanel assemblyStatusPanel;
	private StatusPanel transferStatusPanel;
	private StatusPanel okStatusPanel;
	private StatusPanel notOkStatusPanel;
	private StatusPanel robot2StatusPanel;
	private StatusPanel qualityStatusPanel;
	private StatusPanel weldingStatusPanel;
	private StatusPanel robot1StatusPanel;
	private StatusPanel masterStatusPanel;

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

	JButton btnStart = new JButton("Start");

	/**
	 * Fill the map with the configuration parameter and its value from the
	 * interface
	 */
	private void fillMap(ConfigurationParameters parameter, Integer newValue) {
		currentScadaConfiguration.getMap().put(parameter, newValue);
		Message message = new Message("ConfigurationParameterChange", CommunicationIds.BROADCAST, true, CommunicationMessageType.CONFIGURATION, null);
		message.addAttribute(parameter.name(), newValue);
		master.sendMessage(message);
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
		currentScadaConfiguration = MasterModel.getInstance().getCurrentScadaConfiguration();
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
		transferStatusPanel = new StatusPanel();
		okStatusPanel = new StatusPanel();
		notOkStatusPanel = new StatusPanel();
		robot2StatusPanel = new StatusPanel();
		qualityStatusPanel = new StatusPanel();
		weldingStatusPanel = new StatusPanel();
		robot1StatusPanel = new StatusPanel();
		masterStatusPanel = new StatusPanel();

		axisStatusPanel.setBounds(731, 372, 14, 10);
		mainView.add(axisStatusPanel);

		gearStatusPanel.setBounds(715, 464, 14, 10);
		mainView.add(gearStatusPanel);

		assemblyStatusPanel.setBounds(814, 142, 14, 10);
		mainView.add(assemblyStatusPanel);

		transferStatusPanel.setBounds(468, 254, 14, 10);
		mainView.add(transferStatusPanel);

		okStatusPanel.setBounds(223, 444, 14, 10);
		mainView.add(okStatusPanel);

		notOkStatusPanel.setBounds(468, 464, 14, 10);
		mainView.add(notOkStatusPanel);

		robot2StatusPanel.setBounds(303, 390, 14, 10);
		mainView.add(robot2StatusPanel);

		qualityStatusPanel.setBounds(138, 279, 14, 10);
		mainView.add(qualityStatusPanel);

		weldingStatusPanel.setBounds(387, 171, 14, 10);
		mainView.add(weldingStatusPanel);

		robot1StatusPanel.setBounds(655, 405, 14, 10);
		mainView.add(robot1StatusPanel);

		JPanel panel = new JPanel();
		panel.setBounds(961, 11, 104, 166);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		statusPanels = new HashMap<CommunicationIds, StatusPanel>();

		StatusPanel masterStatusPanel = new StatusPanel();
		masterStatusPanel.setBounds(66, 32, 22, 20);
		masterStatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.MASTER, masterStatusPanel);
		panel.add(masterStatusPanel);

		StatusPanel slave1StatusPanel = new StatusPanel();
		slave1StatusPanel.setBounds(66, 63, 22, 20);
		slave1StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE1, slave1StatusPanel);
		panel.add(slave1StatusPanel);

		StatusPanel slave2StatusPanel = new StatusPanel();
		slave2StatusPanel.setBounds(66, 94, 22, 20);
		slave2StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE2, slave2StatusPanel);
		panel.add(slave2StatusPanel);

		StatusPanel slave3StatusPanel = new StatusPanel();
		slave3StatusPanel.setBounds(66, 125, 22, 20);
		slave3StatusPanel.setVisible(true);
		statusPanels.put(CommunicationIds.SLAVE3, slave3StatusPanel);
		panel.add(slave3StatusPanel);

		JLabel lblSlave = new JLabel("Slave 1");
		lblSlave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave.setBounds(10, 69, 46, 14);
		panel.add(lblSlave);

		JLabel lblSlave_1 = new JLabel("Slave 2");
		lblSlave_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave_1.setBounds(10, 100, 46, 14);
		panel.add(lblSlave_1);

		JLabel lblSlave_2 = new JLabel("Slave 3");
		lblSlave_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlave_2.setBounds(10, 131, 46, 14);
		panel.add(lblSlave_2);

		JLabel lblComponents = new JLabel("Components");
		lblComponents.setBounds(10, 11, 78, 15);
		panel.add(lblComponents);
		lblComponents.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblComponents.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblMaster = new JLabel("Master");
		lblMaster.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMaster.setBounds(10, 37, 46, 14);
		panel.add(lblMaster);

		JButton btnEmergencyStop = new JButton("Emergency Stop");
		btnEmergencyStop.setBounds(944, 279, 152, 51);
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
		btnStop.setBounds(971, 233, 86, 34);
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.feedInput(MasterInput.NSTOP, true);
			}
		});
		btnStop.setForeground(Color.RED);
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnStop);

		btnStart.setBounds(971, 188, 86, 34);
		btnStart.setForeground(new Color(0, 128, 0));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStart.setEnabled(false); //Al principio no se ha conectado ninguno, nos esperamos que el update normal cambie el estado cuando toque
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

		JLabel lblTimeToTransport = new JLabel("Time to transport and place assembled piece (sec.)");
		lblTimeToTransport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport.setBounds(10, 90, 287, 28);
		Master.add(lblTimeToTransport);

		JLabel lblTimeToTransport_1 = new JLabel("Time to transport and place welded piece (sec.)");
		lblTimeToTransport_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeToTransport_1.setBounds(10, 129, 287, 28);
		Master.add(lblTimeToTransport_1);

		JLabel lblRobot = new JLabel("Robot 2");
		lblRobot.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobot.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRobot.setBounds(110, 11, 115, 29);
		Master.add(lblRobot);

		final JSpinner time_to_pick_ass = new JSpinner();
		time_to_pick_ass.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.PICK_TIME_ASSEMBLED, (Integer)time_to_pick_ass.getModel().getValue());
			}
		});
		time_to_pick_ass.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.PICK_TIME_ASSEMBLED).intValue()), 5, 15, 1));
		time_to_pick_ass.setBounds(307, 56, 42, 20);
		Master.add(time_to_pick_ass);
		fillMap(ConfigurationParameters.PICK_TIME_ASSEMBLED, (Integer)time_to_pick_ass.getModel().getValue());


		final JSpinner t_trans_place_ass = new JSpinner();
		t_trans_place_ass.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS, (Integer)t_trans_place_ass.getModel().getValue());
			}
		});
		t_trans_place_ass.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED_IN_WS).intValue()), 5, 15, 1));
		t_trans_place_ass.setBounds(307, 95, 42, 20);
		Master.add(t_trans_place_ass);

		final JSpinner t_trans_place_wel = new JSpinner();
		t_trans_place_wel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_WELDED, (Integer)t_trans_place_wel.getModel().getValue());
			}
		});
		t_trans_place_wel.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_WELDED).intValue()), 5, 15, 1));
		t_trans_place_wel.setBounds(307, 134, 42, 20);
		Master.add(t_trans_place_wel);

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

		JLabel lblTimeToTransport_2 = new JLabel("Time to transport and place axis/gear (sec.)");
		lblTimeToTransport_2.setBounds(5, 71, 246, 15);
		lblTimeToTransport_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_2);

		JLabel lblTimeToTransport_3 = new JLabel("Time to transport and place assembled piece (sec.)");
		lblTimeToTransport_3.setBounds(5, 109, 290, 15);
		lblTimeToTransport_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Robot1.add(lblTimeToTransport_3);

		final JSpinner t_pick_axis_gear = new JSpinner();
		t_pick_axis_gear.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.PICK_TIME_AXIS_GEAR, (Integer)t_pick_axis_gear.getModel().getValue());
			}
		});
		t_pick_axis_gear.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.PICK_TIME_AXIS_GEAR).intValue()), 5, 15, 1));
		t_pick_axis_gear.setBounds(315, 37, 41, 20);
		Robot1.add(t_pick_axis_gear);

		final JSpinner t_trans_place_axis_gear = new JSpinner();
		t_trans_place_axis_gear.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_AXIS_GEAR, (Integer)t_trans_place_axis_gear.getModel().getValue());
			}
		});
		t_trans_place_axis_gear.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_AXIS_GEAR).intValue()), 5, 15, 1));
		t_trans_place_axis_gear.setBounds(315, 69, 41, 20);
		Robot1.add(t_trans_place_axis_gear);

		final JSpinner t_trans_place_ass_p = new JSpinner();
		t_trans_place_ass_p.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED, (Integer)t_trans_place_ass_p.getModel().getValue());
			}
		});
		t_trans_place_ass_p.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.TRANSPORT_PLACE_TIME_ASSEMBLED).intValue()), 5, 15, 1));
		t_trans_place_ass_p.setBounds(315, 107, 41, 20);
		Robot1.add(t_trans_place_ass_p);

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

		final JSpinner cb_axis_length = new JSpinner();
		cb_axis_length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_LENGTH, (Integer)cb_axis_length.getModel().getValue());
			}
		});
		cb_axis_length.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_AXIS_LENGTH).intValue()), 10, 65, 1));
		cb_axis_length.setBounds(199, 30, 43, 20);
		CBAxis.add(cb_axis_length);

		final JSpinner cb_axis_speed = new JSpinner();
		cb_axis_speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_SPEED, (Integer)cb_axis_speed.getModel().getValue());
			}
		});
		cb_axis_speed.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_AXIS_SPEED).intValue()), 20, 55, 5));
		cb_axis_speed.setBounds(199, 63, 43, 20);
		CBAxis.add(cb_axis_speed);

		final JSpinner cb_axis_capa = new JSpinner();
		cb_axis_capa.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_AXIS_CAPACITY, (Integer)cb_axis_capa.getModel().getValue());
			}
		});
		cb_axis_capa.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_AXIS_CAPACITY).intValue()), 50, 64, 1));
		cb_axis_capa.setBounds(199, 96, 43, 20);
		CBAxis.add(cb_axis_capa);

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

		final JSpinner cb_gears_length = new JSpinner();
		cb_gears_length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_LENGTH, (Integer)cb_gears_length.getModel().getValue());
			}
		});
		cb_gears_length.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_GEARS_LENGTH).intValue()), 10, 65, 1));
		cb_gears_length.setBounds(196, 36, 45, 20);
		CBGears.add(cb_gears_length);

		final JSpinner cb_gears_speed = new JSpinner();
		cb_gears_speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_SPEED, (Integer)cb_gears_speed.getModel().getValue());
			}
		});
		cb_gears_speed.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_GEARS_SPEED).intValue()), 20, 55, 5));
		cb_gears_speed.setBounds(196, 70, 45, 20);
		CBGears.add(cb_gears_speed);

		final JSpinner cb_gears_capa = new JSpinner();
		cb_gears_capa.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_GEARS_CAPACITY, (Integer)cb_gears_capa.getModel().getValue());
			}
		});
		cb_gears_capa.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_GEARS_CAPACITY).intValue()), 50, 64, 1));
		cb_gears_capa.setBounds(196, 98, 45, 20);
		CBGears.add(cb_gears_capa);

		JPanel AssemblingStation = new JPanel();
		AssemblingStation.setLayout(null);
		tabbedPane_1.addTab("Assembling Station", null, AssemblingStation, null);

		JLabel lblActivationTimeMounting = new JLabel("Activation time mounting hydraulic cylinder valve (sec.)");
		lblActivationTimeMounting.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblActivationTimeMounting.setBounds(10, 22, 319, 15);
		AssemblingStation.add(lblActivationTimeMounting);

		final JSpinner act_t_ass = new JSpinner();
		act_t_ass.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_AS, (Integer)act_t_ass.getModel().getValue());
			}
		});
		act_t_ass.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.ACTIVATION_TIME_AS).intValue()), 3, 15, 1));
		act_t_ass.setBounds(326, 20, 42, 20);
		AssemblingStation.add(act_t_ass);

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

		JLabel lblActivationTimeAnd = new JLabel("Activation time welding station(sec.)");
		lblActivationTimeAnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblActivationTimeAnd.setBounds(10, 115, 324, 15);
		Slave2.add(lblActivationTimeAnd);

		final JSpinner cb_trans_length = new JSpinner();
		cb_trans_length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_TRANSFER_LENGTH, (Integer)cb_trans_length.getModel().getValue());
			}
		});
		cb_trans_length.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_TRANSFER_LENGTH).intValue()), 10, 65, 1));
		cb_trans_length.setBounds(254, 38, 44, 20);
		Slave2.add(cb_trans_length);

		final JSpinner cb_trans_speed = new JSpinner();
		cb_trans_speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_TRANSFER_SPEED, (Integer)cb_trans_speed.getModel().getValue());
			}
		});
		cb_trans_speed.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_TRANSFER_SPEED).intValue()), 20, 55, 5));
		cb_trans_speed.setBounds(254, 76, 44, 20);
		Slave2.add(cb_trans_speed);

		final JSpinner act_t_welding = new JSpinner();
		act_t_welding.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_WS, (Integer)act_t_welding.getModel().getValue());
			}
		});
		act_t_welding.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.ACTIVATION_TIME_WS).intValue()), 30, 60, 1));
		act_t_welding.setBounds(254, 113, 44, 20);
		Slave2.add(act_t_welding);

		JPanel Slave3 = new JPanel();
		configuration.addTab("Slave 3", null, Slave3, null);
		Slave3.setLayout(null);

		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setBounds(0, 0, 385, 168);
		Slave3.add(tabbedPane_3);

		JPanel QCS = new JPanel();
		tabbedPane_3.addTab("Quality Control Station", null, QCS, null);
		QCS.setLayout(null);

		JLabel lblActivationTimeQuality = new JLabel("Activation Time Quality Control Station (sec.)");
		lblActivationTimeQuality.setBounds(12, 26, 248, 15);
		lblActivationTimeQuality.setHorizontalAlignment(SwingConstants.LEFT);
		lblActivationTimeQuality.setFont(new Font("Tahoma", Font.PLAIN, 12));
		QCS.add(lblActivationTimeQuality);

		final JSpinner act_t_qcs = new JSpinner();
		act_t_qcs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.ACTIVATION_TIME_QCS, (Integer)act_t_qcs.getModel().getValue());
			}
		});
		act_t_qcs.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.ACTIVATION_TIME_QCS).intValue()), 5, 30, 1));
		act_t_qcs.setBounds(298, 24, 49, 20);
		QCS.add(act_t_qcs);

		JPanel CBOk = new JPanel();
		tabbedPane_3.addTab("CB Ok", null, CBOk, null);
		tabbedPane_3.setEnabledAt(1, true);
		CBOk.setLayout(null);

		JLabel lblSpeedmetersminute = new JLabel("      Speed (meters/minute)  ");
		lblSpeedmetersminute.setBounds(0, 24, 162, 15);
		lblSpeedmetersminute.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBOk.add(lblSpeedmetersminute);

		JLabel lblLengthmeters = new JLabel("            Length (meters)      ");
		lblLengthmeters.setBounds(-20, 52, 163, 15);
		lblLengthmeters.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBOk.add(lblLengthmeters);

		final JSpinner cb_ok_speed = new JSpinner();
		cb_ok_speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_OK_SPEED, (Integer)cb_ok_speed.getModel().getValue());
			}
		});
		cb_ok_speed.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_OK_SPEED).intValue()), 20, 55, 5));
		cb_ok_speed.setBounds(200, 22, 42, 20);
		CBOk.add(cb_ok_speed);

		final JSpinner cb_ok_length = new JSpinner();
		cb_ok_length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_OK_LENGTH, (Integer)cb_ok_length.getModel().getValue());
			}
		});
		cb_ok_length.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_OK_LENGTH).intValue()), 10, 65, 1));
		cb_ok_length.setBounds(200, 50, 42, 20);
		CBOk.add(cb_ok_length);

		JPanel CBWrong = new JPanel();
		tabbedPane_3.addTab("CB Wrong", null, CBWrong, null);
		CBWrong.setLayout(null);

		JLabel label_5 = new JLabel("Length (meters)");
		label_5.setBounds(12, 24, 91, 15);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBWrong.add(label_5);

		final JSpinner cb_wrong_length = new JSpinner();
		cb_wrong_length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fillMap(ConfigurationParameters.CB_WRONG_LENGTH, (Integer)cb_wrong_length.getModel().getValue());
			}
		});
		cb_wrong_length.setModel(new SpinnerNumberModel((currentScadaConfiguration.getMap().get(ConfigurationParameters.CB_WRONG_LENGTH).intValue()), 10, 65, 1));
		cb_wrong_length.setBounds(151, 22, 49, 20);
		CBWrong.add(cb_wrong_length);

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

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Message mensaje = new Message("Cambio de configuracion", CommunicationIds.BROADCAST, true, CommunicationMessageType.CONFIGURATION, null);
				for(ConfigurationParameters value: ConfigurationParameters.values()){
					mensaje.addAttribute(value.name(), currentScadaConfiguration.getMap().get(value));
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
		statusPanels.get(CommunicationIds.MASTER).setModo(model.getCurrentMode());
		updateStatusMasterSections(model);
		setStatusPanelFor(CommunicationIds.SLAVE1, model);
		updateStatusSlave1Sections(model);
		setStatusPanelFor(CommunicationIds.SLAVE2, model);
		updateStatusSlave2Sections(model);
		setStatusPanelFor(CommunicationIds.SLAVE3, model);
		updateStatusSlave3Sections(model);
		ventanaReports.updateData(model.getCurrentReport());
		updateConsoles();
		updateBotonStart();
		frame.repaint();
	}


	private void updateStatusMasterSections(MasterModel masterModel) {
		if (masterModel != null){
			masterStatusPanel.setModo(masterModel.getCurrentMode());
			robot2StatusPanel.setModo(masterModel.getRobo2Model().getCurrentMode());
		}else{
			masterStatusPanel.setModo(null);
			robot2StatusPanel.setModo(null);
		}
	}

	private void updateConsoles() {
		MasterModel model = MasterModel.getInstance();
		String masterConsoleText = model.getConsoleBuffer(CommunicationIds.MASTER);
		String slave1ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE1);
		String slave2ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE2);
		String slave3ConsoleText = model.getConsoleBuffer(CommunicationIds.SLAVE3);

		if ((masterConsoleText != null) && !masterConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.MASTER.name()+" --> "+masterConsoleText);
			masterConsoleView.append(masterConsoleText);
			autoScrollIfNeccesary(masterConsoleScroll, masterConsoleView);
		}
		if ((slave1ConsoleText != null) && !slave1ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE1.name()+" --> "+slave1ConsoleText);
			slave1ConsoleView.append(slave1ConsoleText);
			autoScrollIfNeccesary(slave1ConsoleScroll, slave1ConsoleView);
		}
		if ((slave2ConsoleText != null) && !slave2ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE2.name()+" --> "+slave2ConsoleText);
			slave2ConsoleView.append(slave2ConsoleText);
			autoScrollIfNeccesary(slave2ConsoleScroll, slave2ConsoleView);
		}
		if ((slave3ConsoleText != null) && !slave3ConsoleText.equals("")){
			mainConsoleView.append(CommunicationIds.SLAVE3.name()+" --> "+slave3ConsoleText);
			slave3ConsoleView.append(slave3ConsoleText);
			autoScrollIfNeccesary(slave3ConsoleScroll, slave3ConsoleView);
		}

		autoScrollIfNeccesary(mainConsoleScroll, mainConsoleView);
	}

	private void autoScrollIfNeccesary(JScrollPane scroll, JTextArea area) {
		area.setCaretPosition(area.getDocument().getLength());
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

	private void updateStatusSlave2Sections(MasterModel model) {
		Slave2Model slave2Model = (Slave2Model) model.getModel().get(CommunicationIds.SLAVE2);
		if ((slave2Model != null) && model.isConnected(CommunicationIds.SLAVE2)) {
			weldingStatusPanel.setModo(slave2Model.getWeldingModel().getCurrentMode());
			transferStatusPanel.setModo(slave2Model.getTransferBeltModel().getCurrentMode());
		} else {
			weldingStatusPanel.setModo(null);
			transferStatusPanel.setModo(null);
		}
	}


	private void updateStatusSlave3Sections(MasterModel model) {
		Slave3Model slave3Model = (Slave3Model) model.getModel().get(CommunicationIds.SLAVE3);
		if ((slave3Model != null) && model.isConnected(CommunicationIds.SLAVE3)) {
			okStatusPanel.setModo (slave3Model.getOkBeltModel().getCurrentMode());
			notOkStatusPanel.setModo (slave3Model.getNotOkBeltModel().getCurrentMode());
			qualityStatusPanel.setModo (slave3Model.getQualityStationModel().getCurrentMode());
		}else{
			okStatusPanel.setModo (null);
			notOkStatusPanel.setModo (null);
			qualityStatusPanel.setModo (null);
		}

	}

	//TODO CUANDO EST� LISTO EL ESCLAVO 3, DESCOMENTAR
	private void updateBotonStart(){
		btnStart.setEnabled((master.getModel().isConnected(CommunicationIds.SLAVE1)) &&
				(master.getModel().isConnected(CommunicationIds.SLAVE2))
				/*&& !(master.getModel().isConnected(CommunicationIds.SLAVE3))*/
		);
	}

	private void setStatusPanelFor(CommunicationIds commID, MasterModel model) {
		statusPanels.get(commID).setModo(model.isConnected(commID) ? model.getModel().get(commID).getCurrentMode() : null);
	}
}

