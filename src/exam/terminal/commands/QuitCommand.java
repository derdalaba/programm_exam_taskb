package exam.terminal.commands;


import exam.terminal.CommandHandler;

/**
 * Represents the quit command.
 * Quits the terminal application.
 * @author uepiy
 */

public class QuitCommand implements Command {
    private final CommandHandler handler;
    /**
     * Constructs a new QuitCommand with the given handler.
     * @param handler the handler to quit
     */
    public QuitCommand(CommandHandler handler) {
        this.handler = handler;
    }
    /**
     * Executes the quit command.
     * @param args the arguments to pass to the command
     * @return the result of the command
     */
    public CommandResult execute(String[] args) {
        handler.quit();
        return new CommandResult(NO_MESSAGE, true);
    }
}
