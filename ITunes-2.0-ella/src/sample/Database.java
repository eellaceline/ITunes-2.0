package sample;

import java.sql.*;

public class Database {
    String url = "jdbc:mysql://127.0.0.1:3306/musicdb?user=root&password=root";

    Statement statement;


    public Database() {
        try {
            Connection c = (Connection) DriverManager.getConnection(url);
            statement = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("the connection fails");
        }
    }

    public void getAdminID() {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM admin;");

            while(rs.next()) {
                System.out.println("ID: " + rs.getDouble(1));
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query adminID");
        }

    }

    public String getPassword(String userName) {
        String pw = "";
        System.out.println(userName);
        try {
            ResultSet rs = statement.executeQuery("SELECT password FROM user WHERE username = '" + userName + "';");
            pw = rs.getString(1);
            while(rs.next()) {
                System.out.println("Password: " + rs.getString(1));
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query password");
        }
        System.out.println("pw: " + pw);
        return pw;
    }
}