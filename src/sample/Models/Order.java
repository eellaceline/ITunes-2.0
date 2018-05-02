package sample.Models;

import sample.Models.Singletons.LoggedInUser;

import java.util.ArrayList;

public class Order {
    private ArrayList<Song> songs;
    private int orderNumber;
    private String orderDate;

    public Order(ArrayList<Song> songs, int orderNumber, String orderDate) {
        this.songs = songs;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
    }

    public boolean calcBalance() {
        boolean successfullTransaction = false;
        int totalPrice = 0;
        for (Song song: songs) {
            totalPrice += song.getPrice();
        }
        // checking if user has enough balance to purchase
        if (LoggedInUser.getInstance().getUser().getBalance() >= totalPrice) {
            // in short, setBalance(getBalance() - totalPrice);
            LoggedInUser.getInstance().getUser().setBalance(LoggedInUser.getInstance().getUser().getBalance()-totalPrice);
            successfullTransaction = true;
        }
        return successfullTransaction;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
