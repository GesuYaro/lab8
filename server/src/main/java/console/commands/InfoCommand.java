package console.commands;

import collectionmanager.ArrayListManager;
import console.Writer;
import musicband.MusicBand;

/**
 * Класс команды info, выводящей информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    private Writer writer;
    private ArrayListManager listManager;

    /**
     * @param writer Объект класса, выводящего в консоль
     * @param listManager Менеджер коллекции
     */
    public InfoCommand(Writer writer, ArrayListManager listManager) {
        super("info", "print information about the collection (type, date of initialization, number of elements, etc.) to standard output");
        this.writer = writer;
        this.listManager = listManager;
    }


    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) {
        writer.write(listManager.info());
        return CommandCode.DEFAULT;
    }
}
