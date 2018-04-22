package sample.Models;

import java.util.ArrayList;

public class Order_History {
    private User user;
    private ArrayList<Song> songs;
    private int orderNumber;
    private int orderDate;

    public Order_History(User user, ArrayList<Song> songs, int orderNumber, int orderDate) {
        this.user = user;
        this.songs = songs;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
    }

    public boolean newBalance() {
        boolean successfullTransaction = false;
        int totalPrice = 0;
        for (Song song: songs) {
            totalPrice += song.getPrice();
        }
        // checking if user has enough balance to purchase
        if (user.getBalance() >= totalPrice) {
            user.setBalance(user.getBalance()-totalPrice);
            successfullTransaction = true;
        }
        return successfullTransaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(int orderDate) {
        this.orderDate = orderDate;
    }
}
