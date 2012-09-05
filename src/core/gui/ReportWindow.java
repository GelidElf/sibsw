package core.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.messages.enums.ReportValues;
import core.reports.Report;

import javax.swing.SwingConstants;

public class ReportWindow extends JDialog {

	private static final long serialVersionUID = -184902000216862811L;

	private final JPanel contentPanel = new JPanel();
	private JTextField okPieces;
	private JTextField notOkPieces;
	private JTextField okPiecesTotal;
	private JTextField notOkPiecesTotal;
	private JTextField systemBoots;
	private JTextField systemStops;
	private JTextField systemEmergencies;
	
	/**
	 * Update report data in the report window
	 */
	public void updateData(Report report){
		okPieces.setText(report.getMap().get(ReportValues.OK_PIECES).toString());
		notOkPieces.setText(report.getMap().get(ReportValues.NOT_OK_PIECES).toString());
		okPiecesTotal.setText(report.getMapTotales().get(ReportValues.OK_PIECES_TOTAL).toString());
		notOkPiecesTotal.setText(report.getMapTotales().get(ReportValues.NOT_OK_PIECES_TOTAL).toString());
		systemBoots.setText(report.getMapTotales().get(ReportValues.SYSTEM_BOOTS).toString());
		systemStops.setText(report.getMapTotales().get(ReportValues.SYSTEM_STOPS).toString());
		systemEmergencies.setText(report.getMapTotales().get(ReportValues.SYSTEM_EMERGENCIES).toString());
	       
	    this.validate();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ReportWindow dialog = new ReportWindow(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ReportWindow(JFrame father) {
		super(father, true);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLocationRelativeTo(father);
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
			okPieces = new JTextField();
			okPieces.setHorizontalAlignment(SwingConstants.RIGHT);
			okPieces.setEditable(false);
			okPieces.setBounds(209, 62, 86, 20);
			contentPanel.add(okPieces);
			okPieces.setColumns(10);
		}
		{
			notOkPieces = new JTextField();
			notOkPieces.setHorizontalAlignment(SwingConstants.RIGHT);
			notOkPieces.setEditable(false);
			notOkPieces.setColumns(10);
			notOkPieces.setBounds(209, 103, 86, 20);
			contentPanel.add(notOkPieces);
		}
		{
			okPiecesTotal = new JTextField();
			okPiecesTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			okPiecesTotal.setEditable(false);
			okPiecesTotal.setColumns(10);
			okPiecesTotal.setBounds(209, 184, 86, 20);
			contentPanel.add(okPiecesTotal);
		}
		{
			notOkPiecesTotal = new JTextField();
			notOkPiecesTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			notOkPiecesTotal.setEditable(false);
			notOkPiecesTotal.setColumns(10);
			notOkPiecesTotal.setBounds(209, 225, 86, 20);
			contentPanel.add(notOkPiecesTotal);
		}
		{
			systemBoots = new JTextField();
			systemBoots.setHorizontalAlignment(SwingConstants.RIGHT);
			systemBoots.setEditable(false);
			systemBoots.setColumns(10);
			systemBoots.setBounds(209, 313, 86, 20);
			contentPanel.add(systemBoots);
		}
		{
			systemStops = new JTextField();
			systemStops.setHorizontalAlignment(SwingConstants.RIGHT);
			systemStops.setEditable(false);
			systemStops.setColumns(10);
			systemStops.setBounds(209, 354, 86, 20);
			contentPanel.add(systemStops);
		}
		{
			systemEmergencies = new JTextField();
			systemEmergencies.setHorizontalAlignment(SwingConstants.RIGHT);
			systemEmergencies.setEditable(false);
			systemEmergencies.setColumns(10);
			systemEmergencies.setBounds(209, 399, 86, 20);
			contentPanel.add(systemEmergencies);
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
