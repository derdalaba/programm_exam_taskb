package exam;

import exam.terminal.CommandHandler;

/**
 * The main class of the application.
 * Creates a new command handler and runs the command handler.
 * @author uepiy
 */

public final class Main {
    private Main() {
    }
    /**
     * The main method of the application.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler();
        handler.run();
    }
}
