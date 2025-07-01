package exam.parsing.exception;

/**
 * Represents an exception thrown when an invalid operation is performed on a graph.
 * @author uepiy
 */

public class GraphOperandException extends Exception {
    private static final String PREFIX = "Invalid operation: ";

    /**
     * Constructs a new GraphOperandException with the given message.
     * @param message the message
     */
    public GraphOperandException(String message) {
        super(PREFIX + message);
    }
}
