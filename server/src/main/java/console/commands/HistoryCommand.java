package console.commands;

import console.CommandHandler;
import console.Writer;
import musicband.MusicBand;
import network.CurrentUser;


/**
 * Класс команды history, выводящей историю команд
 */
public class HistoryCommand extends AbstractCommand {
    private CommandHandler.HistoryStorage historyStorage;

    /**
     * @param historyStorage Объект класса, хранящего историю команд
     */
    public HistoryCommand(CommandHandler.HistoryStorage historyStorage) {
        super("history", "print the last 7 commands (without their arguments)");
        this.historyStorage = historyStorage;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        String[] history = historyStorage.getCommandHistory();
        String historyInLine = "";
        for (String h : history) {
            if (h != null) historyInLine = h + "\n" + historyInLine;
        }
        commandResponse.setMessage(historyInLine);
        return commandResponse;
    }
}
