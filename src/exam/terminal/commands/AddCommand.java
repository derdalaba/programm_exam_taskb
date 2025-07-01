package exam.terminal.commands;

import exam.graph.Graph;
import exam.graph.exception.InvalidRelationException;
import exam.parsing.ConfigParser;
import exam.parsing.exception.SyntaxException;

/**
 * Represents the add command.
 * Adds an edge to the graph.
 * @author uepiy
 */

public class AddCommand implements GraphHoldingCommand {
    private Graph graph;
    /**
     * Constructs a new AddCommand with the given graph.
     * @param graph the graph to add to
     */
    public AddCommand(Graph graph) {
        this.graph = graph;
    }
    @Override
    public void updateGraph(Graph graph) {
        if (graph == null) {
            return;
        }
        this.graph = graph;
    }
    @Override
    public CommandResult execute(String[] args) {
        if (graph == null) {
            return new CommandResult(NO_DATA_LOADED, false);
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
            }
            ConfigParser.parseLine(graph, sb.toString());
            return new CommandResult(NO_MESSAGE, true);
        } catch (InvalidRelationException | SyntaxException e) {
            return new CommandResult(e.getMessage(), false);
        }
    }
}
