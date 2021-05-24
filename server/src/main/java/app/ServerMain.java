package app;

import collectionmanager.ArrayListInitializer;
import collectionmanager.ArrayListManager;
import collectionmanager.Parser;
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

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownSaver(), "shutdown saver"));
    }

    public static void main(String[] args) {

        logger.info("App is turned on.");
        setArguments(args);
        ArrayList<MusicBand> musicBandArrayList = new ArrayList<>();
        LocalDate initializationDate = LocalDate.now();
        File file = null;
        String path;
        try {
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
        }

        ArrayListManager arrayListManager = new ArrayListManager(musicBandArrayList, initializationDate);
        saveCommand = new SaveCommand(arrayListManager, file, logger);

        if (arrayListManager.containsRepeatingId()) {  // проверяем, есть ли объекты с одинаковым id
            logger.error("Error. Collection contains repeating id");
            arrayListManager.clear();
        }

        Connector connector = null;
        try {
            connector = new Connector(PORT);
            server.Console console = new Console(arrayListManager, connector, logger);
            console.run(singleIterationMode);
        } catch (IOException e) {
            logger.error("Can't make a server");
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

    public static void saveFile() {
        saveCommand.execute(null, null);
    }

}
