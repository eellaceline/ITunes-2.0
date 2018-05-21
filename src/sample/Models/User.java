package sample.Models;

public class User extends Account {
    private int balance;
    private Library library;
    private boolean isAnAdmin;

    public User(int userID, String userName, String password, String email, int balance, Library library, boolean isAnAdmin) {
        super(userID, userName, password, email);
        this.balance = balance;
        this.library = library;
        this.isAnAdmin = isAnAdmin;
    }

    public User(int userID, String userName, String password, String email, int balance, boolean isAnAdmin) {
        super(userID, userName, password, email);
        this.balance = balance;
        this.isAnAdmin = isAnAdmin;
    }

    public User(User user) {
        super(user.getUserID(), user.getUserName(), user.getEmail(), user.getPassword());
        this.balance = user.getBalance();
        this.library = getLibrary();
        this.isAnAdmin = user.isAnAdmin();
    }

    @Override
    public String toString() {
        return "User{" +
                "balance=" + balance +
                ", library=" + library +
                ", isAnAdmin=" + isAnAdmin +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int balance){
        this.balance+=balance;
    }

    public void reduceBalance(int balance) {
        this.balance-=balance;
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

    public boolean isAnAdmin() {
        return isAnAdmin;
    }

    public void setAnAdmin(boolean anAdmin) {
        isAnAdmin = anAdmin;
    }
}
