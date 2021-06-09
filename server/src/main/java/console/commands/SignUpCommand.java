package console.commands;

import collectionmanager.databasetools.UserChecker;
import musicband.MusicBand;
import network.CurrentUser;

public class SignUpCommand extends AbstractCommand {

    private UserChecker userChecker;

    public SignUpCommand(UserChecker userChecker) {
        super("", "");
        this.userChecker = userChecker;
    }

    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        if (userChecker.checkLogin(currentUser)) {
            commandResponse.setMessage("Login exists");
        } else {
            userChecker.signUpUser(currentUser);
            commandResponse.setMessage("Successful");
        }
        return commandResponse;
    }
}
