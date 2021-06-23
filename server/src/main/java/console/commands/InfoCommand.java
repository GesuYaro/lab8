package console.commands;

import collectionmanager.ArrayListManager;
import console.Writer;
import musicband.MusicBand;
import network.CurrentUser;

/**
 * Класс команды info, выводящей информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    private final ArrayListManager listManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public InfoCommand(ArrayListManager listManager) {
        super("info", "print information about the collection (type, date of initialization, number of elements, etc.) to standard output");
        this.listManager = listManager;
    }


    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        commandResponse.setMessage(listManager.info());
        return commandResponse;
    }
}
