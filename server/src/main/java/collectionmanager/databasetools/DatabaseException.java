package collectionmanager.databasetools;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super("Problem with database");
    }

    public DatabaseException(String message) {
        super(message);
    }
}
