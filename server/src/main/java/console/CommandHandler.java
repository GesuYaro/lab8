package console;

import console.exсeptions.NoSuchCommandException;
import console.commands.*;
import network.Request;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Класс обработчика команд, он же Invoker
 */
public class CommandHandler {

    private volatile HistoryStorage historyStorage;
    private HashMap<String,Command> commands;
    private ExecutorService executorService;

    /**
     * @param commands Мапа с объектами команд, которые нужно будет исполнять, каждый объект должен реализовывать интерфейс Command
     * @param historyStorage Хранитель истории
     */
    public CommandHandler(HashMap<String, Command> commands, HistoryStorage historyStorage){
        this.commands = commands;
        this.historyStorage = historyStorage;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    /**
     * @param commands Словарь с командами
     */
    public void setCommands(HashMap<String, Command> commands) {
        this.commands = commands;
    }


    /**
     * @return Массив строк с названиями команд без аргументов
     */
    public String[] getCommandHistory() {
        return historyStorage.getCommandHistory();
    }

    /**
     * Исполняет программу
     * @param request Запрос
     * @return CommandCode в зависимости от результата выполнения команды
     */
    public Future<CommandResponse> execute(Request request) throws NoSuchCommandException {
        Callable<CommandResponse> callable = new Callable<CommandResponse>() {
            @Override
            public CommandResponse call() {
                if(commands.containsKey(request.getCommand())) {
                    historyStorage.addToCommandHistory(request.getCommand());
                    return commands.get(request.getCommand())
                            .execute(request.getFirstArg() != null ? request.getFirstArg() : "", request.getMusicBand(), request.getUser());
                }
                else {
                    CommandResponse commandResponse = new CommandResponse(CommandCode.NO_SUCH_COMMAND);
                    commandResponse.setMessage(new NoSuchCommandException(request.getCommand()).getMessage());
                    return commandResponse;
                }
            }
        };
         return executorService.submit(callable);
    }


    /**
     * @return Словарь с командами, которые использует CommandHandler
     */
    public HashMap<String,Command> getCommands() {
        return commands;
    }

    /**
     * Класс, хранящий историю  команд, которые вызвал пользователь
     */
    public static class HistoryStorage {
        private static String[] commandHistory = new String[7];

        /**
         * @return Историю команд
         */
        public String[] getCommandHistory() {
            return commandHistory;
        }

        /**
         * Добавляет команду в историю
         * @param command
         */
        private void addToCommandHistory(String command) {
            for (int i = commandHistory.length-2; i >= 0; i--) {
                commandHistory[i+1] = commandHistory[i];
            }
            commandHistory[0] = command;
        }
    }
}
