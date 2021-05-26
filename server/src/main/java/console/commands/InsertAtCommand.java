package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
import collectionmanager.databasetools.DatabaseException;
import musicband.InputValueException;
import console.exсeptions.NoArgumentFoundException;
import musicband.*;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Класс команды insert_at, добаляющего новый объект в заданную позицию
 */
public class InsertAtCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private MusicBandFieldsChecker fieldsChecker;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     * @param fieldsChecker Считыватель полей
     */
    public InsertAtCommand(ArrayListManager listManager, MusicBandFieldsChecker fieldsChecker, CollectionManager databaseManager) {
        super("insert_at index {element}", "add a new item at a given position");
        this.listManager = listManager;
        this.fieldsChecker = fieldsChecker;
        this.databaseManager = databaseManager;
    }

    /**
     * @param firstArgument id элемента
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) throws InputValueException, IndexOutOfBoundsException, NoArgumentFoundException {
        try {
            int index = Integer.parseInt(firstArgument
                    .trim()
                    .split(" ")[0]
            );
            if ( !(index > listManager.getArrayList().size() - 1 || index < 0) ) {
                LocalDate creationDate = LocalDate.now();
                try {
                    long id = databaseManager.getNewMaxId(currentUser);
                    requestedMusicBand.setId(id);
                    requestedMusicBand.setCreationDate(creationDate);
                } catch (SQLException e) {
                    throw new DatabaseException("Problem with getting new Id");
                }
                try {
                    int rows = databaseManager.add(requestedMusicBand, currentUser);
                    if (rows > 0) {
                        listManager.insertAtIndex(index, requestedMusicBand);
                    }
                } catch (SQLException e) {
                    throw new DatabaseException("Problem with adding element");
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException();
        }
        return CommandCode.DEFAULT;
    }
}
