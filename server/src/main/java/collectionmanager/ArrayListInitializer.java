package collectionmanager;

import musicband.InputValueException;
import musicband.*;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс инициализатор коллекции
 */
public class ArrayListInitializer {
    private Reader reader;
    private LocalDate initializationDate;
    private Parser parser;
    private MusicBandFieldsChecker fieldsReader;

    /**
     * @param reader
     * @param fieldsChecker Считыватель полей
     * @param parser Парсер
     */
    public ArrayListInitializer(Reader reader, MusicBandFieldsChecker fieldsChecker, Parser parser) {
        this.reader = reader;
        this.fieldsReader = fieldsChecker;
        this.parser = parser;
    }

    /**
     * @return Дата инициализации коллекции
     */
    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    /**
     * Инициализирует коллекцию
     * @return Коллекция объектов MusicBand
     * @throws IOException
     */
    public ArrayList<MusicBand> init() throws IOException, IllegalStateException {
        ArrayList<ArrayList<String>> data = parser.parseCSV(reader);
        ArrayList<MusicBand> musicBands = new ArrayList<>();
        Iterator<ArrayList<String>> iterator = data.iterator();
        Iterator<String> innerIteratorForDate;
        if (iterator.hasNext()) {
            innerIteratorForDate = iterator.next().iterator();
            if (innerIteratorForDate.hasNext()) {
                try {
                    initializationDate = fieldsReader.readDate(innerIteratorForDate.next());
                } catch (InputValueException e) {
                    System.out.println("Error in the file: " + e.getMessage());
                }
            }
        }
        while (iterator.hasNext()) {
            Iterator<String> innerIterator = iterator.next().iterator();
            long id = 0;
            String name = "";
            Long x = Long.valueOf(0);
            Double y = Double.valueOf(0.0);
            Coordinates coordinates = null;
            LocalDate creationDate = null;
            int numberOfParticipants = 0;
            Integer singlesCount = 0;
            MusicGenre genre = null;
            Label label = null;
            try {
                if (innerIterator.hasNext()) id = fieldsReader.readId(innerIterator.next());
                if (innerIterator.hasNext()) name = fieldsReader.readName(innerIterator.next());
                if (innerIterator.hasNext()) x = fieldsReader.readX(innerIterator.next());
                if (innerIterator.hasNext()) { y = fieldsReader.readY(innerIterator.next()); coordinates = new Coordinates(x, y); }
                if (innerIterator.hasNext()) creationDate = fieldsReader.readDate(innerIterator.next());
                if (innerIterator.hasNext()) numberOfParticipants = fieldsReader.readNumberOfParticipants(innerIterator.next());
                if (innerIterator.hasNext()) singlesCount = fieldsReader.readSinglesCount(innerIterator.next());
                if (innerIterator.hasNext()) genre = fieldsReader.readMusicGenre(innerIterator.next());
                if (innerIterator.hasNext()) label = fieldsReader.readLabel(innerIterator.next());
                if (label != null) musicBands.add(new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, singlesCount, genre, label));
            } catch (InputValueException e) {
                System.out.println("Error in the file: " + e.getMessage());
            }
        }
        return musicBands;
    }
}
