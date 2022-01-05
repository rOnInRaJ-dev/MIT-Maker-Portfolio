package nature.stalks;

import planting_information.PlantingPanel;
import rain_information.RainPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public final class Main extends javax.swing.JFrame {

    GridBagLayout layout;

    HomePanel homePanel;
    PlantingPanel plantingPanel;
    RainPanel rainPanel;

    int cwidth;
    int cheight;

    public enum PANEL_NAMES {
        home,
        planting,
        rain;
    }

    PANEL_NAMES currentpanel;

    MyConnection myConnection;
    Connection connection;
//    Constructor

    public Main() {
        initComponents();
        layout = new GridBagLayout();
        pnlCentre.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        cwidth = pnlCentre.getWidth();
        cheight = pnlCentre.getHeight();

        this.setSize(953, 789);
        pack();

        /**
         * Set Location To center of the screen *
         */
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        buttonUI();
        
        myConnection = new MyConnection();
        connection = myConnection.connectDB();

        initiatePanels();
        pnlCentre.removeAll();
        pnlCentre.add(homePanel);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }

    void buttonUI() {
        JButton[] btnsUI = {plantingButton, rainButton, homeButton};
        for (JButton btn : btnsUI) {
            btn.setUI(new BasicButtonUI());
            btn.setBackground(new Color(30, 36, 43));
            btn.setBorder(null);
            btn.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    btn.setBackground(new Color(37, 55, 77));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    btn.setBackground(new Color(30, 36, 43));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(47, 70, 97));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(30, 36, 43));
                }
            });
        }

    }

    JPanel getPanel(PANEL_NAMES panel_names) {
        switch (panel_names) {
            case home:

        }

        switch (panel_names) {
            case home:
                return homePanel;
            case planting:
                return plantingPanel;
            case rain:
                return rainPanel;
            default:
                return null;
        }
    }

    void initiatePanels() {

        homePanel = new HomePanel();

        plantingPanel = new PlantingPanel(connection);
        plantingPanel.setSize(cwidth, cheight);

        rainPanel = new RainPanel(connection);
        rainPanel.setSize(cwidth, cheight);
        rainPanel.setCurrentPanel(RainPanel.PANEL_NAMES.Rain);

    }

    void setCurrentPanel(PANEL_NAMES currentPanel) {
        for (PANEL_NAMES panel_names : PANEL_NAMES.values()) {
            if (getPanel(panel_names) != null) {
                getPanel(panel_names).setVisible(false);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        pnlSide = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        rainButton = new javax.swing.JButton();
        plantingButton = new javax.swing.JButton();
        pnlCentre = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nature Stalks Africa");
        setMinimumSize(new java.awt.Dimension(1280, 768));
        setPreferredSize(new java.awt.Dimension(1280, 768));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 768));

        pnlRoot.setLayout(new java.awt.BorderLayout());

        pnlSide.setBackground(new java.awt.Color(30, 36, 43));
        pnlSide.setPreferredSize(new java.awt.Dimension(80, 789));
        pnlSide.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 20));

        jPanel1.setBackground(new java.awt.Color(30, 36, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(60, 130));

        homeButton.setBackground(new java.awt.Color(30, 36, 43));
        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/m_icon_home.png"))); // NOI18N
        homeButton.setPreferredSize(new java.awt.Dimension(50, 50));
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 40, Short.MAX_VALUE)
                    .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 40, Short.MAX_VALUE)))
        );

        pnlSide.add(jPanel1);

        rainButton.setBackground(new java.awt.Color(30, 36, 43));
        rainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/m_icon_rain.png"))); // NOI18N
        rainButton.setPreferredSize(new java.awt.Dimension(50, 50));
        rainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rainButtonActionPerformed(evt);
            }
        });
        pnlSide.add(rainButton);

        plantingButton.setBackground(new java.awt.Color(30, 36, 43));
        plantingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/m_icon_plant.png"))); // NOI18N
        plantingButton.setPreferredSize(new java.awt.Dimension(50, 50));
        plantingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plantingButtonActionPerformed(evt);
            }
        });
        pnlSide.add(plantingButton);

        pnlRoot.add(pnlSide, java.awt.BorderLayout.LINE_START);

        pnlCentre.setBackground(new java.awt.Color(43, 50, 60));

        javax.swing.GroupLayout pnlCentreLayout = new javax.swing.GroupLayout(pnlCentre);
        pnlCentre.setLayout(pnlCentreLayout);
        pnlCentreLayout.setHorizontalGroup(
            pnlCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 873, Short.MAX_VALUE)
        );
        pnlCentreLayout.setVerticalGroup(
            pnlCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        pnlRoot.add(pnlCentre, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlRoot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        pnlCentre.removeAll();
        pnlCentre.add(homePanel);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }//GEN-LAST:event_homeButtonActionPerformed

    private void rainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainButtonActionPerformed
//        setCurrentPanel(PANEL_NAMES.rain);
        pnlCentre.removeAll();
        pnlCentre.add(rainPanel);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }//GEN-LAST:event_rainButtonActionPerformed

    private void plantingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plantingButtonActionPerformed
//        setCurrentPanel(PANEL_NAMES.planting);
        pnlCentre.removeAll();
        pnlCentre.add(plantingPanel);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }//GEN-LAST:event_plantingButtonActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton homeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton plantingButton;
    private javax.swing.JPanel pnlCentre;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JButton rainButton;
    // End of variables declaration//GEN-END:variables
}
