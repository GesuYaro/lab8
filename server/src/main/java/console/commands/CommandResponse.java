package console.commands;

import musicband.MusicBand;

import java.util.ArrayList;

public class CommandResponse {
    private CommandCode commandCode;
    private ArrayList<MusicBand> arrayList;
    private String message;

    public CommandResponse() {
    }

    public CommandResponse(CommandCode commandCode) {
        this.commandCode = commandCode;
    }

    public CommandCode getCommandCode() {
        return commandCode;
    }

    public ArrayList<MusicBand> getArrayList() {
        return arrayList;
    }

    public void setCommandCode(CommandCode commandCode) {
        this.commandCode = commandCode;
    }

    public void setArrayList(ArrayList<MusicBand> arrayList) {
        this.arrayList = arrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
