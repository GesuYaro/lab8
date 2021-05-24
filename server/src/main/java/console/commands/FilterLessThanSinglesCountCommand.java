package console.commands;

import collectionmanager.ArrayListManager;
import musicband.MusicBand;
import server.ServerWriter;
import console.exсeptions.NoArgumentFoundException;

/**
 * Класс команды filter_less_than_singles_count, выводящей элементы, поле singlesCount которых меньше, чем задано
 */
public class FilterLessThanSinglesCountCommand extends AbstractCommand {
    private ArrayListManager listManager;
    private ServerWriter writer;

    /**
     * @param writer Объект класса, осуществляющего вывод в консоль
     * @param listManager Менеджер коллекции
     */
    public FilterLessThanSinglesCountCommand(ServerWriter writer, ArrayListManager listManager) {
        super("filter_less_than_singles_count singles_count", "display elements whose singlesCount field value is less than the specified one");
        this.listManager = listManager;
        this.writer = writer;
    }

    /**
     * @param firstArgument singles_count
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     * @throws NoArgumentFoundException
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) throws NoArgumentFoundException {
        try {
            Integer singlesCount = Integer.parseInt(firstArgument.trim().split(" ")[0].trim());
            writer.write(listManager.filterLessThanSinglesCount(singlesCount));
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException("Argument not found. Enter integer firstArgument");
        }

        return CommandCode.DEFAULT;
    }
}
