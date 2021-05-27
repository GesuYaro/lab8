package console.commands;


import console.Writer;
import musicband.MusicBand;
import network.CurrentUser;

import java.util.HashMap;

/**
 * Класс команды help, выводящей справку по командам
 */
public class HelpCommand extends AbstractCommand {
    private HashMap<String, Command> commands;
    private Writer writer;

    /**
     * @param commands Мапка с командами
     */
    public HelpCommand(HashMap<String, Command> commands) {
        super("help","display help for available commands");
        this.commands = commands;
    }

    /**
     * Конструктор для того, чтобы положить объект HelpCommand в команду help
     */
    public HelpCommand() {
        super("help","display help for available commands");
    }

    /**
     * @param commands
     */
    public void setCommands(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser) {
        CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
        String description = "Commands:\n";
        for (String key : commands.keySet()) {
            Command command;
            command = commands.get(key);
            description += command.getName() + " : " + command.getDescription() + "\n";
        }
        commandResponse.setMessage(description);

        return commandResponse;
    }
}
