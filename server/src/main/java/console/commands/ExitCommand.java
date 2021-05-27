package console.commands;

import musicband.MusicBand;
import network.CurrentUser;

/**
 * Класс команды exit, осуществяющей выход из программы
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "finish the command (without saving)");
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.EXIT
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        return new CommandResponse(CommandCode.EXIT);
    }
}
