package exam.terminal.exception;

/**
 * Represents an exception thrown when a command is not found.
 * @author uepiy
 */

public class CommandNotFoundException extends Exception {
    private static final String PREFIX = "Command not found: ";
    /**
     * Constructs a new CommandNotFoundException with the given message.
     * @param message the message
     */
    public CommandNotFoundException(String message) {
        super(PREFIX + message);
    }
}
