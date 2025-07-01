package exam.terminal.commands;

import java.util.List;

/**
 * Represents a command that can be executed in the terminal.
 * @author uepiy
 */

public interface Command {
    /**
     * Represents the message when arguments are not allowed.
     */
    String NO_ARG_COMMAND = "Command does not take arguments.";
    /**
     * Represents the empty message.
     */
    String NO_MESSAGE = "";

    /**
     * Executes the command with the given arguments.
     * @param args the arguments
     * @return the result of the command execution
     */
    CommandResult execute(String[] args);
    /**
     * Converts a list of lines to a single string with newlines.
     * @param lines the lines to convert
     * @return the string without trailing newline
     */
    default String lineListToString(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
