package exam.terminal.commands;

import exam.graph.Graph;
import exam.parsing.FileHandle;
import exam.parsing.exception.GraphParseException;
import exam.terminal.exception.InvalidArgumentException;

import java.util.List;

import static exam.parsing.ConfigParser.parse;

/**
 * Represents a command to load a graph from a database file.
 * The command takes a single argument, the path to the database file.
 * The command returns the contents of the database file.
 * @author uepiy
 */

public class LoadDatabaseCommand implements Command {
    private Graph graph;
    /**
     * Constructs a new LoadDatabaseCommand.
     */
    public LoadDatabaseCommand() {
        graph = null;
    }
    @Override
    public CommandResult execute(String[] args) {
        try {
            List<String> lines = FileHandle.getLines(args[0]);
            this.graph = parse(lines);
            return new CommandResult(lineListToString(lines), true);
        } catch (InvalidArgumentException | GraphParseException e) {
            return new CommandResult(e.getMessage(), false);
        }
    }
    /**
     * Returns the graph loaded by the command.
     * @return the graph
     */
    public Graph getGraph() {
        return this.graph;
    }
}
