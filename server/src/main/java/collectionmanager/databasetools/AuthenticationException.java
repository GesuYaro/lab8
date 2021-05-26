package collectionmanager.databasetools;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Authentication error");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
