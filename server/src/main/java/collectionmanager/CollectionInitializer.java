package collectionmanager;

import musicband.MusicBand;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface CollectionInitializer {
    void init() throws SQLException;
}
