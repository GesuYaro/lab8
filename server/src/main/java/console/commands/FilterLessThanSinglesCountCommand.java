package console.commands;

import collectionmanager.ArrayListManager;
import musicband.MusicBand;
import network.CurrentUser;
import console.exсeptions.NoArgumentFoundException;

/**
 * Класс команды filter_less_than_singles_count, выводящей элементы, поле singlesCount которых меньше, чем задано
 */
public class FilterLessThanSinglesCountCommand extends AbstractCommand {
    private final ArrayListManager listManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public FilterLessThanSinglesCountCommand(ArrayListManager listManager) {
        super("filter_less_than_singles_count singles_count", "display elements whose singlesCount field value is less than the specified one");
        this.listManager = listManager;
    }

    /**
     * @param firstArgument singles_count
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     * @throws NoArgumentFoundException
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) throws NoArgumentFoundException {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        try {
            Integer singlesCount = Integer.parseInt(firstArgument.trim().split(" ")[0].trim());
            commandResponse.setArrayList(listManager.filterLessThanSinglesCount(singlesCount));
        } catch (NumberFormatException e) {
            throw new NoArgumentFoundException("Argument not found. Enter integer firstArgument");
        }

        return commandResponse;
    }
}
