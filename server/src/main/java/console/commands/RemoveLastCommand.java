package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
import collectionmanager.databasetools.DatabaseException;
import musicband.MusicBand;

import java.sql.SQLException;

/**
 * Класс команды remove_last, удаляющей последний элемент
 */
public class RemoveLastCommand extends AbstractCommand {

    private final ArrayListManager listManager;
    private final CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public RemoveLastCommand(ArrayListManager listManager, CollectionManager databaseManager) {
        super("remove_last", "remove the last item from the collection");
        this.listManager = listManager;
        this.databaseManager = databaseManager;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT;
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        try {
            int rows = databaseManager.removeLast(currentUser);
            if (rows > 0) {
                listManager.removeLast();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with deleting element");
        }
        return new CommandResponse(CommandCode.DEFAULT, "Removed");
    }
}
