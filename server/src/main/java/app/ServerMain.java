package app;

import collectionmanager.ArrayListInitializer;
import collectionmanager.ArrayListManager;
import collectionmanager.Parser;
import collectionmanager.databasetools.*;
import console.commands.Command;
import console.commands.SaveCommand;
import musicband.MusicBand;
import musicband.MusicBandFieldsChecker;
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

        logger.info("App is turned on.");
        setArguments(args);

        ArrayList<MusicBand> musicBandArrayList = new ArrayList<>();
        LocalDate initializationDate = LocalDate.now();
        File file = null;
        String path;
        /*try {
            path = System.getenv("LAB5_PATH");
            if (path != null) {
                path = path.replace("\u202A", ""); // windows любит добавлять неизвестный непечатный символ в переменную окружения
                file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                if (file.canRead() && file.canWrite()) {
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    ArrayListInitializer arrayListInitializer = new ArrayListInitializer(fileReader, new MusicBandFieldsChecker(), new Parser());
                    try {
                        musicBandArrayList = arrayListInitializer.init();
                    } catch (IllegalStateException e) {
                        logger.error("Incorrect file");
                    }
                    if (arrayListInitializer.getInitializationDate() != null)
                        initializationDate = arrayListInitializer.getInitializationDate();
                }
                else logger.error("Access denied. Can't read or write file");
            }
            else logger.error("Error with environment variable. Variable LAB5_PATH not found");
        } catch (SecurityException e) {
            logger.error("File not found. Access denied. Can't create a new file.");
        } catch (FileNotFoundException e) {
            logger.error("File not found");
        } catch (IOException e) {
            logger.error("Unexpected error with initialization");
        }*/



        /*if (arrayListManager.containsRepeatingId()) {  // проверяем, есть ли объекты с одинаковым id
            logger.error("Error. Collection contains repeating id");
            arrayListManager.clear();
        }*/

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
            databaseManager = new DatabaseManager(databaseConnection, new UserChecker(databaseConnection));
            musicBandArrayList = databaseManager.getArrayList();
            initializationDate = databaseManager.getInitializationDate();
            ArrayListManager arrayListManager = new ArrayListManager(musicBandArrayList, initializationDate);
            Connector connector = null;
            try {
                connector = new Connector(PORT);
                server.Console console = new Console(arrayListManager, connector, logger, databaseManager);
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

    private static void setDatabaseArguments() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        dbHost = readDatabaseArguments(bufferedReader, "Enter database host:");
        dbPort = Integer.parseInt(readDatabaseArguments(bufferedReader, "Enter database port:"));
        dbName = readDatabaseArguments(bufferedReader, "Enter database name:");
        dbLogin = readDatabaseArguments(bufferedReader, "Enter database login");
        dbPassword = readDatabaseArguments(bufferedReader, "Enter database password");
    }

    public static void saveFile() {
        saveCommand.execute(null, null, null);
    }

}
