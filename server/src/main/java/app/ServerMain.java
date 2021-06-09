package app;

import collectionmanager.ArrayListManager;
import collectionmanager.databasetools.*;
import console.commands.Command;
import musicband.MusicBand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.*;
import server.Console;
import java.sql.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Главный класс, в котором создаются объекты и запускается приложение
 */
public class ServerMain {

    private static int PORT = 3101;
    public static final Logger logger = LoggerFactory.getLogger("Server");
    private static boolean singleIterationMode = false;
    private static Command saveCommand;
    private static String dbHost;
    private static String dbName;
    private static int dbPort;
    private static String dbLogin;
    private static String dbPassword;


    public static void main(String[] args) {

        setArguments(args);

        ArrayList<MusicBand> musicBandArrayList = new ArrayList<>();
        LocalDate initializationDate = LocalDate.now();

        setDatabaseArguments();
        DatabaseManager databaseManager = null;
        DatabaseConnector databaseConnector = new DatabaseConnector(dbHost, dbPort, dbName, dbLogin, dbPassword);
        try {
            Connection databaseConnection = databaseConnector.getConnection();
            DatabaseChecker databaseChecker = new DatabaseChecker(databaseConnection, dbLogin);
            DatabaseInitializer databaseInitializer = new DatabaseInitializer(databaseChecker, databaseConnection);
            try {
                databaseInitializer.init();
            } catch (DatabaseException e) {
                logger.error("Problem with database initialization");
                logger.error(e.getMessage());
            }
            databaseManager = new DatabaseManager(databaseConnection);
            musicBandArrayList = databaseManager.getArrayList();
            initializationDate = databaseManager.getInitializationDate();
            ArrayListManager arrayListManager = new ArrayListManager(musicBandArrayList, initializationDate);
            Connector connector = null;
            try {
                connector = new Connector(PORT);
                server.Console console = new Console(arrayListManager, connector, logger, databaseManager, new UserChecker(databaseConnection));
                console.run(singleIterationMode);
            } catch (IOException e) {
                logger.error("Can't make a server");
            }
        } catch (SQLException exception) {
            logger.error("Can't connect to Database");
        }


    }

    private static void setArguments(String[] args) {
        for (String argument:
             args) {
            if (argument.equals("s")) {
                singleIterationMode = true;
            } else {
                try {
                    PORT = Integer.parseInt(argument);
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    private static String readDatabaseArguments(BufferedReader bufferedReader, String message) {
        String answer = null;
        while (answer == null) {
            System.out.println(message);
            try {
                answer = bufferedReader.readLine();
            } catch (IOException e) {
                logger.error("Can't read DB arguments");
            }
        }
        return answer;
    }

    private static String readPassword(BufferedReader bufferedReader, String message) {
        String answer = null;
        java.io.Console console = System.console();
        if (console != null) {
            while (answer == null) {
                System.out.println(message);
                answer = new String(console.readPassword());
            }
        } else {
            while (answer == null) {
                try {
                    answer = bufferedReader.readLine();
                } catch (IOException e) {
                    System.out.println("Can't read password");
                }
            }
        }
        return answer;
    }

    private static void setDatabaseArguments() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        dbHost = readDatabaseArguments(bufferedReader, "Enter database host:");
        dbPort = Integer.parseInt(readDatabaseArguments(bufferedReader, "Enter database port:"));
        dbName = readDatabaseArguments(bufferedReader, "Enter database name:");
        dbLogin = readDatabaseArguments(bufferedReader, "Enter database login:");
        dbPassword = readPassword(bufferedReader, "Enter database password:");
        System.out.println("=========================");
    }

    public static void saveFile() {
        saveCommand.execute(null, null, null);
    }

}
