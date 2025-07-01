package exam.terminal.commands;

/**
 * Represents the result of a command execution.
 * @param message the output/message of the command
 * @param success whether the command was successful
 * @author uepiy
 */

public record CommandResult(String message, boolean success) {
}
