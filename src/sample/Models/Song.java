package sample.Models;

import java.util.ArrayList;

public class Song {
    private int songID;
    private String songName;
    private int length;
    private ArrayList<Artist> artists;
    private String genre;
    private int price;

    public Song(int songID, String songName, int length, ArrayList<Artist> artists, String genre, int price) {
        this.songID = songID;
        this.songName = songName;
        this.length = length;
        this.artists = artists;
        this.genre = genre;
        this.price = price;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
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
}
