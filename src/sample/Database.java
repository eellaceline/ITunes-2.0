package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String url = "jdbc:mysql://127.0.0.1:3306/musicdb?user=root&password=root";

    Statement statement;

   // Database connection = new Database();
     //   connection.updatingCity();


    public Database() {
        try {
            Connection c = (Connection) DriverManager.getConnection(url);
            statement = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("the connection fails");
        }
    }
}
