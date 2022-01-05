package planting_information;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.swing.*;

import nature.stalks.MyConnection;
public class AddPlantInformation extends javax.swing.JPanel {

    //  Variables for SQL and connection to database
    PreparedStatement pst;
    Statement statement;
    Connection connection = null;
    ResultSet resultSet;
    String plantTableName = "rds_db1.plant_info";
    String plantTypeTableName = "rds_db1.plant_type";
    String chemicalTableName = "rds_db1.chemicals";
    String SQL;

    String[] column = {"key", "date_of_planting", "block", "crop", "variety",
        "chemical", "area", "seedling_no", "remarks"};

    MyConnection myconnection = new MyConnection();

//    Data Variables
    int id;
    Date dateOfPlanting;
    String blockName, crop, variety, chemicalUsed, remarks;
    float area;
    int numberOfSeedlingsPlanted;

    public AddPlantInformation(Connection connection) {
        System.out.println("add");
        initComponents();
        this.connection = connection;

        setUI();
        getCropComboDatabaseData();
        getChemComboDatabaseData();
    }

    public void edit(Boolean edit) {
        if (edit) {
            addButton.setEnabled(false);
            addButton.setVisible(false);
            editButton.setEnabled(true);
            editButton.setVisible(true);
        } else if (!edit) {
            clear();
            addButton.setEnabled(true);
            addButton.setVisible(true);
            editButton.setEnabled(false);
            editButton.setVisible(false);
        }
    }

