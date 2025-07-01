package exam.terminal.commands;

import exam.graph.Graph;
import exam.terminal.exception.InvalidArgumentException;

import java.util.Collections;
import java.util.List;

import static exam.parsing.RecursiveDescent.descent;

/**
 * Represents the recommend command.
 * Recommends products selected based on a strategy specified by the user from the graph.
 * @author uepiy
 */
public class RecommendCommand implements GraphHoldingCommand {
    private Graph graph;
    /**
     * Constructs a new RecommendCommand with the given graph.
     * @param graph the graph to recommend
     */
    public RecommendCommand(Graph graph) {
        this.graph = graph;
    }
    @Override
    public void updateGraph(Graph graph) {
        if (graph == null) {
            return;
        }
        this.graph = graph;
    }
    private String listToString(List<String> list) {
        List<String> sortedList = list;
        Collections.sort(sortedList);
        StringBuilder sb = new StringBuilder();
        for (String s : sortedList) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }
    @Override
    public CommandResult execute(String[] args) {
        if (graph == null) {
            return new CommandResult(NO_DATA_LOADED, false);
        }
        try {
            return new CommandResult(listToString(descent(args, graph)), true);
        } catch (InvalidArgumentException e) {
            return new CommandResult(e.getMessage(), false);
        }

    }
}
