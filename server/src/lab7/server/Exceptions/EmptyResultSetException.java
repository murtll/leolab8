package lab7.server.Exceptions;

public class EmptyResultSetException extends Exception {
    public EmptyResultSetException() {
        super("Empty result set!");
    }
}
