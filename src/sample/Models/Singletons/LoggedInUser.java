package sample.Models.Singletons;

import sample.Models.User;

public class LoggedInUser {
    private static LoggedInUser ourInstance = new LoggedInUser();
    private User user;

    public static LoggedInUser getInstance() {
        if (ourInstance == null)
            return ourInstance = new LoggedInUser();
        else
            return ourInstance;
    }

    private LoggedInUser() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
