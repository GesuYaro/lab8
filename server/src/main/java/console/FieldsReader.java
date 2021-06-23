package console;

import console.exсeptions.IncorrectScriptException;
import musicband.InputValueException;
import musicband.*;


/**
 * Считываетель полей для создания объектов
 * Для каждого поля запускает цикл, ожидающий корректного значения
 */
public class FieldsReader implements Reader {
    private final MusicBandFieldsChecker checker;
    private final ConsoleWriter writer;
    private final boolean interactiveMode;

    /**
     * Создает интерактивный считыватель полей
     * @param checker Проверяльщик полей на корректность
     * @param writer Выводяльщик в консоль
     */
    public FieldsReader(MusicBandFieldsChecker checker, ConsoleWriter writer) {
        this.checker = checker;
        this.writer = writer;
        this.interactiveMode = true;
    }

    /**
     * Создает считыватель полей
     * @param checker Проверяльщик полей на корректность
     * @param writer Выводяльщик в консоль
     * @param interactiveMode Если false, то считыватель не будет просить вводить поля (нужно для скрипт-мода)
     */
    public FieldsReader(MusicBandFieldsChecker checker, ConsoleWriter writer, boolean interactiveMode) {
        this.checker = checker;
        this.writer = writer;
        this.interactiveMode = interactiveMode;
    }


    private <T> T read(String message, CheckReader<T> checkReader) throws IncorrectScriptException {
        T obj;
        if (interactiveMode) {
            while (true) {
                try {
                    writer.write(message);
                    obj = checkReader.read();
                    break;
                } catch (InputValueException e) {
                    writer.write(e.getMessage());
                }
            }
        }
        else {
            try {
                obj = checkReader.read();
            } catch (InputValueException e) {
                throw new IncorrectScriptException();
            }
        }
        return obj;
    }

    /**
     * @return Корректное название, введенное пользователем
     */
    public String readName() {
        return read("Enter the name:", checker::readName);
    }


    /**
     * @return Корректные координаты, введенные пользователем
     */
    public Coordinates readCoordinates() {
        Long x;
        Double y;
        x = read("Enter X coordinate:", checker::readX);
        y = read("Enter Y coordinate:", checker::readY);
        return new Coordinates(x, y);
    }

    /**
     * @return Корректное количество участников, введенное пользователем
     */
    public int readNumberOfParticipants() {
        return read("Enter the number of participants:", checker::readNumberOfParticipants);
    }

    /**
     * @return Корректное количество синглов, введенное пользователем
     */
    public Integer readSinglesCount() {
        return read("Enter the singles count:", checker::readSinglesCount);
    }

    /**
     * @return Корректный жанр, введенный пользователем
     */
    public MusicGenre readMusicGenre() {
        String message = "Choose the genre from the list:";
        for (MusicGenre genre : MusicGenre.values()) {
            message += "\n" + genre.name();
        }
        return this.read(message, checker::readMusicGenre);
    }


    /**
     * @return Корректный лейбл, введенный пользователем
     */
    public Label readLabel() {
        return read("Enter the label name:", checker::readLabel);
    }

}
