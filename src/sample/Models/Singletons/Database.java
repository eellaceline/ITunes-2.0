package sample.Models.Singletons;

import com.mysql.jdbc.Connection;
import sample.Handlers.Handler_Alert;
import sample.Handlers.Handler_Password;
import sample.Models.Artist;
import sample.Models.Song;
import sample.Models.User;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Database database;

    private String url = "jdbc:mysql://den1.mysql3.gear.host/itunes?user=itunes&password=itunes!";

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

    public User getUser(String userName) {
        User user = null;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + userName + "';");

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
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLEXception",
                    "Error exececuting getuser query",
                    false);
        }
        return user;
    }

    public User getUser(String userName, String email) {
        User user = null;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + userName + "' AND email = '" + email + "';");

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
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLEXception",
                    "Error exececuting query based on username and email",
                    false);
        }
        return user;
    }

    public void saveAccount(String username, String email, String password) {
        password = Handler_Password.encryption(password);
        try {
            statement.executeUpdate("INSERT INTO user (username, email, password, isAnAdmin) VALUES ('" + username + "', '" + email + "', '" + password +"', 0);");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when inserting user",
                    false
            );
        }
    }

    // checks if the user that logs in is an admin and returns a boolean
    // for the purpose of notifying if the user was an admin or not
    public boolean isAdmin(String userName) {
        boolean isAdmin = false;
        int isAnAdmin = 0;

        try {
            ResultSet rs = statement.executeQuery("SELECT isAnAdmin FROM user WHERE username = '" + userName + "';");

            while(rs.next()) {
                isAnAdmin = rs.getInt(1);
                //System.out.println("ID: " + ID);
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to execute query isAdmin");
        }

        if (isAnAdmin == 1)
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


    //TODO not optimised for new database
    // fetching the songs to be displayed in a library
    public ArrayList<Song> getLibraryForUser(String userName) {
        ArrayList<Song> songList = new ArrayList<>();
        String userID = "";

        // these are needed to fill the songList with data later
        ArrayList<Integer> songID = new ArrayList<>();
        ArrayList<String> songName = new ArrayList<>();
        ArrayList<String> songDuration = new ArrayList<>();
        ArrayList<Integer> price = new ArrayList<>();
        ArrayList<ArrayList<Artist>> artists = new ArrayList<>();
        ArrayList<String> genreName = new ArrayList<>();

        // Selects the userID needed to find songs in the library
        try {
            ResultSet rs = statement.executeQuery("SELECT user_id FROM user WHERE userName = '" + userName + "';");
            while(rs.next()) {
                userID = rs.getString(1);
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "Error reading from DB",
                    false
            );
        }

        // fetches songID's dependent on the users library
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT DISTINCT songs_song_id FROM user_has_songs WHERE user_user_id = " + userID + ";"
            );

            while(rs.next()) {
                // cant create the Song object here since we cant fetch all data
                songID.add(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "Error finding song_id's based on user_id",
                    false
            );
        }

        try {
            // a string to complete the WHERE statement in the query
            String where = "";
            for (int i=0; i<songID.size(); i++) {
                if (i == songID.size()-1)
                    where += "'" + songID.get(i) + "'";
                else
                    where += "'" + songID.get(i) + "' OR ";
            }
            System.out.println(where);
            ResultSet rs = statement.executeQuery(
                    "SELECT songName, songDuration, price, genre_genreName FROM songs WHERE song_id = " + where + ";"
            );
            while(rs.next()) {
                // cant create the song objects yet cause artists are missing
                songName.add(rs.getString(1));
                songDuration.add(rs.getString(2));
                price.add(rs.getInt(3));
                genreName.add(rs.getString(4));
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "Error fecthing song data",
                    false
            );
        }

        // Gathering the data from artists through the DB
        for (int i=0; i<songID.size(); i++) {
            try {
                ResultSet rs = statement.executeQuery(
                        "SELECT DISTINCT artist_id, artistName FROM artist, songs_has_artist WHERE songs_song_id = " + songID.get(i) + ";");
                while(rs.next()) {
                    artists.add(artists.size(), new ArrayList<>());
                    artists.get(i).add(new Artist(
                            rs.getInt(1),
                            rs.getString(2)));
                }
            }
            catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLExeception",
                        "Error fecthing artist data",
                        false
                );
            }
        }

        // takes all the gathered data and makes new Song objects that are added to the songList
        for (int i=0; i<songID.size(); i++) {
            songList.add(new Song(
                    songID.get(i),
                    songName.get(i),
                    songDuration.get(i),
                    price.get(i),
                    artists.get(i),
                    genreName.get(i)));
        }

        return songList;
    }

}
