package collectionmanager.databasetools;

public class DriverException extends RuntimeException {
    public DriverException() {
        super("Driver not found");
    }

    public DriverException(String message) {
        super(message);
    }
}
