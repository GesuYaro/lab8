package server;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseConnector;
import collectionmanager.databasetools.DatabaseManager;
import console.CommandHandler;
import console.commands.*;
import musicband.MusicBandFieldsChecker;
import org.slf4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Console {

    private volatile ArrayListManager arrayListManager;
    private Connector connector;
    private Logger logger;
    private volatile CollectionManager databaseManager;
    private volatile CommandHandler commandHandler;

    public Console(ArrayListManager arrayListManager, Connector connector, Logger logger, CollectionManager databaseManager) {
        this.arrayListManager = arrayListManager;
        this.connector = connector;
        this.logger = logger;
        this.databaseManager = databaseManager;
    }

    public void run(boolean singleIterationMode) {

        CommandHandler.HistoryStorage historyStorage = new CommandHandler.HistoryStorage();
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("info", new InfoCommand(arrayListManager));
        commands.put("show", new ShowCommand(arrayListManager));
        commands.put("add", new AddCommand(arrayListManager, databaseManager));
        commands.put("update", new UpdateCommand(arrayListManager, databaseManager));
        commands.put("remove_by_id", new RemoveByIdCommand(arrayListManager, databaseManager));
        commands.put("clear", new ClearCommand(arrayListManager, databaseManager));
        commands.put("exit", new ExitCommand());
        commands.put("insert_at", new InsertAtCommand(arrayListManager, databaseManager));
        commands.put("remove_last", new RemoveLastCommand(arrayListManager, databaseManager));
        commands.put("history", new HistoryCommand(historyStorage));
        commands.put("count_greater_than_genre", new CountGreaterThanGenreCommand(arrayListManager));
        commands.put("filter_less_than_singles_count", new FilterLessThanSinglesCountCommand(arrayListManager));
        commands.put("print_field_descending_genre", new PrintFieldsDescendingGenreCommand(arrayListManager));
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand());
        commandHandler = new CommandHandler(commands, historyStorage);

        logger.info("App is turned on.");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        do {
            SocketChannel socketChannel = null;
            try {
                while (socketChannel == null) {
                    socketChannel = connector.getSocketChannel();
                }
            } catch (IOException e) {
                logger.warn("Can not connect");
            }
            SocketChannel finalSocketChannel = socketChannel;
            RecursiveAction action = new RecursiveAction() {
                @Override
                protected void compute() {
                    ServerWriter writer = new ServerWriter(finalSocketChannel);
                    RequestReader requestReader = new RequestReader(finalSocketChannel, ByteBuffer.allocate(1024));
                    RequestHandler requestHandler = new RequestHandler(commandHandler, requestReader, writer, logger);
                    requestHandler.run();
                }
            };
            forkJoinPool.execute(action);

//            logger.info("Client disconnected");
        } while (!singleIterationMode);
    }



}
