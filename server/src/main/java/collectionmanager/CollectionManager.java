package collectionmanager;

import musicband.MusicBand;
import musicband.MusicGenre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface CollectionManager {
    String info() throws SQLException;
    int add(MusicBand musicBand) throws SQLException;
    int clear() throws SQLException;
    int removeLast() throws SQLException;
    MusicBand getById(long id) throws SQLException;
    int replace(long id, MusicBand musicBand) throws SQLException;
    int removeById(long id) throws SQLException;
    long getNewMaxId() throws SQLException;
    LocalDate getInitializationDate() throws SQLException;
    ArrayList<MusicBand> getArrayList() throws SQLException;
}
