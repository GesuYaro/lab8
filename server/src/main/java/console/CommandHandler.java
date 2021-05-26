package console;

import console.exсeptions.NoSuchCommandException;
import console.commands.*;
import network.Request;

import java.util.HashMap;

/**
 * Класс обработчика команд, он же Invoker
 */
public class CommandHandler {

    private HistoryStorage historyStorage;
    private HashMap<String,Command> commands;

    /**
     * @param commands Мапа с объектами команд, которые нужно будет исполнять, каждый объект должен реализовывать интерфейс Command
     * @param historyStorage Хранитель истории
     */
    public CommandHandler(HashMap<String, Command> commands, HistoryStorage historyStorage){
        this.commands = commands;
        this.historyStorage = historyStorage;
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
    public CommandCode execute(Request request) throws NoSuchCommandException {
        if(commands.containsKey(request.getCommand())) {
            historyStorage.addToCommandHistory(request.getCommand());
            return commands.get(request.getCommand())
                    .execute(request.getFirstArg() != null ? request.getFirstArg() : "", request.getMusicBand(), request.getUser());
        }
        else {
            throw new NoSuchCommandException(request.getCommand());
        }
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
