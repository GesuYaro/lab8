package collectionmanager.databasetools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private String hostName;
    private String userName;
    private String password;
    private String databaseName;
    private int port;

    public DatabaseConnector(String hostName, int port, String databaseName,String userName, String password) {
        this.hostName = hostName;
        this.userName = userName;
        this.password = password;
        this.databaseName = databaseName;
        this.port = port;
    }

    public Connection getConnection() throws SQLException, DriverException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverException("Database driver not found");
        }
        return DriverManager.getConnection("jdbc:postgresql://" + hostName + ":" + port + "/" + databaseName, userName, password);
    }

}
