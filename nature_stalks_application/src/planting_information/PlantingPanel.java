package planting_information;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import nature.stalks.Exporter;

public class PlantingPanel extends javax.swing.JPanel {

    String fileName = "PlantingInformation.xls";
    File fileOutput = new File("D:\\Github\\" + fileName);

//    Variables for Panel Layout
    GridBagLayout layout = new GridBagLayout();
    AddPlantInformation addPlantInformation;
    PlantingTable plantingTable;
    PlantSettings plantSettings;

//  Variables for SQL and connection to database
    PreparedStatement pst;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String SQL;

    String tableName = "rds_db1.plant_info";

//    Auto made Table properties to make it beautiful
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
            setBackground(new Color(0, 130, 153, 100));
            setFont(new Font("Gidole", Font.PLAIN, 18));

            return this;

        }

    }
//    End Table properties

    public enum PANEL_NAMES {
        PlantingTable,
        Add,
        edit,
        Settings
    }

//    Constructor
    public PlantingPanel(Connection connection) {
        initComponents();
        this.connection = connection;

        pnlCentre.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        buttonUI();

        initiatePanel();

        pnlCentre.removeAll();
        pnlCentre.add(plantingTable);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }

    private void initiatePanel() {
        addPlantInformation = new AddPlantInformation(this.connection);
        
        plantingTable = new PlantingTable(this.connection);
        plantingTable.setPreferredSize(pnlCentre.getSize());

        plantSettings = new PlantSettings(this.connection);
    }

    private void buttonUI() {
        JButton[] btnsUI = {plantingTableButton, addButton, deleteButton, editButton, excelButton, propertyButton};
        for (JButton btn : btnsUI) {
            btn.setUI(new BasicButtonUI());
            btn.setBackground(new Color(39, 45, 54));
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
                    btn.setBackground(new Color(39, 45, 54));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(30, 36, 43));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(39, 45, 54));
                }
            });
        }

    }

    public JPanel getPanel(PANEL_NAMES panel_names) {
        switch (panel_names) {
            case Add:
                return addPlantInformation;
            case PlantingTable:
                return plantingTable;
            case edit:
                return addPlantInformation;
            case Settings:
                return plantSettings;
            default:
                return null;
        }
    }

    Boolean TableVisible() {
        return plantingTable.isVisible();
    }

    boolean deleteEntry() {

        try {
            int row = plantingTable.plantTable.getSelectedRow();

            String id = plantingTable.plantTable.getValueAt(row, 0).toString();
            String query = " Delete FROM " + tableName + " WHERE id =" + id;

            pst = connection.prepareStatement(query);
            pst.execute();

            return true;
        } catch (SQLException e) {
            System.out.print("delete| " + e);
            return false;
        }
    }

    void editEntry() {
//        setCurrentPanel(PANEL_NAMES.edit);

        int row = plantingTable.plantTable.getSelectedRow();
        String id = plantingTable.plantTable.getModel().getValueAt(row, 0).toString();

        try {
            String query = "SELECT * FROM " + tableName + " WHERE id=" + id;
            pst = connection.prepareStatement(query);
            resultSet = pst.executeQuery();

            addPlantInformation.setValues(resultSet, id);

        } catch (SQLException e) {
            System.out.println("edit| " + e);
        } finally {
            try {
                if (pst != null && resultSet != null) {
                    pst.close();
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        plantingTableButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        excelButton = new javax.swing.JButton();
        propertyButton = new javax.swing.JButton();
        pnlCentre = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        basePanel.setBackground(new java.awt.Color(39, 45, 54));
        basePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(39, 45, 54));
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 486));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 20));

        plantingTableButton.setBackground(new java.awt.Color(30, 36, 43));
        plantingTableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_table.png"))); // NOI18N
        plantingTableButton.setPreferredSize(new java.awt.Dimension(50, 50));
        plantingTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plantingTableButtonActionPerformed(evt);
            }
        });
        jPanel1.add(plantingTableButton);

        addButton.setBackground(new java.awt.Color(30, 36, 43));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_add.png"))); // NOI18N
        addButton.setPreferredSize(new java.awt.Dimension(50, 50));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addButton);

        deleteButton.setBackground(new java.awt.Color(30, 36, 43));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_delete.png"))); // NOI18N
        deleteButton.setPreferredSize(new java.awt.Dimension(50, 50));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jPanel1.add(deleteButton);

        editButton.setBackground(new java.awt.Color(30, 36, 43));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_edit.png"))); // NOI18N
        editButton.setPreferredSize(new java.awt.Dimension(50, 50));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jPanel1.add(editButton);

        excelButton.setBackground(new java.awt.Color(30, 36, 43));
        excelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_excel.png"))); // NOI18N
        excelButton.setPreferredSize(new java.awt.Dimension(50, 50));
        excelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(excelButton);

        propertyButton.setBackground(new java.awt.Color(30, 36, 43));
        propertyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_table_property.png"))); // NOI18N
        propertyButton.setPreferredSize(new java.awt.Dimension(50, 50));
        propertyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertyButtonActionPerformed(evt);
            }
        });
        jPanel1.add(propertyButton);

        basePanel.add(jPanel1, java.awt.BorderLayout.LINE_START);

        pnlCentre.setBackground(new java.awt.Color(24, 32, 44));
        pnlCentre.setPreferredSize(new java.awt.Dimension(966, 556));
        pnlCentre.setLayout(new java.awt.BorderLayout());
        basePanel.add(pnlCentre, java.awt.BorderLayout.CENTER);

        add(basePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void plantingTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plantingTableButtonActionPerformed
//        setCurrentPanel(PANEL_NAMES.PlantingTable);
//        plantingTable=new PlantingTable(this.connection);
//        pnlCentre.add(plantingTable);
        pnlCentre.removeAll();
        pnlCentre.add(plantingTable);
        pnlCentre.repaint();
        pnlCentre.revalidate();
        plantingTable.updateTable();
    }//GEN-LAST:event_plantingTableButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
//        setCurrentPanel(PANEL_NAMES.Add);
        pnlCentre.removeAll();
        pnlCentre.add(addPlantInformation);
        addPlantInformation.edit(false);
        pnlCentre.repaint();
        pnlCentre.revalidate();
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        if (TableVisible()) {
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the entry",
                    "Delete Entry",
                    JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                deleteEntry();
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (TableVisible()) {
            pnlCentre.removeAll();
            pnlCentre.add(addPlantInformation);
            editEntry();
            addPlantInformation.edit(true);
            pnlCentre.repaint();
            pnlCentre.revalidate();
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void excelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelButtonActionPerformed
//        Exporting table information to a new "file"
        Exporter exporter = new Exporter(plantingTable.plantTable);
        if (exporter.saveFile(this)) {
            JOptionPane.showMessageDialog(this, "File saved.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not save file");
        }
    }//GEN-LAST:event_excelButtonActionPerformed

    private void propertyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertyButtonActionPerformed
        // TODO add your handling code here:
//        setCurrentPanel(PANEL_NAMES.Settings);
        pnlCentre.removeAll();
        pnlCentre.add(plantSettings);
        pnlCentre.repaint();
        pnlCentre.revalidate();
        plantSettings.updateTables();
    }//GEN-LAST:event_propertyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    public javax.swing.JPanel basePanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton excelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton plantingTableButton;
    private javax.swing.JPanel pnlCentre;
    private javax.swing.JButton propertyButton;
    // End of variables declaration//GEN-END:variables
}
