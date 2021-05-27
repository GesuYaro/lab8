package console.commands;

import collectionmanager.ArrayListManager;
import musicband.MusicBand;
import network.CurrentUser;
import server.ServerWriter;


/**
 * Класс команды show, показывающей элементы коллекции
 */
public class ShowCommand extends AbstractCommand {

    private ArrayListManager listManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public ShowCommand(ArrayListManager listManager) {
        super("show", "print all elements of the collection in string representation");
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
        commandResponse.setArrayList(listManager.getArrayList());
        return commandResponse;
    }
}
