package planting_information;

import static java.awt.Event.DELETE;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author raj15
 */
public class PlantSettings extends javax.swing.JPanel {

    Connection connection;

//    Variables for SQL and connection to database
    PreparedStatement pst;
    Statement statement;
    ResultSet resultSet;
    String SQL;

    String cropTableName = "rds_db1.plant_type";
    String chemTableName = "rds_db1.chemicals";

    String crop, variety, chemical;

    public PlantSettings(Connection connection) {
        initComponents();
        this.connection = connection;

        updateTables();

        // assume JTable is named "table"
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = cropTable.getInputMap(condition);
        ActionMap actionMap = cropTable.getActionMap();

        // DELETE is a String constant that for me was defined as "Delete"
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DELETE);
        actionMap.put(DELETE, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = cropTable.getSelectedRow();

                    String key = cropTable.getValueAt(row, 0).toString();
                    String query = " Delete FROM " + cropTableName + " WHERE key =" + key;

                    pst = connection.prepareStatement(query);
                    pst.execute();
                    System.out.println("entry deleted");

                } catch (SQLException exception) {
                    System.out.print("delete| " + exception);
                }
            }
        });
    }

    public void getCropData() {
        crop = cropTextField.getText();
        variety = varietyTextField.getText();
    }

    public boolean addCropType() {
        getCropData();
        boolean updated = false;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            SQL = "SELECT * FROM " + cropTableName;
            resultSet = statement.executeQuery(SQL);

            /**
             * Moves the cursor in the database to insert a new row for all the
             * data
             */
            resultSet.moveToInsertRow();
            resultSet.updateString("crop", crop);
            resultSet.updateString("variety", variety);
            resultSet.insertRow();

            updated = true;
        } catch (SQLException error) {
            System.out.println("croptype " + error);

        } finally {
            try {
                resultSet.close();
            } catch (SQLException error) {
                System.out.println(error);
            }
        }
        return updated;
    }

    public boolean addChemical() {
        chemical = chemTextField.getText();
        boolean updated = false;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            SQL = "SELECT * FROM " + chemTableName;
            resultSet = statement.executeQuery(SQL);

            /**
             * Moves the cursor in the database to insert a new row for all the
             * data
             */
            resultSet.moveToInsertRow();
            resultSet.updateString("chemical", chemical);
            resultSet.insertRow();

            updated = true;
        } catch (SQLException error) {
            System.out.println("addchem " + error);

        } finally {
            try {
                resultSet.close();
            } catch (SQLException error) {
                System.out.println(error);
            }
        }
        return updated;
    }

    public void updateTables() {

        try {

            SQL = "SELECT * FROM " + cropTableName;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL);
            cropTable.setModel(DbUtils.resultSetToTableModel(resultSet));

            SQL = "SELECT * FROM " + chemTableName;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL);
            chemTable.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException e) {
            System.out.println("at update table " + e);

        } finally {

            cropTextField.setText(null);
            varietyTextField.setText(null);
            chemTextField.setText(null);

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
    }

    boolean deleteCrop() {
        boolean deleted = false;
        try {
            int row = cropTable.getSelectedRow();

            String id = cropTable.getValueAt(row, 0).toString();
            String query = " Delete FROM " + cropTableName + " WHERE id = " + id;

            pst = connection.prepareStatement(query);
            pst.execute();
            deleted = true;
        } catch (SQLException e) {
            System.out.println("delete | " + e);
            deleted = false;

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
        return deleted;
    }

    boolean deleteChemical() {
        boolean deleted = false;
        try {
            int row = chemTable.getSelectedRow();

            String id = chemTable.getValueAt(row, 0).toString();
            String query = " Delete FROM " + chemTableName + " WHERE id = " + id;

            pst = connection.prepareStatement(query);
            pst.execute();
            deleted = true;
        } catch (SQLException e) {
            System.out.print("delete| " + e);
        } finally {
            try {
                if (pst != null && resultSet != null) {
                    pst.close();
                    resultSet.close();
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(this, error);
                deleted = false;
            }
        }
        return deleted;
    }

    boolean cropNotEmpty() {
        return cropTextField.getText() == null;
    }

    boolean chemNotEmpty() {
        return chemTextField.getText() == null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cropTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        chemTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cropTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        varietyTextField = new javax.swing.JTextField();
        updateCropBT = new javax.swing.JButton();
        chemTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        updateChemBT = new javax.swing.JButton();
        deleteCropBT = new javax.swing.JButton();
        deleteChemBT = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        basePanel.setBackground(new java.awt.Color(33, 38, 46));

        cropTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(cropTable);

        chemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(chemTable);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Crop");

        cropTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cropTextField.setForeground(new java.awt.Color(0, 0, 0));
        cropTextField.setToolTipText("");
        cropTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));
        cropTextField.setName(""); // NOI18N
        cropTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Variety");

        varietyTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        varietyTextField.setForeground(new java.awt.Color(0, 0, 0));
        varietyTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));
        varietyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varietyTextFieldActionPerformed(evt);
            }
        });

        updateCropBT.setBackground(new java.awt.Color(39, 45, 54));
        updateCropBT.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        updateCropBT.setForeground(new java.awt.Color(255, 255, 255));
        updateCropBT.setText("Update");
        updateCropBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCropBTActionPerformed(evt);
            }
        });

        chemTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        chemTextField.setForeground(new java.awt.Color(0, 0, 0));
        chemTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));
        chemTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemTextFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Chemicals");

        updateChemBT.setBackground(new java.awt.Color(39, 45, 54));
        updateChemBT.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        updateChemBT.setForeground(new java.awt.Color(255, 255, 255));
        updateChemBT.setText("Update");
        updateChemBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateChemBTActionPerformed(evt);
            }
        });

        deleteCropBT.setBackground(new java.awt.Color(39, 45, 54));
        deleteCropBT.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        deleteCropBT.setForeground(new java.awt.Color(255, 255, 255));
        deleteCropBT.setText("Delete");
        deleteCropBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCropBTActionPerformed(evt);
            }
        });

        deleteChemBT.setBackground(new java.awt.Color(39, 45, 54));
        deleteChemBT.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        deleteChemBT.setForeground(new java.awt.Color(255, 255, 255));
        deleteChemBT.setText("Delete");
        deleteChemBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteChemBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(basePanelLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(183, 183, 183))
                                .addGroup(basePanelLayout.createSequentialGroup()
                                    .addComponent(varietyTextField)
                                    .addGap(33, 33, 33)))
                            .addGroup(basePanelLayout.createSequentialGroup()
                                .addComponent(cropTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)))
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updateCropBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteCropBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(chemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updateChemBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteChemBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basePanelLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cropTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateCropBT))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(varietyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteCropBT)))
                    .addGroup(basePanelLayout.createSequentialGroup()
                        .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(basePanelLayout.createSequentialGroup()
                                .addComponent(updateChemBT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteChemBT))
                            .addGroup(basePanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        add(basePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cropTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cropTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cropTextFieldActionPerformed

    private void varietyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varietyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_varietyTextFieldActionPerformed

    private void updateCropBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCropBTActionPerformed
        if (addCropType()) {
            System.out.println(cropNotEmpty());
            updateTables();
        }

    }//GEN-LAST:event_updateCropBTActionPerformed

    private void chemTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chemTextFieldActionPerformed

    private void updateChemBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateChemBTActionPerformed
        if (addChemical()) {
            System.out.println(chemNotEmpty());
            updateTables();
        }
    }//GEN-LAST:event_updateChemBTActionPerformed

    private void deleteCropBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCropBTActionPerformed
        if (!cropTable.getSelectionModel().isSelectionEmpty()) {
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the entry",
                    "Delete Entry",
                    JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                if (deleteCrop()) {
                    updateTables();
                }
            }
        }
    }//GEN-LAST:event_deleteCropBTActionPerformed

    private void deleteChemBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteChemBTActionPerformed
        if (!chemTable.getSelectionModel().isSelectionEmpty()) {
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the entry",
                    "Delete Entry",
                    JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                if (deleteChemical()) {
                    updateTables();
                }
            }
        }
    }//GEN-LAST:event_deleteChemBTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JTable chemTable;
    private javax.swing.JTextField chemTextField;
    private javax.swing.JTable cropTable;
    private javax.swing.JTextField cropTextField;
    private javax.swing.JButton deleteChemBT;
    private javax.swing.JButton deleteCropBT;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton updateChemBT;
    private javax.swing.JButton updateCropBT;
    private javax.swing.JTextField varietyTextField;
    // End of variables declaration//GEN-END:variables
}
