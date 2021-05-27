package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
import collectionmanager.databasetools.DatabaseException;
import console.exсeptions.NoArgumentFoundException;
import musicband.MusicBand;

import java.sql.SQLException;

/**
 * Класс команды remove_by_id, удаляющей объект по его id
 */
public class RemoveByIdCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public RemoveByIdCommand(ArrayListManager listManager, CollectionManager databaseManager) {
        super("remove_by_id id", "remove an item from the collection by its id");
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
            long id = Long.parseLong(firstArgument.trim().split(" ")[0]);
            int rows = databaseManager.removeById(id, currentUser);
            if (rows > 0) {
                listManager.removeById(id);
            }
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with deleting element");
        }
        return new CommandResponse(CommandCode.DEFAULT);
    }
}
