package console.commands;

import collectionmanager.ArrayListManager;
import console.Writer;
import console.exсeptions.NoArgumentFoundException;
import musicband.MusicBand;
import musicband.MusicGenre;
import network.CurrentUser;

/**
 * Класс для команды count_greater_than_genre, считающей количество элементов, значение поля genre которых больше заданного
 */
public class CountGreaterThanGenreCommand extends AbstractCommand{
    private ArrayListManager listManager;
    private Writer writer;

    /**
     * @param writer Объект класса, осуществляющий вывод в консоль
     * @param listManager Менеджер коллекции
     */
    public CountGreaterThanGenreCommand(Writer writer, ArrayListManager listManager) {
        super("count_greater_than_genre", "print the number of elements whose genre field value is greater than the given one");
        this.listManager = listManager;
        this.writer = writer;
    }

    /**
     * @param firstArgument Значение поля genre
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     * @throws NoArgumentFoundException
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) throws NoArgumentFoundException {
        try {
            firstArgument = firstArgument.trim().split(" ")[0].toUpperCase();
            MusicGenre genre = MusicGenre.valueOf(firstArgument);
            long count = listManager.countGreaterThanGenre(genre);
            writer.write(String.valueOf(count));
        } catch (IllegalArgumentException e) {
            if (firstArgument.equals("")) {
                throw new NoArgumentFoundException();
            } else {
                throw new NoArgumentFoundException("Genre " + firstArgument + " not found");
            }
        }
        return CommandCode.DEFAULT;
    }
}
