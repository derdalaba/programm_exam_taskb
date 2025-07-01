package exam.terminal.exception;

/**
 * Represents an exception thrown when an invalid argument is passed to a command.
 * @author uepiy
 */

public class InvalidArgumentException extends Exception {
    private static final String PREFIX = "Invalid argument: ";

    /**
     * Constructs a new InvalidArgumentException with the given message.
     * @param message the message
     */
    public InvalidArgumentException(String message) {
        super(PREFIX + message);
    }
}
