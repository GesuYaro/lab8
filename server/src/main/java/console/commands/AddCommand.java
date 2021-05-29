package console.commands;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import network.CurrentUser;
import collectionmanager.databasetools.DatabaseException;
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
    private CollectionManager databaseManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public AddCommand(ArrayListManager listManager, CollectionManager databaseManager) {
        super("add {element}", "add new element into collection");
        this.listManager = listManager;
        this.databaseManager = databaseManager;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     * @throws InputValueException
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) throws InputValueException, DatabaseException, NotEnoughArgumentsException {
        if (requestedMusicBand == null) {
            throw new NotEnoughArgumentsException();
        }
        LocalDate creationDate = LocalDate.now();

        try {
            long id = databaseManager.getNewMaxId(currentUser);
            requestedMusicBand.setCreationDate(creationDate);
            requestedMusicBand.setId(id);
        } catch (SQLException e) {
            throw new DatabaseException("Problem with getting new Id");
        }
        try {
            int rows = databaseManager.add(requestedMusicBand, currentUser);
            if (rows > 0) {
                listManager.add(requestedMusicBand);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with adding element");
        }
        return new CommandResponse(CommandCode.DEFAULT);

    }
}
