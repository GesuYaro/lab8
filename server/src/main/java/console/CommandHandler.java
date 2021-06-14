package console;

import collectionmanager.databasetools.AuthenticationException;
import collectionmanager.databasetools.DatabaseException;
import collectionmanager.databasetools.UserChecker;
import console.exсeptions.NoArgumentFoundException;
import console.exсeptions.NoSuchCommandException;
import console.commands.*;
import console.exсeptions.NoSuchIdException;
import console.exсeptions.NotEnoughArgumentsException;
import musicband.InputValueException;
import network.Request;

import java.nio.channels.SocketChannel;
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
    private UserChecker userChecker;

    /**
     * @param commands Мапа с объектами команд, которые нужно будет исполнять, каждый объект должен реализовывать интерфейс Command
     * @param historyStorage Хранитель истории
     */
    public CommandHandler(HashMap<String, Command> commands, HistoryStorage historyStorage, UserChecker userChecker){
        this.commands = commands;
        this.historyStorage = historyStorage;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        this.userChecker = userChecker;
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
    public Future<ExecutorResponse> execute(Request request, SocketChannel socketChannel) {
        Callable<ExecutorResponse> callable = new Callable<ExecutorResponse>() {
            @Override
            public ExecutorResponse call() {
                try {
                    if ((userChecker.checkUser(request.getUser()) || (request.getCommand() != null) && request.getCommand().trim().equals("sign_up"))) {
                        if (commands.containsKey(request.getCommand())) {
                            if (!request.getCommand().equals("show") && !request.getCommand().equals("sign_in") && !request.getCommand().equals("sign_up")) {
                                historyStorage.addToCommandHistory(request.getCommand());
                            }
                            return new ExecutorResponse(commands.get(request.getCommand())
                                    .execute(request.getFirstArg() != null ? request.getFirstArg() : "", request.getMusicBand(), request.getUser()),
                                    socketChannel);
                        } else {
                            CommandResponse commandResponse = new CommandResponse(CommandCode.ERROR);
                            commandResponse.setMessage(new NoSuchCommandException(request.getCommand()).getMessage());
                            return new ExecutorResponse(commandResponse, socketChannel);
                        }
                    } else {
                        CommandResponse commandResponse = new CommandResponse(CommandCode.ERROR);
                        commandResponse.setMessage("You have entered wrong password");
                        return new ExecutorResponse(commandResponse, socketChannel);
                    }
                } catch (NoArgumentFoundException | InputValueException | IndexOutOfBoundsException | NoSuchIdException |
                        NotEnoughArgumentsException | DatabaseException | AuthenticationException e) {
                    CommandResponse commandResponse = new CommandResponse(CommandCode.ERROR);
                    commandResponse.setMessage(e.getMessage());
                    return new ExecutorResponse(commandResponse, socketChannel);
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
        private synchronized void addToCommandHistory(String command) {
            for (int i = commandHistory.length-2; i >= 0; i--) {
                commandHistory[i+1] = commandHistory[i];
            }
            commandHistory[0] = command;
        }
    }
}
