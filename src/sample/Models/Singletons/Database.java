package sample.Models.Singletons;

import com.mysql.jdbc.Connection;
import sample.Handlers.Handler_Alert;
import sample.Models.Artist;
import sample.Models.Song;
import sample.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Handler;

public class Database {
    private static Database database;

    private String url = "jdbc:mysql://den1.mysql3.gear.host:3306/itunes?user=itunes&password=itunes!";
    //String url = "jdbc:mysql://127.0.0.1:3306/musicdb?user=root&password=root";

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

    public User getUser(String userName) throws NullPointerException {
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

    public User getUser(String userName, String email) throws NullPointerException {
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
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLEXception",
                    "Error exececuting query based on username and email",
                    false);
        }
        return user;
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

    public ArrayList<Song> getStore(User loggedInUser) {
        ArrayList<Song> songList = new ArrayList<>();
        ArrayList<Integer> ownedSongID = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery("SELECT songs_song_id FROM user_has_songs WHERE user_user_id = " + loggedInUser.getUserID() + ";");
            while(rs.next()) {
                ownedSongID.add(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "select songs_song_id query failed",
                    false
            );
        }

        try {
            ResultSet rs = statement.executeQuery("");
        }
        catch(SQLException ex) {

        }

        return songList;
    }

    //TODO not optimised for new database
    // fetching the songs to be displayed in a library
    public ArrayList<Song> getLibraryForUser() {
        ArrayList<Song> songList = new ArrayList<>();
        int userID;

        // these are needed to fill the songList with data later
        ArrayList<Integer> songID = new ArrayList<>();
        ArrayList<String> songName = new ArrayList<>();
        ArrayList<String> songDuration = new ArrayList<>();
        ArrayList<Integer> price = new ArrayList<>();
        ArrayList<ArrayList<Artist>> artists = new ArrayList<>();
        ArrayList<String> genreName = new ArrayList<>();
        ArrayList<Integer> albumID = new ArrayList<>();
        ArrayList<String> albumName = new ArrayList<>();
        ArrayList<Integer> artistArtistID = new ArrayList<>();
        ArrayList<String> artistName = new ArrayList<>();
        ArrayList<Integer> albumArtist = new ArrayList<>();
        ArrayList<Integer> artistID = new ArrayList<>();


        // Selects the userID needed to find songs in the library

        userID = LoggedInUser.getInstance().getUser().getUserID();

        try {
            // a string to complete the WHERE statement in the query
            /*String where = "";
            for (int i = 0; i < songID.size(); i++) {
                if (i == songID.size() - 1)
                    where += "'" + songID.get(i) + "'";
                else
                    where += "'" + songID.get(i) + "' OR ";
            }
            System.out.println(where);*/
            ResultSet rs = statement.executeQuery(
                    "SELECT songs.*, album.* FROM songs LEFT JOIN album ON songs.album_album_id = album.album_id  WHERE song_id " +
                    "IN (SELECT DISTINCT songs_song_id FROM user_has_songs WHERE user_user_id = " + userID + ");"
            );
            while (rs.next()) {
                // cant create the song objects yet cause artists are missing
                songID.add(rs.getInt(1));
                songName.add(rs.getString(2));
                songDuration.add(rs.getString(3));
                price.add(rs.getInt(4));
                genreName.add(rs.getString(5));
                albumID.add(rs.getInt(6));
                albumName.add(rs.getString(8));
                albumArtist.add(rs.getInt(9));
            }
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "Error fecthing song data",
                    false
            );
        }
        // Gathering the data from artists through the DB
        ArrayList<Integer> SongSongID = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM songs_has_artist WHERE songs_song_id " +
                            "IN (SELECT songs_song_id FROM user_has_songs WHERE user_user_id = " + userID + ") ORDER BY songs_song_id;");
            while (rs.next()) {
                SongSongID.add(rs.getInt(1));
                artistArtistID.add(rs.getInt(2));
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLExeception",
                    "Error fetching artistID data",
                    false
            );
        }

        ArrayList<Artist> tempArtistsList = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM artist;");
            while (rs.next()) {
                tempArtistsList.add(new Artist(rs.getInt(1), rs.getString(2)));
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error getting data from artists",
                    false
            );
        }

        ArrayList<Integer> counter2 = new ArrayList<>();
        int counter = 1;
        int previousSongID = 0;
        int clumpedIndex = 0;
        int Ti = 0;
        int Si = 0;
        int nextSongID = 0;

        //TODO needed for later
        // artists.add(artists.size(), new ArrayList<>());

        for (int i=0; i<songID.size(); i++) {
            artists.add(artists.size(), new ArrayList<>());
            System.out.println("---------------");
            System.out.println("i:"+i);

            for (int k=0; k<tempArtistsList.size(); k++) {

                if (tempArtistsList.get(k).getArtistID() == artistArtistID.get(Ti)) {
                    boolean continueLoop = true;
                    artists.get(i).add(tempArtistsList.get(k));
                    System.out.println("Added artist on i=" + i);
                    while (continueLoop) {
                        System.out.println("Ti:"+Ti);

                            if (Ti+1 >= SongSongID.size()) {
                                continueLoop = false;
                                nextSongID = 0;
                            }
                            else {
                                nextSongID = SongSongID.get(Ti+1);
                            }

                        System.out.println("currentID:"+SongSongID.get(Ti));
                        System.out.println("nextID:"+nextSongID);
                        if (nextSongID == SongSongID.get(Ti)) {
                            Ti++;
                            System.out.println("c:"+SongSongID.get(Ti) + nextSongID);
                            //System.out.println(artists.get(i).size());
                            System.out.println("added artist on i=" + i + " size: ");// + Integer.toString(artists.get(i).size()));
                            artists.get(i).add(tempArtistsList.get(k+(Ti-i)));
                        }
                        else {
                            Ti++;
                            k=4;
                            System.out.println("exiting while loop");
                            continueLoop = false;
                            Si = Ti;
                        }

                    }

                    //if (previousSongID == SongSongID.get(i)) {
//                        boolean continueLoop = true;
//                        System.out.println("i: "+i);
//                        Ti = i;
//                        Si = i;
//                        while (continueLoop) {
//
//                            System.out.println("Ti"+Ti);
//                            if (previousSongID == SongSongID.get(++Ti)) {
//
//                                artists.get(Si).add(tempArtistsList.get(k));
//                                previousSongID = SongSongID.get(Ti);
//                            }
//                                continueLoop = false;
//                            }
//                        }
//                    }


                }
            }
        }

        System.out.println("sondID"+SongSongID.size());
        System.out.println("artists"+artists.size());
        System.out.println(artists.toString());
        //System.out.println("" + artists.get(5) + artists.get(6) + artists.get(7).toString() + artists.get(8));

        // takes all the gathered data and makes new Song objects that are added to the songList
        for (int i=0; i<songID.size(); i++) {
            songList.add(new Song(
                    songID.get(i),
                    songName.get(i),
                    songDuration.get(i),
                    price.get(i),
                    artists.get(i),
                    genreName.get(i),
                    albumName.get(i)));
        }

        return songList;
    }

}
