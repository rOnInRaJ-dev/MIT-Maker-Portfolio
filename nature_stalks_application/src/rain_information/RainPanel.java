package rain_information;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;
import nature.stalks.Exporter;

public class RainPanel extends javax.swing.JPanel {

    String fileName = "PlantingInformation.xls";
    File fileOutput = new File("D:\\Github\\Nature-Stalks\\dist\\" + fileName);

//    Variables for Panel Layout
    GridBagLayout layout = new GridBagLayout();
    AddRainInformation addRainInformation;
    RainTable rainTable;
    EditRainInformation editRainInformation;

//  Variables for SQL and connection to database
    PreparedStatement pst;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String SQL;

    String tableName = "rds_db1.rain_info";
    
    
    public static enum PANEL_NAMES{
        Rain,
        Add,
        Edit,
    }
    
    public RainPanel(Connection connection) {
        initComponents();
        this.connection = connection;
        
        buttonUI();
        pnlCentre.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        
        setCurrentPanel(PANEL_NAMES.Rain);
    }
    
    public void setBaseSize(Dimension dimension){
        basePanel.setPreferredSize(dimension);
        
    }
    
    private void buttonUI() {
        JButton[] btnsUI = {rainTableButton, addButton, deleteButton, editButton, excelButton};
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
            case Add: return addRainInformation;
            case Rain: return rainTable;
            case Edit: return editRainInformation;
            default: return null;
        }
    }

    void initiatePanel(PANEL_NAMES panel_names) {
        pnlCentre.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        if (null != panel_names) switch (panel_names) {
            case Add:
                addRainInformation = new AddRainInformation(this.connection);
                break;
            case Rain:
                //            System.out.println(" panel || " + this.connection);
                rainTable = new RainTable(this.connection);
                rainTable.setSize(pnlCentre.getSize());
                rainTable.updateTable();
                break;
            case Edit:
                editRainInformation = new EditRainInformation(this.connection);
                break;
            default:
                break;
        }

        pnlCentre.add(getPanel(panel_names));
    }

    public void setCurrentPanel(PANEL_NAMES currentPanel) {

        for (PANEL_NAMES panel_names : PANEL_NAMES.values()) {
            if (getPanel(panel_names) != null) {
                getPanel(panel_names).setVisible(false);
            }
        }
        initiatePanel(currentPanel);

    }
    
    Boolean TableVisible() {
        return rainTable.rainJtable.isVisible();
    }
    
    void editEntry() {
        setCurrentPanel(PANEL_NAMES.Edit);

        int row = rainTable.rainJtable.getSelectedRow();
        String id = rainTable.rainJtable.getModel().getValueAt(row, 0).toString();

        try {
            String query = "SELECT * FROM " + tableName + " WHERE id=" + id;
            pst = connection.prepareStatement(query);
            resultSet = pst.executeQuery();

            editRainInformation.setValues(resultSet, id);

        } catch (SQLException e) {
            System.out.println("edit| " + e);
        }
    }

    boolean deleteEntry() {

        try {
            int row = rainTable.rainJtable.getSelectedRow();

            String id = rainTable.rainJtable.getValueAt(row, 0).toString();
            String query = " Delete FROM " + tableName + " WHERE id =" + id;

            pst = connection.prepareStatement(query);
            pst.execute();

            setCurrentPanel(PANEL_NAMES.Rain);
            return true;
        } catch (SQLException e) {
            System.out.print("delete| " + e);
            return false;
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

        basePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        rainTableButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        excelButton = new javax.swing.JButton();
        pnlCentre = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        basePanel.setBackground(new java.awt.Color(39, 45, 54));
        basePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(39, 45, 54));
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 486));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 20));

        rainTableButton.setBackground(new java.awt.Color(30, 36, 43));
        rainTableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon_table.png"))); // NOI18N
        rainTableButton.setPreferredSize(new java.awt.Dimension(50, 50));
        rainTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rainTableButtonActionPerformed(evt);
            }
        });
        jPanel1.add(rainTableButton);

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

        basePanel.add(jPanel1, java.awt.BorderLayout.LINE_START);

        pnlCentre.setBackground(new java.awt.Color(24, 32, 44));

        javax.swing.GroupLayout pnlCentreLayout = new javax.swing.GroupLayout(pnlCentre);
        pnlCentre.setLayout(pnlCentreLayout);
        pnlCentreLayout.setHorizontalGroup(
            pnlCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 894, Short.MAX_VALUE)
        );
        pnlCentreLayout.setVerticalGroup(
            pnlCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );

        basePanel.add(pnlCentre, java.awt.BorderLayout.CENTER);

        add(basePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void rainTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainTableButtonActionPerformed
        setCurrentPanel(PANEL_NAMES.Rain);
    }//GEN-LAST:event_rainTableButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        setCurrentPanel(PANEL_NAMES.Add);
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
            editEntry();
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void excelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelButtonActionPerformed
        //        Exporting table information to a new "file"
        Exporter exporter = new Exporter(rainTable.rainJtable);
        if(exporter.saveFile(this)){
            JOptionPane.showMessageDialog(this, "File saved.");
        } else{
            JOptionPane.showMessageDialog(this, "Could not save file");
        }
    }//GEN-LAST:event_excelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton excelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlCentre;
    private javax.swing.JButton rainTableButton;
    // End of variables declaration//GEN-END:variables
}
