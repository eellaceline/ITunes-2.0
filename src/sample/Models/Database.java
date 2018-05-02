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

    public User getUser(String userName, String email) {
        User user = null;

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM user, WHERE username = '" + userName + "' AND email = '" + email + "';");

            while(rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getBoolean(6));

            }
        }
        catch(SQLException ex){
            System.out.println("error getting user based on username and email");
        }

        return user;
    }

    public void getAdminID() {

    }

    public boolean isAdmin(String userName) {
        boolean isAdmin = false;
        int ID = 0;

        try {
            ResultSet rs = statement.executeQuery("SELECT user_id FROM user WHERE username = '" + userName + "';");

            while(rs.next()) {
                ID = rs.getInt(1);
                System.out.println("ID23: " + ID);
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

        System.out.println("ID :" + ID);
        System.out.println("AdminID :" + adminID);
        if (ID == adminID)
            isAdmin = true;

        return isAdmin;
    }

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
        System.out.println("pw: " + pw);
        return pw;
    }

    public void createAccount() {
        try {
            ResultSet rs = statement.executeQuery("INSERT INTO user (userName, email) VALUES");
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute");
        }
    }
}
