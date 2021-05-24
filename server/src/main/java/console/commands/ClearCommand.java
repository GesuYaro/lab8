package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseException;
import musicband.MusicBand;

import java.sql.SQLException;

/**
 * Класс команды clear, очищающей коллекцию
 */
public class ClearCommand extends AbstractCommand {
    private ArrayListManager listManager;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public ClearCommand(ArrayListManager listManager, CollectionManager databaseManager) {
        super("clear", "clear collection");
        this.listManager = listManager;
        this.databaseManager = databaseManager;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) {
        try {
            int rows = databaseManager.clear();
            if (rows > 0) {
                listManager.clear();
                listManager.setArrayList(databaseManager.getArrayList());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with clearing collection");
        }
        return CommandCode.DEFAULT;
    }
}
