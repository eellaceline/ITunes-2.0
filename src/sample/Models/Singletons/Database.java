package sample.Models.Singletons;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.log.Log;
import sample.Handlers.Handler_Alert;
import sample.Handlers.Handler_Password;
import sample.Models.Artist;
import sample.Models.Song;
import sample.Models.User;

import javax.xml.crypto.Data;
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
        }
        catch (SQLException ex) {
            System.out.println("the connection to DB failed");
        }
    }

    public static Database getInstance(){
        if (database == null) {
            database = new Database();
        }

        return database;
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
                    "SQLException",
                    "Error executing getUser query",
                    false);
        }
        return user;
    }

    public User getUser(String userName, String email) throws NullPointerException {
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
                    "SQLException",
                    "Error executing query based on username and email",
                    false);
        }
        return user;
    }

    //Update password
    public boolean changePassword(String username, String password ){
        boolean isConfirmed = false;
        System.out.println(username + password);
        String tempPw = Handler_Password.encryption(password);

        System.out.println("UPDATE user SET password = '" + password + "' WHERE username = '" + username + "'");

        try {

            int rows = statement.executeUpdate("UPDATE user SET password = '" + tempPw + "' WHERE username = '" + username + "'");
            isConfirmed = true;
            System.out.println(rows);
            if (rows != 0){
                Handler_Alert.information(
                        "Information",
                        "Password Changed",
                        "Your new password is " + password + ".",
                        false
                );
            }else{
                Handler_Alert.alert(
                        "Error",
                        "Password not changed",
                        "Your password could not be saved.",
                        false
                );
            }
        }catch (SQLException ex){  Handler_Alert.alert(
                "Error",
                "SQLException",
                "Error executing an update to changing password",
                false
        );
        }


        return isConfirmed;
    }

    //Update username
    public boolean changeUsername(String username, String newUsername){
        boolean isConfirmed = false;

        try {

            int rows = statement.executeUpdate("UPDATE user SET username = '" + newUsername + "' WHERE username = '" + username + "'");
            isConfirmed = true;
            System.out.println(rows);
            if (rows != 0){
                Handler_Alert.information(
                        "Information",
                        "Username Changed",
                        "Your new username is " + newUsername + ".",
                        false
                );
                LoggedInUser.getInstance().getUser().setUserName(newUsername    );
            }else{
                Handler_Alert.alert(
                        "Error",
                        "Username not changed",
                        "Your username could not be saved.",
                        false
                );
            }
        }catch (SQLException ex){  Handler_Alert.alert(
                "Error",
                "SQLException",
                "Error executing an update to changing username",
                false
                );
        }


        return isConfirmed;
    }
    // Update the price by admin
    public boolean updatePrice(String songName, String artistName, int newPrice) {
        boolean isConfirmed = false;
        try {
            int rows = statement.executeUpdate("UPDATE songs, artist SET price = '" + newPrice + "' WHERE songs.songName = '" + songName +"' AND artist.artistName = '" + artistName + "';");
            isConfirmed = true;
            System.out.println(rows);
            if (rows != 0) {
                Handler_Alert.information(
                        "Price Changed",
                        "Price Changed",
                        "The new price of the song " + songName + " is " + newPrice,
                        false
                );
            } else {
                Handler_Alert.alert(
                        "Price Not Changed",
                        "Price Not Changed",
                        "The new price could not change",
                        false
                );
            }
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing an update to song price",
                    false
            );
        }
        return isConfirmed;
    }

    // SQL statement in this order: Artist, Album, Song
    public void addSong(String songName, String artistName, String genreName) {

        try {
            statement.executeUpdate("INSERT INTO songs(songName) VALUES ('" + songName + "')");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error when inserting song name",
                    false
            );
        }

        //TODO needs check if the artist already exists
        try {
            statement.executeUpdate("INSERT INTO artist(artistName) VALUES ('" + artistName +"')");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when adding artist",
                    false
            );
        }

        //TODO check DB if genre exists before executing the update
        try {
            ResultSet rs = statement.executeQuery("INSERT INTO genre (genreName) VALUES ('" + genreName +"')");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when adding genre",
                    false
            );
        }

        //TODO if statement that checks if a there exists an album or not,
        // if there is no album to be added a secondary update without the album_album_id needs to be made
        try {
            statement.executeUpdate("INSERT INTO songs (songName, songDuration, price, genre_genreName, album_album_id) VALUES ('" + songName +"')");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when adding song",
                    false
            );
        }
    }

    public void deleteSong(int song_id) {
        try {
            statement.executeUpdate("DELETE FROM orders_has_songs WHERE songs_song_id = '" + song_id + "'");
            statement.executeUpdate("DELETE FROM songs_has_artist WHERE songs_song_id = '" + song_id + "'");
            statement.executeUpdate("DELETE FROM user_has_songs WHERE songs_song_id = '" + song_id + "'");
            statement.executeUpdate("DELETE FROM songs WHERE song_id = '" + song_id + "' ");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when deleting song",
                    false
            );
        }
    }

    public Song getSong(String songName, String artistName, String genreName) {
        Song song = null;

        try {
            ResultSet rs = statement.executeQuery("SELECT songName, artistName, genreName FROM songs, artist, genre");
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing when getting song",
                    false
            );
        }
        return song;
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

    public ArrayList<Song> getStore() {
        long startTime = System.nanoTime();

        ArrayList<Song> songList = new ArrayList<>();

        int userID = LoggedInUser.getInstance().getUser().getUserID();

        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT songs.*, album.* FROM songs LEFT JOIN album ON songs.album_album_id = album.album_id  WHERE NOT song_id " +
                    "IN (SELECT DISTINCT songs_song_id FROM user_has_songs WHERE user_user_id = " + userID + ");");
            while(rs.next()) {
                songList.add(new Song(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(8)));
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

        ArrayList<Integer> SongSongID = new ArrayList<>();
        ArrayList<Integer> artistArtistID = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM songs_has_artist WHERE NOT songs_song_id " +
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

        ArrayList<Artist> tempArtistsList = getArtists();
        ArrayList<ArrayList<Artist>> artists = new ArrayList<>();

        int Ti = 0;
        int nextSongID = 0;

        for (int i=0; i<songList.size(); i++) {
            artists.add(new ArrayList<>());

            for (int k=0; k<tempArtistsList.size(); k++) {

                if (tempArtistsList.get(k).getArtistID() == artistArtistID.get(Ti)) {

                    boolean continueLoop = true;
                    artists.get(i).add(tempArtistsList.get(k));

                    while (continueLoop) {
                        if (Ti+1 >= SongSongID.size()) {
                            continueLoop = false;
                            nextSongID = 0;
                        }
                        else {
                            nextSongID = SongSongID.get(Ti+1);
                        }

                        if (nextSongID == SongSongID.get(Ti++)) {
                            artists.get(i).add(tempArtistsList.get(k+(Ti-i)));
                        }
                        else {
                            k=4;
                            continueLoop = false;
                        }
                    }
                }
            }
        }

        for (int i=0; i<songList.size(); i++) {
            songList.get(i).setArtists(artists.get(i));
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("getStore execute time(ms): "+duration/1000000);

        return songList;
    }

    //TODO not optimised and quite unreadable
    // 3 query's exexuted minimum 300ms
    // fetching the songs to be displayed in a library
    public ArrayList<Song> getLibraryForUser() {
        long startTime = System.nanoTime();

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
        ArrayList<Integer> albumArtist = new ArrayList<>();

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

        // getting all artists from DB
        ArrayList<Artist> tempArtistsList = getArtists();

        int Ti = 0;
        int nextSongID = 0;

        //TODO needed for later
        // artists.add(artists.size(), new ArrayList<>());

        for (int i=0; i<songID.size(); i++) {
            artists.add(artists.size(), new ArrayList<>());
            for (int k=0; k<tempArtistsList.size(); k++) {
                if (tempArtistsList.get(k).getArtistID() == artistArtistID.get(Ti)) {
                    boolean continueLoop = true;
                    artists.get(i).add(tempArtistsList.get(k));
                    while (continueLoop) {
                        if (Ti+1 >= SongSongID.size()) {
                            continueLoop = false;
                            nextSongID = 0;
                        }
                        else {
                            nextSongID = SongSongID.get(Ti+1);
                        }

                        if (nextSongID == SongSongID.get(Ti++)) {
                                artists.get(i).add(tempArtistsList.get(k+(Ti-i)));
                        }
                        else {
                            k=4;
                            continueLoop = false;
                        }
                    }
                }
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
                    genreName.get(i),
                    albumName.get(i)));
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("getLibrary execute time(ms): "+duration/1000000);

        return songList;
    }

    public ArrayList<Artist> getArtists() {
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
        return tempArtistsList;
    }

    public ArrayList<Song> getLibraryForUserDebug() {
        long startTime = System.nanoTime();

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
        ArrayList<Integer> albumArtist = new ArrayList<>();

        // Selects the userID needed to find songs in the library

        userID = LoggedInUser.getInstance().getUser().getUserID();

        try {
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

        // getting all artists from DB
        ArrayList<Artist> tempArtistsList = getArtists();


        int Ti = 0;
        int nextSongID = 0;

        // needed for later
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
                        }

                    }
                }
            }
        }

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

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("getLibrary execute time(ms): "+duration/1000000);

        return songList;
    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> songList = new ArrayList<>();
        //ArrayList<Integer> SongSongID = new ArrayList<>();
        //ArrayList<Integer> artistArtistID = new ArrayList<>();


        try {
            ResultSet rs = statement.executeQuery("SELECT songs.*, album.* FROM songs LEFT JOIN album ON songs.album_album_id = album.album_id;");

            while (rs.next()) {
                songList.add(new Song(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(8),
                        rs.getString(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Failed to get all songs",
                    false
            );
        }

        ArrayList<Integer> SongSongID = new ArrayList<>();
        ArrayList<Integer> artistArtistID = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM songs_has_artist ORDER BY songs_song_id");
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

        ArrayList<Artist> tempArtistsList = getArtists();
        ArrayList<ArrayList<Artist>> artists = new ArrayList<>();

        int Ti = 0;
        int nextSongID = 0;
        System.out.println(songList.size());
        for (int i=0; i<songList.size(); i++) {
            artists.add(new ArrayList<>());
            System.out.println("---------------");
            System.out.println(artists.toString());
            System.out.println("i:"+i);

            for (int k=0; k<tempArtistsList.size(); k++) {
                System.out.println(artistArtistID.size() +", Ti: "+ Ti);
                if (tempArtistsList.get(k).getArtistID() == artistArtistID.get(Ti)) {
                    boolean continueLoop = true;
                    System.out.println("test");
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
                        }

                    }
                }
            }
        }
        System.out.println("got here");
        System.out.println(artists.toString());

        for (int i=0; i<songList.size(); i++) {
            songList.get(i).setArtists(artists.get(i));
        }

        System.out.println(songList.toString());
        return songList;
    }
}
