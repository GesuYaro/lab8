package musicband;


import collectionmanager.CSVConvertible;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Музыкальная группа, объекты этого класса хранятся в коллекции
 */
public class MusicBand implements Comparable<MusicBand>, CSVConvertible, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private Integer singlesCount; //Поле может быть null, Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Label label; //Поле не может быть null

    /**
     * Создает музыкальную группу
     * @param id
     * @param name Название
     * @param coordinates Координаты
     * @param creationDate Дата создания
     * @param numberOfParticipants Количество участников
     * @param singlesCount Количество синглов
     * @param genre Жанр
     * @param label Лейбл
     */
    public MusicBand(long id, String name, Coordinates coordinates, LocalDate creationDate, int numberOfParticipants, Integer singlesCount, MusicGenre genre, Label label) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.genre = genre;
        this.label = label;
    }


    public long getId() {
        return id;
    }

    /**
     * @return Название группы
     */
    public String getName() {
        return name;
    }

    /**
     * @return Координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Дата создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return Количество участников
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * @return Количество синглов
     */
    public Integer getSinglesCount() {
        return singlesCount;
    }

    /**
     * @return Жанр
     */
    public MusicGenre getGenre() {
        return genre;
    }

    /**
     * @return Жанр
     */
    public Label getLabel() {
        return label;
    }


    /**
     * @param o Экземпляр класса MusicBand, с которым необходимо сравнить
     * @return больше 0, если больше, меньше 0, если меньше, 0, если равно
     */
    @Override
    public int compareTo(MusicBand o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * @return Приводит объект к строковому виду
     */
    @Override
    public String toString() {
        return "MusicBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.getX() + " ; " + coordinates.getY() +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", singlesCount=" + singlesCount +
                ", genre=" + genre +
                ", label=" + label.getName() +
                '}';
    }

    /**
     * @return Приводит объект к строковому виду в формате CSV
     */
    @Override
    public String toCSV() {
        String csvName = name.replaceAll(",", "%COMMA%");
        String csvLabelName = label.getName().replaceAll(",", "%COMMA%");
        return "" + id + ","
                + csvName + ","
                + coordinates.getX() + ","
                + coordinates.getY() + ","
                + creationDate + ","
                + numberOfParticipants + ","
                + ((singlesCount != null) ? singlesCount : "") + ","
                + ((genre != null) ? genre : "") + ","
                + csvLabelName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}





