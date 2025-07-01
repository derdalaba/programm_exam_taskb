package exam.parsing.exception;

/**
 * Represents an exception thrown when a syntax error in the graph configuration file is encountered.
 * The exception message should describe the syntax error.
 * @author uepiy
 */

public class GraphParseException extends Exception {
    private static final String PREFIX = "Syntax error: ";
    /**
     * Constructs a new GraphParseException with the given message.
     * @param message the message
     */
    public GraphParseException(String message) {
        super(PREFIX + message);
    }
}
