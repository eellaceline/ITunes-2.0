package sample.Models;

public class User extends Account {
    private int balance;
    private Library library;
    private boolean isAnAdmin;

    public User(int userID, String userName, String email, String password, int balance, Library library) {
        super(userID,email, userName, password);
        this.balance = balance;
        this.library = library;
    }

    public User(int userID, String userName, String email, String password, int balance, boolean isAnAdmin) {
        super(userID, userName, email, password);
        this.balance = balance;
        this.isAnAdmin = isAnAdmin;
    }

    public User(User user) {
        super(user.getUserID(), user.getUserName(), user.getEmail(), user.getPassword());
        this.balance = user.getBalance();
        this.isAnAdmin = user.isAnAdmin();
        this.library = user.getLibrary();
    }

    public boolean isAnAdmin() {
        return isAnAdmin;
    }

    public void setAnAdmin(boolean anAdmin) {
        isAnAdmin = anAdmin;
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
