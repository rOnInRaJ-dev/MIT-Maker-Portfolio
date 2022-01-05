package nature.stalks;

import java.awt.Dimension;

public class HomePanel extends javax.swing.JPanel {


    MyConnection myconnection = new MyConnection();

    public HomePanel() {
        initComponents();
    }

    public void setSize(Dimension dimension) {
        basePanel.setPreferredSize(dimension);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(39, 45, 54));
        setLayout(new java.awt.BorderLayout());

        basePanel.setBackground(new java.awt.Color(24, 32, 44));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/h_background.png"))); // NOI18N

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, Short.MAX_VALUE)
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, Short.MAX_VALUE)
        );

        add(basePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