    void getCropComboDatabaseData() {
        try {

            SQL = "SELECT crop FROM " + plantTypeTableName;
            pst = connection.prepareStatement(SQL);
            resultSet = pst.executeQuery();

            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
            ArrayList<String> crops = new ArrayList<>();

            //Stores properties of a ResultSet object, including column count
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (resultSet.next()) {
                int i = 1;
                while (i <= columnCount) {
                    crops.add(resultSet.getString(i++));
                }
            }

            // Update the combobox model with the arraylist crops without any duplicates
            for (String cropString : crops) {
                if (comboBoxModel.getIndexOf(cropString) == -1) {
                    comboBoxModel.addElement(cropString);
                }
            }

            cropCombo.setModel(comboBoxModel);

        } catch (SQLException e) {
            System.out.println("at crop Combo" + e);

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
    }

    void getVarietyComboDatabaseData() {
        try {

            SQL = "SELECT variety FROM " + plantTypeTableName + " WHERE crop='" + crop + "'";
            pst = connection.prepareStatement(SQL);
            resultSet = pst.executeQuery();

            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
            ArrayList<String> varietyArrayList = new ArrayList<>();

            //Stores properties of a ResultSet object, including column count
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (resultSet.next()) {
                int i = 1;
                while (i <= columnCount) {
                    varietyArrayList.add(resultSet.getString(i++));
                }
            }

            // Update the combobox model with the arraylist crops without any duplicates
            for (String varietyString : varietyArrayList) {
                if (comboBoxModel.getIndexOf(varietyString) == -1) {
                    comboBoxModel.addElement(varietyString);
                }
            }

            varietyCombo.setModel(comboBoxModel);

        } catch (SQLException e) {
            System.out.println("at var Combo " + e);

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
    }

    void getChemComboDatabaseData() {
        try {

            SQL = "SELECT chemical FROM " + chemicalTableName;
            pst = connection.prepareStatement(SQL);
            resultSet = pst.executeQuery();

            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
            ArrayList<String> chemicalsArrayList = new ArrayList<>();

            //Stores properties of a ResultSet object, including column count
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (resultSet.next()) {
                int i = 1;
                while (i <= columnCount) {
                    chemicalsArrayList.add(resultSet.getString(i++));
                }
            }

            // Update the combobox model with the arraylist crops without any duplicates
            for (String chemicalString : chemicalsArrayList) {
                if (comboBoxModel.getIndexOf(chemicalString) == -1) {
                    comboBoxModel.addElement(chemicalString);
                }
            }

            chemCombo.setModel(comboBoxModel);

        } catch (SQLException e) {
            System.out.println("at chem Combo" + e);

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
    }

    void setUI() {

        JComponent[] components = {AreaTextField, blockTextField, chemCombo,
            numberOfSeedlingsTextField, varietyCombo, dateOfPlantingDateChooser, RemarksTextPane, cropCombo};

        Font font = new Font("Tahoma", Font.PLAIN, 20);

        for (JComponent component : components) {
            component.setBackground(Color.WHITE);
            component.setForeground(Color.BLACK);
            component.setFont(font);
        }

        dateOfPlantingDateChooser.setEnabled(false);
        dateOfPlantingDateChooser.getCalendarButton().setEnabled(true);

    }

    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    void getData() {
        dateOfPlanting = convertJavaDateToSqlDate(dateOfPlantingDateChooser.getDate());
        blockName = blockTextField.getText();
        crop = cropCombo.getSelectedItem().toString();
        variety = varietyCombo.getSelectedItem().toString();
        chemicalUsed = chemCombo.getSelectedItem().toString();
        area = Float.parseFloat(AreaTextField.getText());
        numberOfSeedlingsPlanted = Integer.parseInt(numberOfSeedlingsTextField.getText());
        remarks = RemarksTextPane.getText();
    }

    boolean isValidated() {
        if (blockTextField.getText() == null
                || dateOfPlantingDateChooser.getDate() == null) {
            statusTextField.setForeground(Color.RED);
            statusTextField.setText(statusTextField.getText() + "Please fill in all the required information");
            return false;
        } else {
            boolean areaValidated = false;
            try {
                if (AreaTextField.getText() != null) {
                    Integer.parseInt(AreaTextField.getText());
                    areaValidated = true;
                }
            } catch (NumberFormatException e) {
                statusTextField.setForeground(Color.RED);
                statusTextField.setText(statusTextField.getText() + "Please enter a valid value for 'Area'");
                System.out.println("area " + e);
                return false;
            }
            if (areaValidated) {
                try {
                    Integer.parseInt(numberOfSeedlingsTextField.getText());
                } catch (NumberFormatException e) {
                    statusTextField.setForeground(Color.RED);
                    statusTextField.setText(statusTextField.getText() + "Please enter a valid value for 'Number of Seedlings'");
                    System.out.println("numberofseedlings" + e);
                    return false;
                }
                return true;
            } else {return false;}
        }
    }

    void setValues(ResultSet resultSet, String id) {
        this.id = Integer.parseInt(id);
        try {
            if (resultSet.next()) {
                dateOfPlantingDateChooser.setDate(resultSet.getDate(column[1]));
                blockTextField.setText(resultSet.getString(column[2]));

                cropCombo.setSelectedItem(resultSet.getString(column[3]));

                varietyCombo.setSelectedItem(resultSet.getString(column[4]));
                chemCombo.setSelectedItem(resultSet.getString(column[5]));
                AreaTextField.setText(resultSet.getString(column[6]));
                numberOfSeedlingsTextField.setText(resultSet.getString(column[7]));
                RemarksTextPane.setText(resultSet.getString(column[8]));

            }
        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
    }

    void addData() {
        if (isValidated()) {
            try {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                SQL = "SELECT * FROM " + plantTableName;
                resultSet = statement.executeQuery(SQL);

                /**
                 * Moves the cursor in the database to insert a new row for all
                 * the data
                 */
                resultSet.moveToInsertRow();
                resultSet.updateDate(column[1], dateOfPlanting);
                resultSet.updateString(column[2], blockName);
                resultSet.updateString(column[3], crop);
                resultSet.updateString(column[4], variety);
                resultSet.updateString(column[5], chemicalUsed);
                resultSet.updateFloat(column[6], area);
                resultSet.updateInt(column[7], numberOfSeedlingsPlanted);
                resultSet.updateString(column[8], remarks);
                resultSet.insertRow();

            } catch (SQLException error) {
                System.out.println("addPLant " + error);

            } finally {
                try {
                    resultSet.close();
                } catch (SQLException error) {
                    System.out.println(error);
                }
            }
        }
    }

    void editData() {
        if (isValidated()) {
            try {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                SQL = "SELECT * FROM " + plantTableName;
                resultSet = statement.executeQuery(SQL);

                String query = ("UPDATE " + plantTableName + " SET `" + column[1] + "` = '" + dateOfPlanting + "',"
                        + "`" + column[2] + "` = '" + blockName + "',"
                        + "`" + column[3] + "` = '" + crop + "',"
                        + "`" + column[4] + "` = '" + variety + "',"
                        + "`" + column[5] + "` = '" + chemicalUsed + "',"
                        + "`" + column[6] + "` = '" + area + "',"
                        + "`" + column[7] + "` = '" + numberOfSeedlingsPlanted + "',"
                        + "`" + column[8] + "` = '" + remarks + "'"
                        + " WHERE `id` = '" + id + "';");
                pst = connection.prepareStatement(query);
                pst.executeUpdate();

            } catch (SQLException error) {
                System.out.println("edit data| " + error);

            } finally {
                try {
                    resultSet.close();
                } catch (SQLException error) {
                    System.out.println(error);
                }
            }
        } else {

            JOptionPane.showMessageDialog(this, "invalid data entered");
        }
    }

    void clear() {
        dateOfPlantingDateChooser.setDate(null);
        blockTextField.setText(null);
        cropCombo.setSelectedItem(null);
        varietyCombo.setSelectedItem(null);
        chemCombo.setSelectedItem(null);
        AreaTextField.setText(null);
        numberOfSeedlingsTextField.setText(null);
        RemarksTextPane.setText(null);
        statusTextField.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        AreaTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RemarksTextPane = new javax.swing.JTextPane();
        jLabel9 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dateOfPlantingDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        blockTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        numberOfSeedlingsTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cropCombo = new javax.swing.JComboBox<>();
        editButton = new javax.swing.JButton();
        chemCombo = new javax.swing.JComboBox<>();
        varietyCombo = new javax.swing.JComboBox<>();
        statusTextField = new javax.swing.JLabel();

        setBackground(new java.awt.Color(39, 45, 54));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Chemical Used");

        AreaTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        AreaTextField.setForeground(new java.awt.Color(0, 0, 0));
        AreaTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));

        addButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        addButton.setForeground(new java.awt.Color(0, 0, 0));
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        RemarksTextPane.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        RemarksTextPane.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(RemarksTextPane);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Number Of Seedlings*");

        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 0, 0));
        cancelButton.setText("Clear");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Date Of Planting*");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Block*");

