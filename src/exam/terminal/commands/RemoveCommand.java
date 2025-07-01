package exam.terminal.commands;

import exam.graph.Graph;
import exam.parsing.exception.GraphOperandException;

import static exam.parsing.GraphOperand.removeEdge;

/**
 * Represents the remove command.
 * Removes an edge from the graph.
 * @author uepiy
 */

public class RemoveCommand implements GraphHoldingCommand {
    private Graph graph;
    /**
     * Constructs a new RemoveCommand with the given graph.
     * @param graph the graph to remove from
     */
    public RemoveCommand(Graph graph) {
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
        StringBuilder sb = new StringBuilder();
        for (String argument : args) {
            sb.append(argument);
        }
        String line = sb.toString();
        try {
            removeEdge(graph, line);
            return new CommandResult(Command.NO_MESSAGE, true);
        } catch (GraphOperandException e) {
            return new CommandResult(e.getMessage(), false);
        }
    }
}
