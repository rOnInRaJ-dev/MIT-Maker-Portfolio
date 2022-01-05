package nature.stalks;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exporter {

    JTable jTable;
    int colCount;

    public Exporter(JTable jTable) {
        this.jTable = jTable;
    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public boolean saveFile(Component component){
        try{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(component);
            File saveFile = jFileChooser.getSelectedFile();
            
            if(saveFile != null){
                saveFile = new File(saveFile.toString()+".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Customer");
                
                Row rowCol = sheet.createRow(0);
                
//                Set column heading names
                for (int i = 0; i < jTable.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(jTable.getColumnName(i));
                }
                
                for (int j = 0; j < jTable.getRowCount(); j++) {
                    Row row = sheet.createRow(j+1);
                    for (int k = 0; k < jTable.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if(jTable.getValueAt(j, k) != null){
                            cell.setCellValue(jTable.getValueAt(j, k).toString());
                        }
                    }
                }
                
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());
                
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error at general archive");
                return false;
            }
            
        } catch(FileNotFoundException e){
            System.out.println(e);
            return false;
        } catch (IOException e){
            System.out.println(e);
            return false;
        }
    }

}
