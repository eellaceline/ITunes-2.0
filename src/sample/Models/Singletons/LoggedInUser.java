package sample.Models.Singletons;

import sample.Models.User;

public class LoggedInUser {
    private static LoggedInUser loggedInUser = new LoggedInUser();
    private User user;

    public static LoggedInUser getInstance() {
        if (loggedInUser == null)
            return loggedInUser = new LoggedInUser();
        else
            return loggedInUser;
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
