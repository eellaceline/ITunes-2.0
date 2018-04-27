package sample.Models;

import com.mysql.jdbc.Connection;

import java.sql.*;

public class Database {
    private static Database database;

    private String url = "jdbc:mysql://127.0.0.1:3306/musicdb?user=root&password=root";

    private Statement statement;

    private Database() {
        try {
            Connection c = (Connection) DriverManager.getConnection(url);
            statement = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("the connection fails");
        }
    }

    public static Database getInstance(){
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    // might not be of use
    public void getAdminID() {

    }

    // checks if the user that logs in is an admin and returns a boolean
    // for the purpose of notifying if the user was an admin or not
    public boolean isAdmin(String userName) {
        boolean isAdmin = false;
        int ID = 0;

        try {
            ResultSet rs = statement.executeQuery("SELECT user_id FROM user WHERE username = '" + userName + "';");

            while(rs.next()) {
                ID = rs.getInt(1);
                //System.out.println("ID: " + ID);
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query isAdmin");
        }

        int adminID = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT user_user_id FROM admin, user WHERE username = '" + userName + "';");
            while(rs.next()) {
                adminID = rs.getInt(1);
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query compare adminID");
        }

        //System.out.println("ID :" + ID);
        //System.out.println("AdminID :" + adminID);
        if (ID == adminID)
            isAdmin = true;

        return isAdmin;
    }

    // returns the password from the database dependant on user name
    public String getPassword(String userName) {
        String pw = "";
        try {
            ResultSet rs = statement.executeQuery("SELECT password FROM user WHERE username = '" + userName + "';");
            while(rs.next()) {
                pw = rs.getString(1);
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query password");
        }
        catch (NullPointerException ex) {
            System.out.println("no database connection");
        }
        return pw;
    }
}
