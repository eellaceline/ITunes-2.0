package sample.Models;

import java.util.ArrayList;

public class Song {
    private int songID;
    private String songName;
    private String length;
    private int price;
    private ArrayList<Artist> artists;
    private String genre;

    // hopefully temporary
    private String artistNames;

    public Song(int songID, String songName, String length, int price, ArrayList<Artist> artists, String genre) {
        this.songID = songID;
        this.songName = songName;
        this.length = length;
        this.artists = artists;
        this.genre = genre;
        this.price = price;
        this.artistNames = combineArtistName();
    }

    public Song(Song song) {
        this.songID = song.getSongID();
        this.songName = song.getSongName();
        this.length = song.getLength();
        this.artists = song.getArtists();
        this.genre = song.getGenre();
        this.price = song.getPrice();
        this.artistNames = combineArtistName();
    }

    public String combineArtistName() {
        String finalString = "";
        for (int i=0; i<artists.size(); i++) {
            if (artists.size() == 1)
                finalString += artists.get(i).toString();
            else
                finalString += artists.get(i).toString() + ", ";
        }
        return finalString;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID=" + songID +
                ", songName='" + songName + '\'' +
                ", length='" + length + '\'' +
                ", price=" + price +
                ", artists=" + artists +
                ", genre='" + genre + '\'' +
                ", artistNames='" + artistNames + '\'' +
                '}';
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getArtistNames() {
        return artistNames;
    }
}
