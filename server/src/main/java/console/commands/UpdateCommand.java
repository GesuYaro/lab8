package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
import collectionmanager.databasetools.DatabaseException;
import console.exсeptions.NoArgumentFoundException;
import console.exсeptions.NoSuchIdException;
import musicband.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public UpdateCommand(ArrayListManager listManager, CollectionManager databaseManager) {
        super("update id {element}", "update the value of the collection item whose id is equal to the given");
        this.listManager = listManager;
        this.databaseManager = databaseManager;
    }

    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) throws NoArgumentFoundException, NoSuchIdException{
        CommandResponse response = new CommandResponse(CommandCode.DEFAULT);
        try {
            if (firstArgument == null) {
                firstArgument = "";
            }
            Long arg = Long.parseLong(firstArgument.trim().split(" ")[0]);
            MusicBand updatingMusicBand = listManager.getById(arg);
            long id = updatingMusicBand.getId();
            LocalDate creationDate = updatingMusicBand.getCreationDate();
            requestedMusicBand.setCreationDate(creationDate);
            requestedMusicBand.setId(id);
            try {
                int rows = databaseManager.replace(id, requestedMusicBand, currentUser);
                if (rows > 0) {
                    listManager.replace(id, requestedMusicBand);
                    response.setMessage("Updated");
                } else {
                    response.setMessage("Can't update");
                }
            } catch (SQLException e) {
                throw new DatabaseException("Problem with updating element");
            }
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException();
        }
        return response;
    }
}
