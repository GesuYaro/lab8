package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
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
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        try {
            int rows = databaseManager.clear(currentUser);
            if (rows > 0) {
                listManager.clear();
                listManager.setArrayList(databaseManager.getArrayList());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with clearing collection");
        }
        return new CommandResponse(CommandCode.DEFAULT, "Cleared");
    }
}
