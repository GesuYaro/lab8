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

    private final ArrayListManager listManager;
    private final CollectionManager databaseManager;

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
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        try {
            long id = Long.parseLong(firstArgument.trim().split(" ")[0]);
            int rows = databaseManager.removeById(id, currentUser);
            if (rows > 0) {
                listManager.removeById(id);
                commandResponse.setMessage("Removed");
            } else {
                commandResponse.setMessage("Not Removed");
            }
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with deleting element");
        }
        return commandResponse;
    }
}
