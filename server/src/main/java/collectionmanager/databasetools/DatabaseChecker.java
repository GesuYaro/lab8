package collectionmanager.databasetools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseChecker {

    private final String userName;
    private final Connection connection;
    private static final String CHECK_MAIN_TABLE = "SELECT COUNT(*) AS row_count FROM pg_tables " +
            "WHERE schemaname != 'pg_catalog' " +
            "AND schemaname != 'information_schema' AND tableowner = ? AND tablename = ?";
//    private static final String MAIN_TABLE_COLUMNS = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";

    public DatabaseChecker(Connection connection, String userName) {
        this.userName = userName;
        this.connection = connection;
    }

    public boolean checkMainTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CHECK_MAIN_TABLE);
//        preparedStatement.setString(1, "row_count");
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, "musicbands");
        ResultSet resultSet = preparedStatement.executeQuery();
        int tablesCount = 0;
        while (resultSet.next()) {
            tablesCount += resultSet.getInt("row_count");
        }
        return tablesCount == 1;
    }


}
