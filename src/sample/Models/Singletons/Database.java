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

    private PreparedStatement ps;
    private Statement statement;
    private Connection c;

    private Database() {
        try {
            c = (Connection) DriverManager.getConnection(url);
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
            ps = c.prepareStatement("SELECT * FROM user WHERE username = ?");
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            //ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + userName + "';");

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
            ps = c.prepareStatement("SELECT * FROM user WHERE username = ? AND email = ?");
            ps.setString(1, userName);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();

            //ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + userName + "' AND email = '" + email + "';");

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

    public void addOrder() {


    }

    public boolean changeBalance() {
        try {
            ps = c.prepareStatement("UPDATE user SET balance = ? WHERE user_id = ?");
            ps.setInt(1, LoggedInUser.getInstance().getUser().getBalance());
            ps.setInt(2, LoggedInUser.getInstance().getUser().getUserID());

            int rows = ps.executeUpdate();

            //int rows = statement.executeUpdate("UPDATE user SET balance = " + LoggedInUser.getInstance().getUser().getBalance() + " WHERE user_id = " + LoggedInUser.getInstance().getUser().getUserID());
            if (rows != 0) {
                Handler_Alert.information(
                        "Information",
                        "Balance changed",
                        "Your balance is " + LoggedInUser.getInstance().getUser().getBalance() + ".",
                        false
                );
                return true;
            }
            else {
                Handler_Alert.alert(
                        "Error",
                "Balance not changed",
                "Your balance wasnt changed",
                false
                );

                return false;
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing changeBalance",
                    false
            );

            return false;
        }
    }

    //Update password
    public boolean changePassword(String username, String password ){
        boolean isConfirmed = false;
        System.out.println(username + password);
        String tempPw = Handler_Password.encryption(password);

        //System.out.println("UPDATE user SET password = '" + password + "' WHERE username = '" + username + "'");

        try {
            ps = c.prepareStatement("UPDATE user SET password = ? WHERE username = ?");
            ps.setString(1, tempPw);
            ps.setString(2, username);

            int rows = ps.executeUpdate();

            //int rows = statement.executeUpdate("UPDATE user SET password = '" + tempPw + "' WHERE username = '" + username + "'");
            isConfirmed = true;
            System.out.println(rows);
            if (rows != 0){
                Handler_Alert.information(
                        "Information",
                        "Password changed",
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
        }catch (SQLException ex){
            Handler_Alert.alert(
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
            ps = c.prepareStatement("UPDATE user SET username = ? WHERE username = ?");
            ps.setString(1, newUsername);
            ps.setString(2, username);

            int rows = ps.executeUpdate();

            //int rows = statement.executeUpdate("UPDATE user SET username = '" + newUsername + "' WHERE username = '" + username + "'");
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
            ps = c.prepareStatement("UPDATE songs, artist SET price = ? WHERE songs.songName = ? AND artist.artistName = ?;");
            ps.setInt(1, newPrice);
            ps.setString(2, songName);
            ps.setString(3, artistName);

            int rows = ps.executeUpdate();

            //int rows = statement.executeUpdate("UPDATE songs, artist SET price = '" + newPrice + "' WHERE songs.songName = '" + songName +"' AND artist.artistName = '" + artistName + "';");
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

    public void addSongsForUser(ArrayList<Integer> songID) {

        String where = "";
        for (int i=0; i<songID.size(); i++) {
            if (i == songID.size() - 1)
                where += "(" + LoggedInUser.getInstance().getUser().getUserID() + "," + songID.get(i) + ");";

            else
                where += "(" + LoggedInUser.getInstance().getUser().getUserID() + "," + songID.get(i) + "),";
        }
        System.out.println(where);

        try {
            ps = c.prepareStatement("INSERT INTO user_has_songs(user_user_id, songs_song_id) VALUES ?");
            ps.setString(1, where);

            //ps.executeUpdate();
            statement.executeUpdate("INSERT INTO user_has_songs(user_user_id, songs_song_id) VALUES " + where);
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error executing query addSongForUser",
                    false
            );
        }
    }

    // SQL statement in this order: Artist, Album, Song
    public void addSong(String songName, String songDuration, int price, String genreName, String albumName, String artistName) {
        long startTime = System.nanoTime();

        Artist artist = null;
        boolean foundArtist = false;

        try {
            ps = c.prepareStatement("SELECT * FROM artist WHERE artistName = ?");
            ps.setString(1, artistName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                foundArtist = true;
                artist = new Artist(rs.getInt(1), rs.getString(2));
            }
        }
        catch (SQLException ex) {

        }

        //TODO needs check if the artist already exists
        if (!foundArtist) {
            try {
                statement.executeUpdate("INSERT INTO artist(artistName) VALUES ('" + artistName + "')");
            } catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error executing when adding artist",
                        false
                );
            }
        }

        boolean foundGenre = false;
        try {
            ps = c.prepareStatement("SELECT * FROM genre WHERE genreName = ?");
            ps.setString(1, genreName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                foundGenre = true;
            }
        }
        catch (SQLException ex) {

        }


        //TODO check DB if genre exists before executing the update
        if (!foundGenre) {
            try {
                statement.executeUpdate("INSERT INTO genre (genreName) VALUES ('" + genreName + "')");
            } catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error executing when adding genre",
                        false
                );
            }
        }

        System.out.println(albumName);
        boolean foundAlbum = false;
        try {
            ps = c.prepareStatement("SELECT * FROM album WHERE albumName = ?");
            ps.setString(1, albumName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                foundAlbum = true;
            }
        }
        catch (SQLException ex) {

        }

        if (!foundAlbum) {
            if (artist == null) {
                try {
                    ps = c.prepareStatement("SELECT * FROM artist WHERE artistName = ?");
                    ps.setString(1, artistName);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        artist = new Artist(rs.getInt(1), rs.getString(2));
                    }
                }
                catch (SQLException ex) {

                }
            }


            System.out.println(artist.getArtistID());
            try {
                ps = c.prepareStatement("INSERT INTO album(albumName,artist_artist_id) VALUES (?,?)");
                ps.setString(1, albumName);
                ps.setInt(2, artist.getArtistID());

                ps.executeUpdate();
            }
            catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error when insert into album",
                        false
                );
            }
        }

        //TODO if statement that checks if a there exists an album or not,
        // if there is no album to be added a secondary update without the album_album_id needs to be made
        if (albumName.equals("")) {
            System.out.println("add song without album");
            try {
                ps = c.prepareStatement("INSERT INTO songs (songName, songDuration, price, genre_genreName) VALUES (?,?,?,?)");
                ps.setString(1, songName);
                ps.setString(2, songDuration);
                ps.setInt(3, price);
                ps.setString(4, genreName);
                //statement.executeUpdate("INSERT INTO songs (songName, songDuration, price, genre_genreName, album_album_id) VALUES ('" + songName +"')");
            } catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error executing when adding song",
                        false
                );
            }
        }
        else {
            System.out.println("add song with album");
            int albumID = 0;
            try {
                ps = c.prepareStatement("SELECT album_id FROM album WHERE albumName = ?");
                ps.setString(1, albumName);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    albumID = rs.getInt(1);
                }
            }
            catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error when selecting album_id",
                        false
                );
            }
            try {
                ps = c.prepareStatement("INSERT INTO songs (songName, songDuration, price, genre_genreName, album_album_id) VALUES (?,?,?,?,?)");
                ps.setString(1, songName);
                ps.setString(2, songDuration);
                ps.setInt(3, price);
                ps.setString(4, genreName);
                ps.setInt(5, albumID);

                ps.executeUpdate();
                //statement.executeUpdate("INSERT INTO songs (songName, songDuration, price, genre_genreName, album_album_id) VALUES ('" + songName +"')");
            } catch (SQLException ex) {
                Handler_Alert.alert(
                        "Error",
                        "SQLException",
                        "Error executing when adding song",
                        false
                );
            }
        }

        int songID = 0;
        try {
            ps = c.prepareStatement("SELECT song_id FROM songs ORDER BY song_id DESC LIMiT 1;");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                songID = rs.getInt(1);
            }
        }
        catch (SQLException ex) {
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Error when getting latest added song_id | row488",
                    false
            );
        }

        try {
            ps = c.prepareStatement("INSERT INTO songs_has_artist(songs_song_id,artist_artist_id) VALUES (?,?)");
            ps.setInt(1, songID);
            ps.setInt(2, artist.getArtistID());

            ps.executeUpdate();
        }
        catch (SQLException ex) {

        }

        Handler_Alert.information(
                "Info",
                "Added song",
                "Added song to database",
                false
        );

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("addSong execute time(ms): "+duration/1000000);
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

        ArrayList<Artist> tempArtistsList = new ArrayList<>();
        Artist artist = null;

        try {
            ps = c.prepareStatement("SELECT * FROM artist WHERE artist_id IN(SELECT artist_artist_id FROM songs_has_artist WHERE songs_song_id IN(SELECT song_ID FROM songs WHERE songName = ? AND genre_genreName = ?))");
            ps.setString(1, songName);
            ps.setString(2, genreName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                tempArtistsList.add(new Artist(rs.getInt(1), rs.getString(2)));
            }

            // if the result is null jump out of function
            if (tempArtistsList.isEmpty()) {
                System.out.println("no songs");
                return song;
            }

            else {
                for (Artist tempArtist: tempArtistsList) {
                    if (tempArtist.getArtistName().equals(artistName)) {
                        artist = tempArtist;
                    }
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("error finding artist");
        }

        // if artist is null jump out of function
        if (artist == null) {
            return song;
        }

        try {
            ps = c.prepareStatement("SELECT * FROM songs WHERE song_id IN(SELECT songs_song_id FROM songs_has_artist WHERE songs_song_id IN(SELECT song_ID FROM songs WHERE songName = ? AND genre_genreName = ?) AND artist_artist_id = ?)");
            ps.setString(1, songName);
            ps.setString(2, genreName);
            ps.setInt(3, artist.getArtistID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                song = new Song(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        }
        catch (SQLException ex) {

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


    // a version that prints most values in console
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
    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM user");

            while(rs.next()) {
                userList.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getBoolean(6)));

            }
            System.out.println(userList.toString());

        }catch (SQLException ex){
            Handler_Alert.alert(
                    "Error",
                    "SQLException",
                    "Failed to get all users",
                    false
            );

        }return userList;
    }
    public boolean updatePassword(String email, String password) {
        boolean isConfirmed = false;
        String tempPw;
        tempPw = Handler_Password.encryption(password);
        System.out.println(tempPw);

        try {
            int rows = statement.executeUpdate("UPDATE user set password = '" + tempPw + "' WHERE email = '" + email + "' ");
            isConfirmed = true;
            System.out.println(rows);

        } catch (SQLException ex) {
            Handler_Alert.alert("Error",
                    "SQLException",
                    "Error executing an update to changing password",
                    false
            );
        }
        return isConfirmed;
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
