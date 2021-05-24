package console.commands;

import collectionmanager.ArrayListManager;
import musicband.MusicBand;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;

import java.io.*;

/**
 * Класс команды save, сохраняющей коллекцию в файл
 */
public class SaveCommand extends AbstractCommand {

    private ArrayListManager listManager;
    private Writer writer;
    private Logger logger;
    private File file;

    /**
     * @param listManager Менеджер коллекции
     * @param file Файл, в который будет сохрянятся коллекция
     * @param logger Объект класса, выводящего в консоль
     */
    public SaveCommand(ArrayListManager listManager, File file, Logger logger) {
        super("save", "save collection into file");
        this.listManager = listManager;
        this.file = file;
        this.logger = logger;
    }

    /**
     * @param firstArgument
     * @param requestedMusicBand
     * @return CommandCode.DEFAULT
     */
    @Override
    public CommandCode execute(String firstArgument, MusicBand requestedMusicBand) {
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file));
            try {
                writer.write(listManager.getInitializationDate().toString() + "\n");
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.RFC4180);
                for (MusicBand musicBand: listManager.getArrayList()) {
                    printer.printRecord(musicBand.getId(), musicBand.getName(), musicBand.getCoordinates().getX(), musicBand.getCoordinates().getY(), musicBand.getCreationDate(), musicBand.getNumberOfParticipants(), ((musicBand.getSinglesCount()) != null) ? musicBand.getSinglesCount() : "", ((musicBand.getGenre() != null) ? musicBand.getGenre() : ""), ((musicBand.getLabel().getName() != null) ? musicBand.getLabel().getName() : ""));
                }
                writer.flush();
                logger.info("Saving successfully done");
            } catch (IOException e) {
                logger.error("Unexpected save error");
            }
        } catch (FileNotFoundException | NullPointerException e) {
            logger.error("File not found");
        }
        return CommandCode.DEFAULT;
    }
}
