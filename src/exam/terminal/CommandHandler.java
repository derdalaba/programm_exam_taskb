package exam.terminal;

import exam.graph.Graph;
import exam.terminal.commands.CommandResult;
import exam.terminal.commands.Command;
import exam.terminal.commands.AddCommand;
import exam.terminal.commands.RemoveCommand;
import exam.terminal.commands.NodesCommand;
import exam.terminal.commands.EdgesCommand;
import exam.terminal.commands.RecommendCommand;
import exam.terminal.commands.ExportCommand;
import exam.terminal.commands.LoadDatabaseCommand;
import exam.terminal.commands.QuitCommand;
import exam.terminal.commands.GraphHoldingCommand;
import exam.terminal.exception.CommandNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents a command handler.
 * Handles terminal io and command execution.
 * @author uepiy
 */

public class CommandHandler {
    private static final String ERROR_PREFIX = "Error: ";
    private static final String ARGUMENT_SEPARATOR = " ";
    private static final String EMPTY_STRING = "";
    private static final String QUIT_COMMAND = "quit";
    private static final String LOAD_DATABASE_COMMAND = "load database";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String NODES_COMMAND = "nodes";
    private static final String EDGES_COMMAND = "edges";
    private static final String RECOMMEND_COMMAND = "recommend";
    private static final String EXPORT_COMMAND = "export";
    private boolean running;
    private HashMap<String, Command> commands;
    private Graph graph;
    /**
     * Constructs a new command handler.
     * Initializes the commands and an empty graph.
     * Runs the command handler.
     * @author uepiy
     */
    public CommandHandler() {
        this.commands = new HashMap<>();
        this.graph = null;
        initCommands();
    }
    private void initCommands() {
        commands.put(QUIT_COMMAND, new QuitCommand(this));
        commands.put(LOAD_DATABASE_COMMAND, new LoadDatabaseCommand());
        commands.put(ADD_COMMAND, new AddCommand(graph));
        commands.put(REMOVE_COMMAND, new RemoveCommand(graph));
        commands.put(NODES_COMMAND, new NodesCommand(graph));
        commands.put(EDGES_COMMAND, new EdgesCommand(graph));
        commands.put(RECOMMEND_COMMAND, new RecommendCommand(graph));
        commands.put(EXPORT_COMMAND, new ExportCommand(graph));
    }
    /**
     * Quits the command handler.
     */
    public void quit() {
        running = false;
    }
    private String getCommand(String input) throws CommandNotFoundException {
        ArrayList<String> possibleCommands = new ArrayList<>();
        // Find all possible commands that match the input
        for (String command : commands.keySet()) {
            if (input.startsWith(command)) {
                possibleCommands.add(command);
            }
        }
        // Find the longest possible command. This is the actual command
        String command = EMPTY_STRING;
        for (String possibleCommand : possibleCommands) {
            if (possibleCommand.length() > command.length()) {
                command = possibleCommand;
            }
        }
        if (command.equals(EMPTY_STRING)) {
            throw new CommandNotFoundException(input.split(ARGUMENT_SEPARATOR)[0]); // Command not found. Throw exception
        }
        return command;
    }
    private String[] getArguments(String input, String command) {
        return input.substring(command.length()).trim().split(" ");
    }
    private void printResult(CommandResult result) {
        if (result.message().isEmpty()) {
            return;
        }
        if (result.success()) {
            if (result.message().contains("\n")) { // If message contains new line
                System.out.print(result.message());
                return;
            }
            System.out.println(result.message());
        } else {
            printError(result.message());
        }
    }
    private void printError(String message) {
        System.err.println(ERROR_PREFIX + message);
    }
    private void updateGraphInstance(Graph graph) {
        this.graph = graph;
        for (Command command : commands.values()) {
            if (command instanceof GraphHoldingCommand) {
                ((GraphHoldingCommand) command).updateGraph(graph);
            }
        }
    }
    private CommandResult processInput(Scanner scanner) throws CommandNotFoundException {
        String input = scanner.nextLine();
        String command = getCommand(input);
        String[] arguments = getArguments(input, command);
        if (command.equals(LOAD_DATABASE_COMMAND)) {
            CommandResult result = commands.get(command).execute(arguments);
            if (result.success()) {
                Graph updatedGraph = ((LoadDatabaseCommand) commands.get(command)).getGraph();
                updateGraphInstance(updatedGraph);
            }
            return result;
        }
        return commands.get(command).execute(arguments);
    }
    /**
     * Runs the command handler.
     * Reads input from the terminal and processes it.
     * Prints command results.
     */
    public void run() {
        this.running = true;
        Scanner scanner = new Scanner(System.in);
        while (running && scanner.hasNextLine()) {
            try {
                CommandResult result = processInput(scanner);
                printResult(result);
            } catch (CommandNotFoundException e) {
                printError(e.getMessage());
            }
        }
    }
}