package musicband;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Класс, осуществляющий проверку вводимых значений полей на корректность и выводящий предупреждающие сообщения при неверном вводе
 */
public class MusicBandFieldsChecker {
    private BufferedReader reader;

    /**
     * @param reader Буфферизированный поток ввода
     */
    public MusicBandFieldsChecker(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Конструктор для создания чеккера, работающего только со строками
     * Может использоваться для чтения из файла
     */
    public MusicBandFieldsChecker(){}

    /**
     * @return Корректный id
     * @throws InputValueException При неправильно введенных значениях
     */
    public long readId() throws InputValueException {
        long id;
            try {
                id = Long.parseLong(reader.readLine().trim());
            }
            catch (NumberFormatException e){
                throw new InputValueException("ID must be long and greater than 0");
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }
        return id;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректный id
     * @throws InputValueException При неправильно введенных данных
     */
    public long readId(String string) throws InputValueException {
        long id;
        try {
            id = Long.parseLong(string.trim());
            if (id <= 0) throw new InputValueException("ID must be long and greater than 0");
        }
        catch (NumberFormatException e){
            throw new InputValueException("ID must be long and greater than 0");
        }
        return id;
    }

    /**
     * @return Корректное название
     * @throws InputValueException При неправильно введенных данных
     */
    public String readName() throws InputValueException {
        String name;
        String prename;
            try {
                prename = reader.readLine();
                if (prename != null) {
                    prename = prename.trim();
                    if (!prename.equals("")) {
                        name = prename;
                    } else throw new InputValueException("Input Error\nField can't be null, string can't be empty");
                } else throw new InputValueException("Input Error\nField can't be null, string can't be empty");
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }
        return name;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректное название
     * @throws InputValueException При неправильно введенных данных
     */
    public String readName(String string) throws InputValueException {
        String name;
        String prename;
        prename = string.trim();
        if (!prename.equals("")) {
            name = prename;
            //name = name.replaceAll("%COMMA%", ",");
        }
        else throw new InputValueException("Input Error\nName field can't be null, string can't be empty");
        return name;
    }

    /**
     * @return Корректное значение координаты X
     * @throws InputValueException При неправильно введенных данных
     */
    public long readX() throws InputValueException {
        long x;
        try {
            String string = reader.readLine();
            if (string != null) {
                x = Long.parseLong(string.trim());
                if (x < -153) throw new InputValueException("Field can't be less than -153");
            } else throw new InputValueException("Input Error\nField can't be null");
        }
        catch (NumberFormatException e){
            throw new InputValueException("Input Error\nField should be long");
        }
        catch (IOException e) {
            throw new InputValueException("Unexpected input error");
        }
        return x;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректное значение координаты X
     * @throws InputValueException При неправильно введенных данных
     */
    public long readX(String string) throws InputValueException {
        long x;
        try {
            x = Long.parseLong(string.trim());
            if (x < -153) throw new InputValueException("X can't be less than -153");
        }
        catch (NumberFormatException e){
            throw new InputValueException("Input Error\nField should be long");
        }
        return x;
    }

    /**
     * @return Корректное значение координаты Y
     * @throws InputValueException При неправильно введенных данных
     */
    public double readY() throws InputValueException {
        double y;
            try {
                String string = reader.readLine();
                if (string != null) {
                    y = Double.parseDouble(string.trim());
                    if (y < -159) throw new InputValueException("Field should be greater than -159");
                } else throw new InputValueException("Input Error\nField can't be null");
            }
            catch (NumberFormatException e){
                throw new InputValueException("Input Error\nField should be double");
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }
        return y;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректное значение координаты Y
     * @throws InputValueException При неправильно введенных данных
     */
    public double readY(String string) throws InputValueException {
        double y;
        try {
            y = Double.parseDouble(string.trim());
            if (y < -159) throw new InputValueException("Y should be greater than -159");
        }
        catch (NumberFormatException e){
            throw new InputValueException("Input Error\nY should be double");
        }
        return y;
    }

    /**
     * @return Корректное значение координат
     * @throws InputValueException При неправильно введенных данных
     */
    public Coordinates readCoordinates() throws InputValueException {
        Long x = this.readX();
        Double y = this.readY();
        return new Coordinates(x, y);
    }

    /**
     * @return Корректное значение даты
     * @throws InputValueException При неправильно введенных данных
     */
    public LocalDate readDate() throws InputValueException {
        LocalDate date;
        try {
            String string = reader.readLine();
            if (string != null) {
                date = LocalDate.parse(string.trim());
            } else throw new InputValueException("Input Error\nField can't be null");
        } catch (DateTimeParseException e) {
            throw new InputValueException("Input Error\nEnter date in YYYY-MM-DD format");
        } catch (IOException e) {
            throw new InputValueException("Unexpected input error");
        }
        return date;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректная дата
     * @throws InputValueException При неправильно введенных данных
     */
    public LocalDate readDate(String string) throws InputValueException {
        LocalDate date;
        try {
            date = LocalDate.parse(string.trim());
        } catch (DateTimeParseException e) {
            throw new InputValueException("Input Error\nEnter date in YYYY-MM-DD format");
        }
        return date;
    }

    /**
     * @return Корректное количество участников
     * @throws InputValueException При неправильно введенных данных
     */
    public int readNumberOfParticipants() throws InputValueException {
        int numberOfParticipants;
            try {
                String string = reader.readLine();
                if (string != null) {
                    numberOfParticipants = Integer.parseInt(string.trim());
                    if (numberOfParticipants <= 0) throw new InputValueException("Input Error\nEnter the natural number");
                } else throw new InputValueException("Input Error\nField can't be null");
            }
            catch (NumberFormatException e){
                throw new InputValueException("Input Error\nEnter the natural number");
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }
        return numberOfParticipants;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректное количество участников
     * @throws InputValueException При неправильно введенных данных
     */
    public int readNumberOfParticipants(String string) throws InputValueException {
        int numberOfParticipants;
        try {
            numberOfParticipants = Integer.parseInt(string.trim());
            if (numberOfParticipants <= 0) throw new NumberFormatException();
        }
        catch (NumberFormatException e){
            throw new InputValueException("Input Error\nNumber of participants should be the natural number");
        }
        return numberOfParticipants;
    }

    /**
     * @return Корректное количество участников
     * @throws InputValueException При неправильно введенных данных
     */
    public Integer readSinglesCount() throws InputValueException {
        Integer singlesCount = null;
            try {
                String str = reader.readLine();
                if (str != null) {
                    str = str.trim();
                    if (!str.equals("")) {
                        Integer preSinglesCount = Integer.parseInt(str);
                        if (preSinglesCount > 0) {
                            singlesCount = preSinglesCount;
                        } else {
                            throw new NumberFormatException();
                        }
                    }
                }
            }
            catch (NumberFormatException e){
                throw new InputValueException("Input Error\nEnter the natural number");
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }
        return singlesCount;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректное количество синглов
     * @throws InputValueException При неправильно введенных данных
     */
    public Integer readSinglesCount(String string) throws InputValueException {
        Integer singlesCount = null;
        try {
            String str = string.trim();
            if (!str.equals("")) {
                singlesCount = Integer.parseInt(str);
            }
        }
        catch (NumberFormatException e){
            throw new InputValueException("Input Error\nSingles count should be the natural number");
        }
        return singlesCount;
    }

    /**
     * @return Корректный жанр
     * @throws InputValueException
     */
    public MusicGenre readMusicGenre() throws InputValueException {
        String str;
        MusicGenre choicedMusicGenre = null;
            try {
                str = reader.readLine();
                if (str != null) {
                    str = str.trim().toUpperCase();
                    if (!str.equals("")) {
                        for (MusicGenre musicGenre : MusicGenre.values()) {
                            if (musicGenre.name().equals(str)) {
                                choicedMusicGenre = musicGenre;
                            }
                        }
                        if (choicedMusicGenre == null) {
                            throw new InputValueException("Input Error\nGenre should be chosen from the list");
                        }
                    }
                }
            }
            catch (IOException e) {
                throw new InputValueException("Unexpected input error");
            }

        return choicedMusicGenre;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректный жанр
     * @throws InputValueException При неправильно введенных данных
     */
    public MusicGenre readMusicGenre(String string) throws InputValueException {
        String str;
        MusicGenre choicedMusicGenre = null;
        if (string != null) {
            str = string.trim().toUpperCase();
            if (!str.equals("")) {
                for (MusicGenre musicGenre : MusicGenre.values()) {
                    if (musicGenre.name().equals(str)) {
                        choicedMusicGenre = musicGenre;
                    }

                }
                if (choicedMusicGenre == null) {
                    throw new InputValueException("Input Error\nGenre should be chosen from the list");
                }
            }
        }
        return choicedMusicGenre;
    }

    /**
     * @return Корректный лейбл
     * @throws InputValueException
     */
    public Label readLabel() throws InputValueException {
        Label label = new Label();
        try {
            String string = reader.readLine();
            if (string != null) {
                label.setName(string.trim());
            }
        }
        catch (IOException e) {
            throw new InputValueException("Unexpected input error");
        }
        return label;
    }

    /**
     * @param string Строка, в которой могут находиться значения
     * @return Корректный лейбл
     * @throws InputValueException При неправильно введенных данных
     */
    public Label readLabel(String string) throws InputValueException {
        Label label = new Label();
        if (string != null) {
            string = string.trim();
        }
        label.setName(string);
        return label;
    }

    /**
     * @param reader Устанавливает новый буфферезированный поток ввода
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }
}
