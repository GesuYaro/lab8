package musicband;


import collectionmanager.CSVConvertible;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Vector;

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
    private String owner;

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
    public MusicBand(long id, String name, Coordinates coordinates, LocalDate creationDate, int numberOfParticipants, Integer singlesCount, MusicGenre genre, Label label, String owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.genre = genre;
        this.label = label;
        this.owner = owner;
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

    public Vector<Object> toVector() {
        Vector<Object> vector = new Vector<>();
        vector.add(id);
        vector.add(name);
        vector.add(coordinates.getX());
        vector.add(coordinates.getY());
        vector.add(creationDate);
        vector.add(numberOfParticipants);
        vector.add(singlesCount);
        vector.add(genre.name());
        vector.add(label.getName());
        return vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand musicBand = (MusicBand) o;
        return getId() == musicBand.getId() && getNumberOfParticipants() == musicBand.getNumberOfParticipants() && Objects.equals(getName(), musicBand.getName()) && Objects.equals(getCoordinates(), musicBand.getCoordinates()) && Objects.equals(getCreationDate(), musicBand.getCreationDate()) && Objects.equals(getSinglesCount(), musicBand.getSinglesCount()) && getGenre() == musicBand.getGenre() && Objects.equals(getLabel(), musicBand.getLabel());
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setSinglesCount(Integer singlesCount) {
        this.singlesCount = singlesCount;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}





