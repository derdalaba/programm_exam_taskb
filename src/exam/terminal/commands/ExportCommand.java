package exam.terminal.commands;

import exam.graph.Graph;

import java.util.List;

import static exam.parsing.DotView.toDot;

/**
 * Represents the export command.
 * Exports the graph to the console in dot format.
 * @author uepiy
 */

public class ExportCommand implements GraphHoldingCommand {
    private Graph graph;
    /**
     * Constructs a new ExportCommand with the given graph.
     * @param graph the graph to export
     */
    public ExportCommand(Graph graph) {
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
        List<String> lines = toDot(this.graph);
        return new CommandResult(lineListToString(lines), true);
    }
}
