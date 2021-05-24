package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseException;
import musicband.MusicBand;

import java.sql.SQLException;

/**
 * Класс команды remove_last, удаляющей последний элемент
 */
public class RemoveLastCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private CollectionManager databaseManager;

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
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) {
        try {
            int rows = databaseManager.removeLast();
            if (rows > 0) {
                listManager.removeLast();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with deleting element");
        }
        return CommandCode.DEFAULT;
    }
}
