package exam.terminal.commands;

import exam.graph.Graph;

/**
 * Represents a command that holds a graph.
 * Provides a method to update the graph.
 * @author uepiy
 */

public interface GraphHoldingCommand extends Command {
    /**
     * The message to display when no data is loaded.
     */
    String NO_DATA_LOADED = "No database loaded";
    /**
     * Updates the graph.
     * @param graph the graph to update to
     */
    void updateGraph(Graph graph);
}
