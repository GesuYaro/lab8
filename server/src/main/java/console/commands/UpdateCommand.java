package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseException;
import collectionmanager.databasetools.DatabaseManager;
import console.exсeptions.NoArgumentFoundException;
import console.exсeptions.NoSuchIdException;
import musicband.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private MusicBandFieldsChecker fieldsChecker;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     * @param fieldsChecker Считыватель полей
     */
    public UpdateCommand(ArrayListManager listManager, MusicBandFieldsChecker fieldsChecker, CollectionManager databaseManager) {
        super("update id {element}", "update the value of the collection item whose id is equal to the given");
        this.listManager = listManager;
        this.fieldsChecker = fieldsChecker;
        this.databaseManager = databaseManager;
    }

    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) throws NoArgumentFoundException, NoSuchIdException{
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
                int rows = databaseManager.replace(id, requestedMusicBand);
                if (rows > 0) {
                    listManager.replace(id, requestedMusicBand);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Problem with updating element");
            }
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException();
        }
        return CommandCode.DEFAULT;
    }
}