        dateOfPlantingDateChooser.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Variety*");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Crop Name*");

        blockTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        blockTextField.setForeground(new java.awt.Color(0, 0, 0));
        blockTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));
        blockTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Area");

        numberOfSeedlingsTextField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        numberOfSeedlingsTextField.setForeground(new java.awt.Color(0, 0, 0));
        numberOfSeedlingsTextField.setMargin(new java.awt.Insets(0, 2, 0, 0));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Remarks");

        cropCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cropComboItemStateChanged(evt);
            }
        });
        cropCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropComboActionPerformed(evt);
            }
        });

        editButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        editButton.setForeground(new java.awt.Color(0, 0, 0));
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        chemCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemComboActionPerformed(evt);
            }
        });

        varietyCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varietyComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editButton)
                        .addGap(231, 231, 231)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blockTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(cropCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(476, 476, 476)
                                    .addComponent(jLabel4))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel8)
                                            .addComponent(chemCombo, 0, 319, Short.MAX_VALUE))
                                        .addComponent(statusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(129, 129, 129)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(dateOfPlantingDateChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(numberOfSeedlingsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                        .addComponent(varietyCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(AreaTextField)))))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateOfPlantingDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(blockTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cropCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(varietyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chemCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(numberOfSeedlingsTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AreaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(addButton)
                    .addComponent(editButton))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (isValidated()) {
            getData();
            addData();
            JOptionPane.showMessageDialog(this, "Database Updated");
            clear();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void blockTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_blockTextFieldActionPerformed

    private void cropComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cropComboActionPerformed
        // TODO add your handling code here:
//        getVarietyComboDatabaseData();
    }//GEN-LAST:event_cropComboActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (isValidated()) {
            getData();
            editData();
            JOptionPane.showMessageDialog(this, "Database Updated");
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void chemComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chemComboActionPerformed

    private void varietyComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varietyComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_varietyComboActionPerformed

    private void cropComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cropComboItemStateChanged
        // TODO add your handling code here:
        try {
            if (cropCombo.getSelectedItem().toString() != null) {
                crop = cropCombo.getSelectedItem().toString();
                getVarietyComboDatabaseData();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_cropComboItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AreaTextField;
    private javax.swing.JTextPane RemarksTextPane;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField blockTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> chemCombo;
    private javax.swing.JComboBox<String> cropCombo;
    private com.toedter.calendar.JDateChooser dateOfPlantingDateChooser;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField numberOfSeedlingsTextField;
    private javax.swing.JLabel statusTextField;
    private javax.swing.JComboBox<String> varietyCombo;
    // End of variables declaration//GEN-END:variables
}
