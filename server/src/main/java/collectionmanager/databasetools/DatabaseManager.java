package collectionmanager.databasetools;

import collectionmanager.CollectionManager;
import musicband.Coordinates;
import musicband.Label;
import musicband.MusicBand;
import musicband.MusicGenre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseManager implements CollectionManager {

    private Connection connection;
    private String userName;

    private static final String COLLECTION_SIZE = "SELECT COUNT(*) AS size FROM musicbands";
    private static final String INIT_DATE = "SELECT initialization_Date FROM MUSICBANDS_INITIALIZATION_DATE";
    private static final String ADD = "INSERT INTO musicbands VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CLEAR = "DELETE FROM musicbands WHERE owner = ?";
    private static final String REMOVE_LAST = "DELETE FROM musicbands WHERE owner = ? AND id = (SELECT MAX(id) FROM musicbands WHERE owner = ?)";
    private static final String GET_BY_ID = "SELECT * FROM musicbands WHERE id = ?";
    private static final String REPLACE_BY_ID = "UPDATE musicbands SET name = ?, coordinate_x = ?, coordinate_y = ?, number_of_participants = ?," +
            "singles_count = ?, music_genre = ?, label = ? WHERE id = ? AND owner = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM musicbands WHERE owner = ? AND id = ?";
    private static final String GET_NEW_MAX_ID = "SELECT nextval('musicband_ids')";
    private static final String GET_INIT_DATE = "SELECT initialization_date FROM musicbands_initialization_date";
    private static final String GET_ALL = "SELECT id, name, coordinate_x, coordinate_y, creation_date, number_of_participants, singles_count, music_genre, label " +
            "FROM musicbands";

    public DatabaseManager(Connection connection, String userName) {
        this.connection = connection;
        this.userName = userName;
    }

    @Override
    public String info() throws SQLException {
        PreparedStatement initDateStatement = connection.prepareStatement(INIT_DATE);
        PreparedStatement sizeStatement = connection.prepareStatement(COLLECTION_SIZE);
        ResultSet resultSet = initDateStatement.executeQuery();
        resultSet.next();
        String initDate = resultSet.getString(1);
        resultSet = sizeStatement.executeQuery();
        resultSet.next();
        String size = resultSet.getString(1);
        return "Type: ArrayList<MusicBand>"
                + "\nInitialization date: " + initDate
                + "\nNumber of elements: " + size;
    }

    @Override
    public int add(MusicBand musicBand) throws SQLException {
        PreparedStatement addStatement = connection.prepareStatement(ADD);
        addStatement.setLong(1, musicBand.getId());
        addStatement.setString(2, musicBand.getName());
        addStatement.setLong(3, musicBand.getCoordinates().getX());
        addStatement.setDouble(4, musicBand.getCoordinates().getY());
        addStatement.setDate(5, Date.valueOf(musicBand.getCreationDate()));
        addStatement.setInt(6, musicBand.getNumberOfParticipants());
        addStatement.setInt(7, musicBand.getSinglesCount());
        addStatement.setString(8, musicBand.getGenre().toString());
        addStatement.setString(9, musicBand.getLabel().getName());
        addStatement.setString(10, userName);
        return addStatement.executeUpdate();
    }

    @Override
    public int clear() throws SQLException {
        PreparedStatement clearStatement = connection.prepareStatement(CLEAR);
        clearStatement.setString(1, userName);
        return clearStatement.executeUpdate();
    }

    @Override
    public int removeLast() throws SQLException {
        PreparedStatement removeLastStatement = connection.prepareStatement(REMOVE_LAST);
        removeLastStatement.setString(1, userName);
        removeLastStatement.setString(2, userName);
        return removeLastStatement.executeUpdate();
    }

    @Override
    public MusicBand getById(long id) throws SQLException {
        MusicBand musicBand = null;
        PreparedStatement getByIdStatement = connection.prepareStatement(GET_BY_ID);
        getByIdStatement.setLong(1, id);
        ResultSet resultSet = getByIdStatement.executeQuery();
        if (resultSet.next()) {
            musicBand = new MusicBand(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    new Coordinates(resultSet.getLong("coordinate_x"), resultSet.getDouble("coordinate_y")),
                    resultSet.getDate("creation_date").toLocalDate(),
                    resultSet.getInt("number_of_participants"),
                    resultSet.getInt("singles_count"),
                    MusicGenre.valueOf(resultSet.getString("music_genre")),
                    new Label(resultSet.getString("label")));
        }
        return musicBand;
    }

    @Override
    public int replace(long id, MusicBand musicBand) throws SQLException {
        PreparedStatement replaceStatement = connection.prepareStatement(REPLACE_BY_ID);
        replaceStatement.setString(1, musicBand.getName());
        replaceStatement.setLong(2, musicBand.getCoordinates().getX());
        replaceStatement.setDouble(3, musicBand.getCoordinates().getY());
        replaceStatement.setInt(4, musicBand.getNumberOfParticipants());
        replaceStatement.setInt(5, musicBand.getSinglesCount());
        replaceStatement.setString(6, musicBand.getGenre().toString());
        replaceStatement.setString(7, musicBand.getLabel().getName());
        replaceStatement.setLong(8, id);
        replaceStatement.setString(9, userName);
        return replaceStatement.executeUpdate();
    }

    @Override
    public int removeById(long id) throws SQLException {
        PreparedStatement removeByIdStatement = connection.prepareStatement(REMOVE_BY_ID);
        removeByIdStatement.setString(1, userName);
        removeByIdStatement.setLong(2, id);
        return removeByIdStatement.executeUpdate();
    }

    @Override
    public long getNewMaxId() throws SQLException {
        Long maxId = 0L;
        PreparedStatement getNewMaxIdStatement = connection.prepareStatement(GET_NEW_MAX_ID);
        ResultSet resultSet = getNewMaxIdStatement.executeQuery();
        if (resultSet.next()) {
            maxId = resultSet.getLong("nextval");
        }
        return maxId;
    }

    @Override
    public LocalDate getInitializationDate() throws SQLException {
        LocalDate initDate = null;
        PreparedStatement getInitDateStatement = connection.prepareStatement(GET_INIT_DATE);
        ResultSet resultSet = getInitDateStatement.executeQuery();
        if (resultSet.next()) {
            initDate = resultSet.getDate("initialization_date").toLocalDate();
        }
        return initDate;
    }

    @Override
    public ArrayList<MusicBand> getArrayList() throws SQLException {
        ArrayList<MusicBand> list = new ArrayList<>();
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL);
        ResultSet resultSet = getAllStatement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Long coordinateX = resultSet.getLong("coordinate_x");
            Double coordinateY = resultSet.getDouble("coordinate_y");
            LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
            int numberOfParticipants = resultSet.getInt("number_of_participants");
            Integer singlesCount = resultSet.getInt("singles_count");
            MusicGenre musicGenre = MusicGenre.valueOf(resultSet.getString("music_genre"));
            Label label = new Label(resultSet.getString("label"));
            list.add(new MusicBand(id, name, new Coordinates(coordinateX, coordinateY), creationDate, numberOfParticipants, singlesCount, musicGenre, label));
        }
        return list;
    }
}
