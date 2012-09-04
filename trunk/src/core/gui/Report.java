package core.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.messages.enums.ReportValues;

public class Report extends JDialog {

	private static final long serialVersionUID = -184902000216862811L;

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	private Map<ReportValues, Integer> map = new HashMap<ReportValues, Integer>();
	private Map<ReportValues, Integer> mapTotales = new HashMap<ReportValues, Integer>();

	/**
	 * Store report values
	 */
	public void receiveSignal(ReportValues signal) {
		map.put(signal, map.get(signal) + 1);
		mapTotales.put(signal, mapTotales.get(signal) + 1);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Report dialog = new Report(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Report(JFrame father) {
		super(father, true);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLocationRelativeTo(father);
		for (ReportValues parameter : ReportValues.values()) {
			map.put(parameter, 0);
			mapTotales.put(parameter, 0);
		}

		setBounds(100, 100, 382, 524);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblQualityReportlast = new JLabel("Quality Report (Last System)");
			lblQualityReportlast.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblQualityReportlast.setBounds(10, 11, 205, 49);
			contentPanel.add(lblQualityReportlast);
		}
		{
			JLabel lblQualityReporttotal = new JLabel("Quality Report (Total)");
			lblQualityReporttotal.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblQualityReporttotal.setBounds(10, 138, 156, 49);
			contentPanel.add(lblQualityReporttotal);
		}
		{
			JLabel lblEventsReporttotal = new JLabel("Events Report (Total)");
			lblEventsReporttotal.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblEventsReporttotal.setBounds(10, 266, 156, 49);
			contentPanel.add(lblEventsReporttotal);
		}
		{
			JLabel lblCorrectPieces = new JLabel("Correct Pieces");
			lblCorrectPieces.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblCorrectPieces.setBounds(38, 56, 92, 30);
			contentPanel.add(lblCorrectPieces);
		}
		{
			JLabel lblIncorrectPieces = new JLabel("Incorrect Pieces");
			lblIncorrectPieces.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblIncorrectPieces.setBounds(38, 97, 92, 30);
			contentPanel.add(lblIncorrectPieces);
		}
		{
			JLabel label = new JLabel("Correct Pieces");
			label.setFont(new Font("Tahoma", Font.PLAIN, 13));
			label.setBounds(38, 184, 92, 30);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Incorrect Pieces");
			label.setFont(new Font("Tahoma", Font.PLAIN, 13));
			label.setBounds(38, 225, 92, 30);
			contentPanel.add(label);
		}
		{
			JLabel lblSystemBoots = new JLabel("System Boots");
			lblSystemBoots.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblSystemBoots.setBounds(38, 313, 92, 30);
			contentPanel.add(lblSystemBoots);
		}
		{
			JLabel lblSystemStops = new JLabel("System Stops");
			lblSystemStops.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblSystemStops.setBounds(38, 354, 92, 30);
			contentPanel.add(lblSystemStops);
		}
		{
			JLabel lblSystemEmergencies = new JLabel("System Emergencies");
			lblSystemEmergencies.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblSystemEmergencies.setBounds(38, 399, 128, 30);
			contentPanel.add(lblSystemEmergencies);
		}
		{
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(209, 62, 86, 20);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setEditable(false);
			textField_1.setColumns(10);
			textField_1.setBounds(209, 103, 86, 20);
			contentPanel.add(textField_1);
		}
		{
			textField_2 = new JTextField();
			textField_2.setEditable(false);
			textField_2.setColumns(10);
			textField_2.setBounds(209, 184, 86, 20);
			contentPanel.add(textField_2);
		}
		{
			textField_3 = new JTextField();
			textField_3.setEditable(false);
			textField_3.setColumns(10);
			textField_3.setBounds(209, 225, 86, 20);
			contentPanel.add(textField_3);
		}
		{
			textField_4 = new JTextField();
			textField_4.setEditable(false);
			textField_4.setColumns(10);
			textField_4.setBounds(209, 313, 86, 20);
			contentPanel.add(textField_4);
		}
		{
			textField_5 = new JTextField();
			textField_5.setEditable(false);
			textField_5.setColumns(10);
			textField_5.setBounds(209, 354, 86, 20);
			contentPanel.add(textField_5);
		}
		{
			textField_6 = new JTextField();
			textField_6.setEditable(false);
			textField_6.setColumns(10);
			textField_6.setBounds(209, 399, 86, 20);
			contentPanel.add(textField_6);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
