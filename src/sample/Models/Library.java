package sample.Models;

import java.util.ArrayList;

public class Library {
    ArrayList<Song> songs;

        public Library(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }
}
