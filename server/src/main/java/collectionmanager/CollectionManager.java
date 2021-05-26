package collectionmanager;

import network.CurrentUser;
import musicband.MusicBand;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface CollectionManager {
    String info() throws SQLException;
    int add(MusicBand musicBand, CurrentUser currentUser) throws SQLException;
    int clear(CurrentUser currentUser) throws SQLException;
    int removeLast(CurrentUser currentUser) throws SQLException;
    MusicBand getById(long id) throws SQLException;
    int replace(long id, MusicBand musicBand, CurrentUser currentUser) throws SQLException;
    int removeById(long id, CurrentUser currentUser) throws SQLException;
    long getNewMaxId(CurrentUser currentUser) throws SQLException;
    LocalDate getInitializationDate() throws SQLException;
    ArrayList<MusicBand> getArrayList() throws SQLException;
}
