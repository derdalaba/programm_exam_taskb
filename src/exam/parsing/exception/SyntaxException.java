package exam.parsing.exception;

/**
 * Represents an exception thrown when a syntax error in the database configuration file is encountered.
 * @author uepiy
 */

public class SyntaxException extends Exception {
    private static final String PREFIX = "Syntax error: ";
    /**
     * Constructs a new SyntaxException with the given message.
     * @param message the message
     */
    public SyntaxException(String message) {
        super(PREFIX + message);
    }
}
