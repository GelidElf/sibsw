/*
 * ExecutableView.java
 */

package gui;

import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;


/**
 * The application's main frame.
 */
public class ExecutableView extends FrameView {

    public ExecutableView(SingleFrameApplication app) {
        super(app);

        initComponents();

    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        assemblyPanel = new javax.swing.JPanel();
        assemblyActuatorLed = new javax.swing.JLabel();
        assemblyLabel = new javax.swing.JLabel();
        assemblyGearLed = new javax.swing.JLabel();
        assemblyAxisLed = new javax.swing.JLabel();
        gearBeltPanel = new javax.swing.JPanel();
        gearBeltIcon = new javax.swing.JLabel();
        gearBeltLabel = new javax.swing.JLabel();
        gearBeltFinalLed = new javax.swing.JLabel();
        gearBeltInitialLed = new javax.swing.JLabel();
        gearBeltCount = new javax.swing.JLabel();
        axisBeltPanel = new javax.swing.JPanel();
        axisBeltIcon = new javax.swing.JLabel();
        axisBeltLabel = new javax.swing.JLabel();
        axisBeltFinalLed = new javax.swing.JLabel();
        axisBeltInitalLed = new javax.swing.JLabel();
        axisBeltCount = new javax.swing.JLabel();
        robot1Panel = new javax.swing.JPanel();
        robot1Icon = new javax.swing.JLabel();
        robot1Label = new javax.swing.JLabel();
        weldingBeltPanel = new javax.swing.JPanel();
        weldingBeltIcon = new javax.swing.JLabel();
        weldingBeltLabel = new javax.swing.JLabel();
        weldingBeltCount = new javax.swing.JLabel();
        weldingBeltInitialLed = new javax.swing.JLabel();
        weldingBeltFinalLed = new javax.swing.JLabel();
        assemblyPanel1 = new javax.swing.JPanel();
        weldingLabel = new javax.swing.JLabel();
        weldingPieceLed = new javax.swing.JLabel();
        weldingActuatorLed = new javax.swing.JLabel();
        robot2Panel = new javax.swing.JPanel();
        robot2Icon = new javax.swing.JLabel();
        robot2Label = new javax.swing.JLabel();
        qualityPanel = new javax.swing.JPanel();
        qualityLabel = new javax.swing.JLabel();
        qualitySensorLed = new javax.swing.JLabel();
        qualityReworkingLed = new javax.swing.JLabel();
        qualityOkLed = new javax.swing.JLabel();
        okBeltPanel = new javax.swing.JPanel();
        weldingBeltIcon1 = new javax.swing.JLabel();
        okBeltLabel = new javax.swing.JLabel();
        okBeltCount = new javax.swing.JLabel();
        okBeltInitialLed = new javax.swing.JLabel();
        okBeltFinalLed = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1024, 768));

        assemblyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assemblyPanel.setName("assemblyPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.Executable.class).getContext().getResourceMap(ExecutableView.class);
        assemblyActuatorLed.setIcon(resourceMap.getIcon("assemblyActuatorLed.icon")); // NOI18N
        assemblyActuatorLed.setText(resourceMap.getString("assemblyActuatorLed.text")); // NOI18N
        assemblyActuatorLed.setName("assemblyActuatorLed"); // NOI18N

        assemblyLabel.setFont(resourceMap.getFont("assemblyLabel.font")); // NOI18N
        assemblyLabel.setText(resourceMap.getString("assemblyLabel.text")); // NOI18N
        assemblyLabel.setName("assemblyLabel"); // NOI18N

        assemblyGearLed.setIcon(resourceMap.getIcon("assemblyGearLed.icon")); // NOI18N
        assemblyGearLed.setText(resourceMap.getString("assemblyGearLed.text")); // NOI18N
        assemblyGearLed.setName("assemblyGearLed"); // NOI18N

        assemblyAxisLed.setIcon(resourceMap.getIcon("assemblyAxisLed.icon")); // NOI18N
        assemblyAxisLed.setText(resourceMap.getString("assemblyAxisLed.text")); // NOI18N
        assemblyAxisLed.setName("assemblyAxisLed"); // NOI18N

        org.jdesktop.layout.GroupLayout assemblyPanelLayout = new org.jdesktop.layout.GroupLayout(assemblyPanel);
        assemblyPanel.setLayout(assemblyPanelLayout);
        assemblyPanelLayout.setHorizontalGroup(
            assemblyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, assemblyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(assemblyLabel)
                .addContainerGap())
            .add(assemblyPanelLayout.createSequentialGroup()
                .add(38, 38, 38)
                .add(assemblyGearLed)
                .addContainerGap(55, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, assemblyPanelLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .add(assemblyAxisLed)
                .add(55, 55, 55))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, assemblyPanelLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .add(assemblyActuatorLed)
                .addContainerGap())
        );
        assemblyPanelLayout.setVerticalGroup(
            assemblyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(assemblyPanelLayout.createSequentialGroup()
                .add(12, 12, 12)
                .add(assemblyLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(assemblyGearLed)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(assemblyAxisLed)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(assemblyActuatorLed)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        gearBeltPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gearBeltPanel.setName("gearBeltPanel"); // NOI18N

        gearBeltIcon.setIcon(resourceMap.getIcon("gearBeltIcon.icon")); // NOI18N
        gearBeltIcon.setText(resourceMap.getString("gearBeltIcon.text")); // NOI18N
        gearBeltIcon.setName("gearBeltIcon"); // NOI18N

        gearBeltLabel.setFont(resourceMap.getFont("gearBeltLabel.font")); // NOI18N
        gearBeltLabel.setText(resourceMap.getString("gearBeltLabel.text")); // NOI18N
        gearBeltLabel.setName("gearBeltLabel"); // NOI18N

        gearBeltFinalLed.setIcon(resourceMap.getIcon("gearBeltFinalLed.icon")); // NOI18N
        gearBeltFinalLed.setText(resourceMap.getString("gearBeltFinalLed.text")); // NOI18N
        gearBeltFinalLed.setName("gearBeltFinalLed"); // NOI18N

        gearBeltInitialLed.setIcon(resourceMap.getIcon("gearBeltInitialLed.icon")); // NOI18N
        gearBeltInitialLed.setText(resourceMap.getString("gearBeltInitialLed.text")); // NOI18N
        gearBeltInitialLed.setName("gearBeltInitialLed"); // NOI18N

        gearBeltCount.setText(resourceMap.getString("gearBeltCount.text")); // NOI18N
        gearBeltCount.setName("gearBeltCount"); // NOI18N

        org.jdesktop.layout.GroupLayout gearBeltPanelLayout = new org.jdesktop.layout.GroupLayout(gearBeltPanel);
        gearBeltPanel.setLayout(gearBeltPanelLayout);
        gearBeltPanelLayout.setHorizontalGroup(
            gearBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(gearBeltPanelLayout.createSequentialGroup()
                .add(gearBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gearBeltPanelLayout.createSequentialGroup()
                        .add(99, 99, 99)
                        .add(gearBeltLabel))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, gearBeltPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(gearBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(gearBeltCount)
                            .add(gearBeltIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 282, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(gearBeltPanelLayout.createSequentialGroup()
                                .add(gearBeltFinalLed)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 84, Short.MAX_VALUE)
                                .add(gearBeltInitialLed)))))
                .addContainerGap())
        );
        gearBeltPanelLayout.setVerticalGroup(
            gearBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(gearBeltPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(gearBeltLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gearBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(gearBeltFinalLed)
                    .add(gearBeltInitialLed))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gearBeltIcon)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gearBeltCount)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        axisBeltPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        axisBeltPanel.setName("axisBeltPanel"); // NOI18N

        axisBeltIcon.setIcon(resourceMap.getIcon("axisBeltIcon.icon")); // NOI18N
        axisBeltIcon.setName("axisBeltIcon"); // NOI18N

        axisBeltLabel.setFont(resourceMap.getFont("axisBeltLabel.font")); // NOI18N
        axisBeltLabel.setText(resourceMap.getString("axisBeltLabel.text")); // NOI18N
        axisBeltLabel.setName("axisBeltLabel"); // NOI18N

        axisBeltFinalLed.setIcon(resourceMap.getIcon("axisBeltFinalLed.icon")); // NOI18N
        axisBeltFinalLed.setText(resourceMap.getString("axisBeltFinalLed.text")); // NOI18N
        axisBeltFinalLed.setName("axisBeltFinalLed"); // NOI18N

        axisBeltInitalLed.setIcon(resourceMap.getIcon("axisBeltInitalLed.icon")); // NOI18N
        axisBeltInitalLed.setText(resourceMap.getString("axisBeltInitalLed.text")); // NOI18N
        axisBeltInitalLed.setName("axisBeltInitalLed"); // NOI18N

        axisBeltCount.setText(resourceMap.getString("axisBeltCount.text")); // NOI18N
        axisBeltCount.setName("axisBeltCount"); // NOI18N

        org.jdesktop.layout.GroupLayout axisBeltPanelLayout = new org.jdesktop.layout.GroupLayout(axisBeltPanel);
        axisBeltPanel.setLayout(axisBeltPanelLayout);
        axisBeltPanelLayout.setHorizontalGroup(
            axisBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(axisBeltPanelLayout.createSequentialGroup()
                .add(axisBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(axisBeltPanelLayout.createSequentialGroup()
                        .add(99, 99, 99)
                        .add(axisBeltLabel))
                    .add(axisBeltPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(axisBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(axisBeltPanelLayout.createSequentialGroup()
                                .add(axisBeltFinalLed)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(axisBeltInitalLed))
                            .add(axisBeltIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 282, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, axisBeltPanelLayout.createSequentialGroup()
                .addContainerGap(216, Short.MAX_VALUE)
                .add(axisBeltCount)
                .addContainerGap())
        );
        axisBeltPanelLayout.setVerticalGroup(
            axisBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(axisBeltPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(axisBeltLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(axisBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(axisBeltInitalLed)
                    .add(axisBeltFinalLed))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(axisBeltIcon)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(axisBeltCount)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        robot1Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        robot1Panel.setName("robot1Panel"); // NOI18N

        robot1Icon.setIcon(resourceMap.getIcon("robot1Icon.icon")); // NOI18N
        robot1Icon.setText(resourceMap.getString("robot1Icon.text")); // NOI18N
        robot1Icon.setName("robot1Icon"); // NOI18N

        robot1Label.setFont(resourceMap.getFont("robot1Label.font")); // NOI18N
        robot1Label.setText(resourceMap.getString("robot1Label.text")); // NOI18N
        robot1Label.setName("robot1Label"); // NOI18N

        org.jdesktop.layout.GroupLayout robot1PanelLayout = new org.jdesktop.layout.GroupLayout(robot1Panel);
        robot1Panel.setLayout(robot1PanelLayout);
        robot1PanelLayout.setHorizontalGroup(
            robot1PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(robot1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(robot1PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(robot1Icon)
                    .add(robot1PanelLayout.createSequentialGroup()
                        .add(62, 62, 62)
                        .add(robot1Label)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        robot1PanelLayout.setVerticalGroup(
            robot1PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(robot1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(robot1PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(robot1Label)
                    .add(robot1PanelLayout.createSequentialGroup()
                        .add(58, 58, 58)
                        .add(robot1Icon)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        weldingBeltPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        weldingBeltPanel.setName("weldingBeltPanel"); // NOI18N

        weldingBeltIcon.setIcon(resourceMap.getIcon("weldingBeltIcon.icon")); // NOI18N
        weldingBeltIcon.setText(resourceMap.getString("weldingBeltIcon.text")); // NOI18N
        weldingBeltIcon.setName("weldingBeltIcon"); // NOI18N

        weldingBeltLabel.setFont(resourceMap.getFont("weldingBeltLabel.font")); // NOI18N
        weldingBeltLabel.setText(resourceMap.getString("weldingBeltLabel.text")); // NOI18N
        weldingBeltLabel.setName("weldingBeltLabel"); // NOI18N

        weldingBeltCount.setText(resourceMap.getString("weldingBeltCount.text")); // NOI18N
        weldingBeltCount.setName("weldingBeltCount"); // NOI18N

        weldingBeltInitialLed.setIcon(resourceMap.getIcon("weldingBeltInitialLed.icon")); // NOI18N
        weldingBeltInitialLed.setText(resourceMap.getString("weldingBeltInitialLed.text")); // NOI18N
        weldingBeltInitialLed.setName("weldingBeltInitialLed"); // NOI18N

        weldingBeltFinalLed.setIcon(resourceMap.getIcon("weldingBeltFinalLed.icon")); // NOI18N
        weldingBeltFinalLed.setText(resourceMap.getString("weldingBeltFinalLed.text")); // NOI18N
        weldingBeltFinalLed.setName("weldingBeltFinalLed"); // NOI18N

        org.jdesktop.layout.GroupLayout weldingBeltPanelLayout = new org.jdesktop.layout.GroupLayout(weldingBeltPanel);
        weldingBeltPanel.setLayout(weldingBeltPanelLayout);
        weldingBeltPanelLayout.setHorizontalGroup(
            weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 332, Short.MAX_VALUE)
            .add(weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(weldingBeltPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(weldingBeltIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(weldingBeltCount)
                            .add(weldingBeltPanelLayout.createSequentialGroup()
                                .add(weldingBeltFinalLed)
                                .add(94, 94, 94)
                                .add(weldingBeltInitialLed)))
                        .add(weldingBeltPanelLayout.createSequentialGroup()
                            .add(62, 62, 62)
                            .add(weldingBeltLabel)))
                    .addContainerGap(28, Short.MAX_VALUE)))
        );
        weldingBeltPanelLayout.setVerticalGroup(
            weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 152, Short.MAX_VALUE)
            .add(weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(weldingBeltPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(weldingBeltLabel)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(weldingBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(weldingBeltFinalLed)
                        .add(weldingBeltInitialLed))
                    .add(14, 14, 14)
                    .add(weldingBeltIcon)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(weldingBeltCount)
                    .addContainerGap(37, Short.MAX_VALUE)))
        );

        assemblyPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assemblyPanel1.setName("assemblyPanel1"); // NOI18N

        weldingLabel.setFont(resourceMap.getFont("weldingLabel.font")); // NOI18N
        weldingLabel.setText(resourceMap.getString("weldingLabel.text")); // NOI18N
        weldingLabel.setName("weldingLabel"); // NOI18N

        weldingPieceLed.setIcon(resourceMap.getIcon("weldingPieceLed.icon")); // NOI18N
        weldingPieceLed.setText(resourceMap.getString("weldingPieceLed.text")); // NOI18N
        weldingPieceLed.setName("weldingPieceLed"); // NOI18N

        weldingActuatorLed.setIcon(resourceMap.getIcon("weldingActuatorLed.icon")); // NOI18N
        weldingActuatorLed.setText(resourceMap.getString("weldingActuatorLed.text")); // NOI18N
        weldingActuatorLed.setName("weldingActuatorLed"); // NOI18N

        org.jdesktop.layout.GroupLayout assemblyPanel1Layout = new org.jdesktop.layout.GroupLayout(assemblyPanel1);
        assemblyPanel1.setLayout(assemblyPanel1Layout);
        assemblyPanel1Layout.setHorizontalGroup(
            assemblyPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(assemblyPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(assemblyPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(weldingActuatorLed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(weldingPieceLed)
                    .add(weldingLabel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        assemblyPanel1Layout.setVerticalGroup(
            assemblyPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(assemblyPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(weldingLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(weldingPieceLed)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(weldingActuatorLed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addContainerGap())
        );

        robot2Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        robot2Panel.setName("robot2Panel"); // NOI18N

        robot2Icon.setIcon(resourceMap.getIcon("robot2Icon.icon")); // NOI18N
        robot2Icon.setName("robot2Icon"); // NOI18N

        robot2Label.setFont(resourceMap.getFont("robot2Label.font")); // NOI18N
        robot2Label.setText(resourceMap.getString("robot2Label.text")); // NOI18N
        robot2Label.setName("robot2Label"); // NOI18N

        org.jdesktop.layout.GroupLayout robot2PanelLayout = new org.jdesktop.layout.GroupLayout(robot2Panel);
        robot2Panel.setLayout(robot2PanelLayout);
        robot2PanelLayout.setHorizontalGroup(
            robot2PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(robot2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(robot2PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(robot2Icon)
                    .add(robot2PanelLayout.createSequentialGroup()
                        .add(62, 62, 62)
                        .add(robot2Label)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        robot2PanelLayout.setVerticalGroup(
            robot2PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(robot2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(robot2PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(robot2Label)
                    .add(robot2PanelLayout.createSequentialGroup()
                        .add(58, 58, 58)
                        .add(robot2Icon)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        qualityPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        qualityPanel.setName("qualityPanel"); // NOI18N

        qualityLabel.setFont(resourceMap.getFont("qualityLabel.font")); // NOI18N
        qualityLabel.setText(resourceMap.getString("qualityLabel.text")); // NOI18N
        qualityLabel.setName("qualityLabel"); // NOI18N

        qualitySensorLed.setIcon(resourceMap.getIcon("qualitySensorLed.icon")); // NOI18N
        qualitySensorLed.setText(resourceMap.getString("qualitySensorLed.text")); // NOI18N
        qualitySensorLed.setName("qualitySensorLed"); // NOI18N

        qualityReworkingLed.setIcon(resourceMap.getIcon("qualityReworkingLed.icon")); // NOI18N
        qualityReworkingLed.setText(resourceMap.getString("qualityReworkingLed.text")); // NOI18N
        qualityReworkingLed.setName("qualityReworkingLed"); // NOI18N

        qualityOkLed.setIcon(resourceMap.getIcon("qualityOkLed.icon")); // NOI18N
        qualityOkLed.setText(resourceMap.getString("qualityOkLed.text")); // NOI18N
        qualityOkLed.setName("qualityOkLed"); // NOI18N

        org.jdesktop.layout.GroupLayout qualityPanelLayout = new org.jdesktop.layout.GroupLayout(qualityPanel);
        qualityPanel.setLayout(qualityPanelLayout);
        qualityPanelLayout.setHorizontalGroup(
            qualityPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(qualityPanelLayout.createSequentialGroup()
                .add(64, 64, 64)
                .add(qualityPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(qualityPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(qualityPanelLayout.createSequentialGroup()
                            .add(qualityReworkingLed)
                            .add(18, 18, 18)
                            .add(qualityOkLed))
                        .add(qualityPanelLayout.createSequentialGroup()
                            .add(60, 60, 60)
                            .add(qualitySensorLed)))
                    .add(qualityLabel))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        qualityPanelLayout.setVerticalGroup(
            qualityPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(qualityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(qualityLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(qualitySensorLed)
                .add(14, 14, 14)
                .add(qualityPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(qualityReworkingLed)
                    .add(qualityOkLed))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        okBeltPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        okBeltPanel.setName("okBeltPanel"); // NOI18N

        weldingBeltIcon1.setIcon(resourceMap.getIcon("weldingBeltIcon1.icon")); // NOI18N
        weldingBeltIcon1.setName("weldingBeltIcon1"); // NOI18N

        okBeltLabel.setFont(resourceMap.getFont("okBeltLabel.font")); // NOI18N
        okBeltLabel.setText(resourceMap.getString("okBeltLabel.text")); // NOI18N
        okBeltLabel.setName("okBeltLabel"); // NOI18N

        okBeltCount.setText(resourceMap.getString("okBeltCount.text")); // NOI18N
        okBeltCount.setName("okBeltCount"); // NOI18N

        okBeltInitialLed.setIcon(resourceMap.getIcon("okBeltInitialLed.icon")); // NOI18N
        okBeltInitialLed.setText(resourceMap.getString("okBeltInitialLed.text")); // NOI18N
        okBeltInitialLed.setName("okBeltInitialLed"); // NOI18N

        okBeltFinalLed.setIcon(resourceMap.getIcon("okBeltFinalLed.icon")); // NOI18N
        okBeltFinalLed.setText(resourceMap.getString("okBeltFinalLed.text")); // NOI18N
        okBeltFinalLed.setName("okBeltFinalLed"); // NOI18N

        org.jdesktop.layout.GroupLayout okBeltPanelLayout = new org.jdesktop.layout.GroupLayout(okBeltPanel);
        okBeltPanel.setLayout(okBeltPanelLayout);
        okBeltPanelLayout.setHorizontalGroup(
            okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 332, Short.MAX_VALUE)
            .add(okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(okBeltPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(weldingBeltIcon1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(okBeltCount)
                            .add(okBeltPanelLayout.createSequentialGroup()
                                .add(okBeltFinalLed)
                                .add(94, 94, 94)
                                .add(okBeltInitialLed)))
                        .add(okBeltPanelLayout.createSequentialGroup()
                            .add(62, 62, 62)
                            .add(okBeltLabel)))
                    .addContainerGap(28, Short.MAX_VALUE)))
        );
        okBeltPanelLayout.setVerticalGroup(
            okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 152, Short.MAX_VALUE)
            .add(okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(okBeltPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(okBeltLabel)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(okBeltPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(okBeltFinalLed)
                        .add(okBeltInitialLed))
                    .add(14, 14, 14)
                    .add(weldingBeltIcon1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(okBeltCount)
                    .addContainerGap(42, Short.MAX_VALUE)))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(55, 55, 55)
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(weldingBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(assemblyPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(78, 78, 78)))
                        .add(70, 70, 70)
                        .add(robot1Panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(mainPanelLayout.createSequentialGroup()
                                .add(94, 94, 94)
                                .add(robot2Panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(mainPanelLayout.createSequentialGroup()
                                .add(120, 120, 120)
                                .add(assemblyPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 43, Short.MAX_VALUE)
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(qualityPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(okBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(56, 56, 56)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(gearBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(axisBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(136, 136, 136)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(11, 11, 11)
                        .add(robot1Panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                        .add(assemblyPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(weldingBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                        .add(gearBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(axisBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(robot2Panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(assemblyPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(qualityPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(34, 34, 34)
                        .add(okBeltPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(67, 67, 67))
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assemblyActuatorLed;
    private javax.swing.JLabel assemblyAxisLed;
    private javax.swing.JLabel assemblyGearLed;
    private javax.swing.JLabel assemblyLabel;
    private javax.swing.JPanel assemblyPanel;
    private javax.swing.JPanel assemblyPanel1;
    private javax.swing.JLabel axisBeltCount;
    private javax.swing.JLabel axisBeltFinalLed;
    private javax.swing.JLabel axisBeltIcon;
    private javax.swing.JLabel axisBeltInitalLed;
    private javax.swing.JLabel axisBeltLabel;
    private javax.swing.JPanel axisBeltPanel;
    private javax.swing.JLabel gearBeltCount;
    private javax.swing.JLabel gearBeltFinalLed;
    private javax.swing.JLabel gearBeltIcon;
    private javax.swing.JLabel gearBeltInitialLed;
    private javax.swing.JLabel gearBeltLabel;
    private javax.swing.JPanel gearBeltPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel okBeltCount;
    private javax.swing.JLabel okBeltFinalLed;
    private javax.swing.JLabel okBeltInitialLed;
    private javax.swing.JLabel okBeltLabel;
    private javax.swing.JPanel okBeltPanel;
    private javax.swing.JLabel qualityLabel;
    private javax.swing.JLabel qualityOkLed;
    private javax.swing.JPanel qualityPanel;
    private javax.swing.JLabel qualityReworkingLed;
    private javax.swing.JLabel qualitySensorLed;
    private javax.swing.JLabel robot1Icon;
    private javax.swing.JLabel robot1Label;
    private javax.swing.JPanel robot1Panel;
    private javax.swing.JLabel robot2Icon;
    private javax.swing.JLabel robot2Label;
    private javax.swing.JPanel robot2Panel;
    private javax.swing.JLabel weldingActuatorLed;
    private javax.swing.JLabel weldingBeltCount;
    private javax.swing.JLabel weldingBeltFinalLed;
    private javax.swing.JLabel weldingBeltIcon;
    private javax.swing.JLabel weldingBeltIcon1;
    private javax.swing.JLabel weldingBeltInitialLed;
    private javax.swing.JLabel weldingBeltLabel;
    private javax.swing.JPanel weldingBeltPanel;
    private javax.swing.JLabel weldingLabel;
    private javax.swing.JLabel weldingPieceLed;
    // End of variables declaration//GEN-END:variables
  
    
    public void activateLed(javax.swing.JLabel label){
        label.setIcon(new javax.swing.ImageIcon("/gui/resources/green.jpg"));
    }
    public void deactivateLed(javax.swing.JLabel label){
        label.setIcon(new javax.swing.ImageIcon("/gui/resources/red.jpg"));
    }
    
    // Position can be "1" or "2"
    public void changeBeltIcon(javax.swing.JLabel label, String position){
        label.setIcon(new javax.swing.ImageIcon("/gui/resources/belt+"+position+".jpg"));
    }
    
    //Position can be "left" or "right"
    public void changeRobotIcon(javax.swing.JLabel label, String robotNumber, String position){
        label.setIcon(new javax.swing.ImageIcon("/gui/resources/robot_"+position+robotNumber+".jpg"));
    }
    
}
