package console.commands;

import collectionmanager.ArrayListManager;
import console.Writer;
import musicband.MusicBand;
import musicband.MusicGenre;
import network.CurrentUser;

import java.util.Iterator;

/**
 * Класс команды print_fields_descending_genre, выводящей поле genre всех объектов в убывающем порядке
 */
public class PrintFieldsDescendingGenreCommand extends AbstractCommand {
    private ArrayListManager listManager;

    /**
     * @param listManager Менеджер коллекции
     */
    public PrintFieldsDescendingGenreCommand(ArrayListManager listManager) {
        super("print_field_descending_genre", "display the genre field values of all elements in descending order");
        this.listManager = listManager;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        String str = "";
        if (listManager.getArrayList().size() < 1) {
            str = "Collection is empty";
        } else {
            for (Iterator<MusicBand> it = listManager.sortByGenre().iterator(); it.hasNext(); ) {
                MusicGenre nextGenre = it.next().getGenre();
                if (nextGenre != null) str = nextGenre.name() + "\n" + str;
                else str = "null\n" + str;
            }
        }
        commandResponse.setMessage(str);
        return commandResponse;
    }
}
