package sample.Models;

public class User extends Account {
    private int balance;
    private Library library;

    public User(String userID, String userName, String password, int balance, Library library) {
        super(userID, userName, password);
        this.balance = balance;
        this.library = library;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void addSong(Song song) {
        this.library.addSong(song);
    }


}
