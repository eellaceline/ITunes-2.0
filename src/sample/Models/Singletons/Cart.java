package sample.Models.Singletons;

import sample.Models.Song;

import java.util.ArrayList;

public class Cart {
    private static Cart cart = new Cart();
    private static ArrayList<Song> songList;

    public static Cart getInstance() {
        if (cart == null)
            return cart = new Cart();
        else
            return cart;
    }

    private Cart() {

    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }
}
