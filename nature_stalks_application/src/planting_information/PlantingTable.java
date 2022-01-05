package planting_information;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

/**
 *
 * @author raj15
 */
public final class PlantingTable extends javax.swing.JPanel {

//    Variables for SQL and connection to database
    PreparedStatement pst;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String SQL;

    String tableName = "rds_db1.plant_info";
    Date fromDate, toDate;

    //    Auto made Table properties to make it beautiful
    UIDefaults defaults = UIManager.getLookAndFeelDefaults();

    public class ForcedListSelectionModel extends DefaultListSelectionModel {

        public ForcedListSelectionModel() {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }

    }

    public static class HeaderColor extends DefaultTableCellRenderer {

        public HeaderColor() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable jTable, Object value, boolean selected, boolean focused, int row, int column) {

            super.getTableCellRendererComponent(jTable, value, selected, focused, row, column);
            setBackground(new Color(0, 4, 5));
            setFont(new Font("Arial", Font.PLAIN, 2));

            return this;

        }

    }
//    End Table properties

    public PlantingTable(Connection connection) {
        initComponents();
        this.connection = connection;

        setTableStyle();
        updateTable();

        fromDateChoose.setEnabled(false);
        fromDateChoose.getCalendarButton().setEnabled(true);

        toDateChoose.setEnabled(false);
        toDateChoose.getCalendarButton().setEnabled(true);
    }

    private void setTableStyle() {
//        TablePlantingInformation.getTableHeader().setFont(new Font("Tahome", Font.BOLD, 10));

        plantTable.getTableHeader().setForeground(Color.WHITE);

        plantTable.getTableHeader().setDefaultRenderer(new PlantingPanel.HeaderColor());
        plantTable.getTableHeader().setReorderingAllowed(false);

        plantTable.setSelectionModel(new PlantingTable.ForcedListSelectionModel());
        plantTable.setRowSelectionInterval(0, 0);
//        TablePlantingInformation.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void updateTable() {

        try {

            SQL = "SELECT * FROM " + tableName;
            pst = connection.prepareStatement(SQL);
            resultSet = pst.executeQuery();
            plantTable.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException e) {
            System.out.println("at update table " + e);

        } finally {
            try {
                if (pst != null && resultSet != null) {
                    pst.close();
                    resultSet.close();
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }

        resizeColumnWidth(plantTable);
    }

    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    boolean findDate() {

        fromDate = convertJavaDateToSqlDate(fromDateChoose.getDate());
        toDate = convertJavaDateToSqlDate(toDateChoose.getDate());

        try {

            String query = "SELECT * from " + tableName + " WHERE date_of_planting BETWEEN '" + fromDate + "' AND '" + toDate + "'";
            System.out.println(query);
            pst = connection.prepareStatement(SQL);
            resultSet = pst.executeQuery(query);

            plantTable.setModel(DbUtils.resultSetToTableModel(resultSet));
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Please enter a vlid date.");
            return false;
        }
    }

    void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        basePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        toDateChoose = new com.toedter.calendar.JDateChooser();
        fromDateChoose = new com.toedter.calendar.JDateChooser();
        searchTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        plantTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(39, 45, 54));
        setLayout(new java.awt.BorderLayout(5, 5));

        basePanel.setBackground(new java.awt.Color(24, 32, 44));

        jPanel1.setBackground(new java.awt.Color(36, 56, 90));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Planting Information");
        jPanel1.add(jLabel1);

        fromDateChoose.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fromDateChooseFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fromDateChooseFocusLost(evt);
            }
        });
        fromDateChoose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fromDateChooseMouseClicked(evt);
            }
        });

        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("From");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("To");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_search_24px.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        plantTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(plantTable);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Search");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_search_24px.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(basePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addComponent(toDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toDateChoose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fromDateChoose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchTextField)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(basePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_searchTextFieldKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (findDate()) {
            try {

                String query = "SELECT * from " + tableName + " WHERE crop LIKE '%" + searchTextField.getText() + "%' "
                        + "AND date_of_planting BETWEEN '" + fromDate + "' AND '" + toDate + "'";

                pst = connection.prepareStatement(SQL);
                resultSet = pst.executeQuery(query);
                plantTable.setModel(DbUtils.resultSetToTableModel(resultSet));

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } else {
            try {

                String query = "SELECT * from " + tableName + " WHERE crop LIKE '%" + searchTextField.getText() + "%'";

                pst = connection.prepareStatement(SQL);
                resultSet = pst.executeQuery(query);
                plantTable.setModel(DbUtils.resultSetToTableModel(resultSet));

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldKeyReleased

    private void fromDateChooseFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fromDateChooseFocusGained
        // TODO add your handling code here:
        System.out.println("focus Gained");
    }//GEN-LAST:event_fromDateChooseFocusGained

    private void fromDateChooseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fromDateChooseFocusLost
        // TODO add your handling code here:
        System.out.println("focus lost");
    }//GEN-LAST:event_fromDateChooseFocusLost

    private void fromDateChooseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromDateChooseMouseClicked
        // TODO add your handling code here:
        if (fromDateChoose.getDate() != null) {
            fromDate = convertJavaDateToSqlDate(fromDateChoose.getDate());
            System.out.println(fromDate);
        }
    }//GEN-LAST:event_fromDateChooseMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (findDate()) {

        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private com.toedter.calendar.JDateChooser fromDateChoose;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable plantTable;
    private javax.swing.JTextField searchTextField;
    private com.toedter.calendar.JDateChooser toDateChoose;
    // End of variables declaration//GEN-END:variables
}
