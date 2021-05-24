package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseException;
import collectionmanager.databasetools.DatabaseManager;
import musicband.InputValueException;
import console.exсeptions.NotEnoughArgumentsException;
import musicband.*;

import java.sql.SQLException;
import java.time.LocalDate;


/**
 * Класс команды add, добавляющей элемент в коллекцию
 */
public class AddCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private MusicBandFieldsChecker checker;
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     * @param reader Считыватель полей элемента коллекции
     */
    public AddCommand(ArrayListManager listManager, MusicBandFieldsChecker reader, CollectionManager databaseManager) {
        super("add {element}", "add new element into collection");
        this.listManager = listManager;
        this.checker = reader;
        this.databaseManager = databaseManager;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     * @throws InputValueException
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) throws InputValueException, DatabaseException {
        if (requestedMusicBand == null) throw new NotEnoughArgumentsException();
        LocalDate creationDate = LocalDate.now();
        listManager.increaseMaxId();
        try {
            long id = databaseManager.getNewMaxId();
            requestedMusicBand.setCreationDate(creationDate);
            requestedMusicBand.setId(id);
        } catch (SQLException e) {
            throw new DatabaseException("Problem with getting new Id");
        }
        try {
            int rows = databaseManager.add(requestedMusicBand);
            if (rows > 0) {
                listManager.add(requestedMusicBand);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with adding element");
        }
        return CommandCode.DEFAULT;

    }
}
