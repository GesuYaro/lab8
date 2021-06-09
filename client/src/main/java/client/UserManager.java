package client;

import network.CurrentUser;

public class UserManager {

    private CurrentUser user;

    public UserManager(CurrentUser user) {
        this.user = user;
    }

    public UserManager() {
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }
}
