package main.java.main;

public class DomException extends Exception {
    public DomException(String message) {
        super(message);
    }

    public DomException(String message, Throwable cause) {
        super(message, cause);
    }
}
