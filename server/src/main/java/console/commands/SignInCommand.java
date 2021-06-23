package console.commands;

import collectionmanager.databasetools.UserChecker;
import musicband.MusicBand;
import network.CurrentUser;

public class SignInCommand extends AbstractCommand {

    private final UserChecker userChecker;

    public SignInCommand(UserChecker userChecker) {
        super("", "");
        this.userChecker = userChecker;
    }

    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        if (userChecker.checkUser(currentUser)) {
            commandResponse.setMessage("Successful");
        } else {
            commandResponse.setMessage("Unsuccessful");
        }
        return commandResponse;
    }
}
