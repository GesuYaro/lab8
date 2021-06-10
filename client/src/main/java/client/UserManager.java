package client;

import network.CurrentUser;

public class UserManager {

    private CurrentUser user;

    public UserManager(CurrentUser user) {
        this.user = user;
    }

    public UserManager() {
    }

    public synchronized CurrentUser getUser() {
        return user;
    }

    public synchronized void setUser(CurrentUser user) {
        this.user = user;
    }
}
