package server;

import collectionmanager.ArrayListManager;
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

public class Console {

    private ArrayListManager arrayListManager;
    private Connector connector;
    private Logger logger;

    public Console(ArrayListManager arrayListManager, Connector connector, Logger logger) {
        this.arrayListManager = arrayListManager;
        this.connector = connector;
        this.logger = logger;
    }

    public void run(boolean singleIterationMode) {
        do {
            SocketChannel socketChannel = null;
            try {
                while (socketChannel == null) {
                    socketChannel = connector.getSocketChannel();
                }
//                logger.info("Connected");
            } catch (IOException e) {
                logger.warn("Can not connect");
            }
            ServerWriter writer = new ServerWriter(socketChannel);

            DatabaseManager databaseManager = null;
            DatabaseConnector databaseConnector = new DatabaseConnector("pg", 5432, "studs", "s312764", "pni300");
            try {
                Connection databaseConnection = databaseConnector.getConnection();
                databaseManager = new DatabaseManager(databaseConnection, "admin");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            CommandHandler.HistoryStorage historyStorage = new CommandHandler.HistoryStorage();
            MusicBandFieldsChecker musicBandFieldsChecker = new MusicBandFieldsChecker(reader);
            HashMap<String, Command> commands = new HashMap<>();
            commands.put("info", new InfoCommand(writer, arrayListManager));
            commands.put("show", new ShowCommand(writer, arrayListManager));
            commands.put("add", new AddCommand(arrayListManager, musicBandFieldsChecker, databaseManager));
            commands.put("update", new UpdateCommand(arrayListManager, musicBandFieldsChecker, databaseManager));
            commands.put("remove_by_id", new RemoveByIdCommand(arrayListManager, databaseManager));
            commands.put("clear", new ClearCommand(arrayListManager, databaseManager));
            //commands.put("save", new SaveCommand(arrayListManager, savingFile, writer));
            commands.put("exit", new ExitCommand());
            commands.put("insert_at", new InsertAtCommand(arrayListManager, musicBandFieldsChecker, databaseManager));
            commands.put("remove_last", new RemoveLastCommand(arrayListManager, databaseManager));
            commands.put("history", new HistoryCommand(writer, historyStorage));
            commands.put("count_greater_than_genre", new CountGreaterThanGenreCommand(writer, arrayListManager));
            commands.put("filter_less_than_singles_count", new FilterLessThanSinglesCountCommand(writer, arrayListManager));
            commands.put("print_field_descending_genre", new PrintFieldsDescendingGenreCommand(writer, arrayListManager));
            commands.put("help", new HelpCommand(writer, commands));
            commands.put("exit", new ExitCommand());
            CommandHandler commandHandler = new CommandHandler(commands, historyStorage);


            RequestReader requestReader = new RequestReader(socketChannel, ByteBuffer.allocate(1024));
            RequestHandler requestHandler = new RequestHandler(commandHandler, requestReader, writer, logger);
            requestHandler.run();
//            logger.info("Client disconnected");
        } while (!singleIterationMode);
    }



}
