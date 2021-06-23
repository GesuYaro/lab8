package network;

import musicband.MusicBand;

import java.io.Serializable;

public class Request implements Serializable {

    private final String command;
    private final String firstArg;
    private final MusicBand musicBand;
    private final CurrentUser currentUser;

    public Request(String command, String firstArg, MusicBand musicBand, CurrentUser currentUser) {
        this.command = command;
        this.firstArg = firstArg;
        this.musicBand = musicBand;
        this.currentUser = currentUser;
    }

    public String getCommand() {
        return command;
    }

    public String getFirstArg() {
        return firstArg;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public CurrentUser getUser() {
        return currentUser;
    }
}
