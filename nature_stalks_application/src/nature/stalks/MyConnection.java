package nature.stalks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyConnection {

    String hostName = "jdbc:mysql://nature-stalks-db1.ccidj1x9gotj.af-south-1"
            + ".rds.amazonaws.com:3306?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String userName = "root";
    String password = "Wz7V/Wnc\"!exu.W";
    
    Connection connection;

    public Connection connectDB() {

        try {
            String databaseURL = "";
            connection = DriverManager.getConnection(hostName, userName, password);
            System.out.println("Connected to database");
            return connection;

        } catch (SQLException error) {
            System.out.println("MyConnection error: " + error.getMessage());
            JOptionPane.showMessageDialog(null, error);
            return connection;
        }
    }
}
