package exam.terminal.commands;

import exam.graph.Edge;
import exam.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents the edges command.
 * Lists the edges of the graph.
 * @author uepiy
 */

public class EdgesCommand implements GraphHoldingCommand {
    private static final String LINE_FORMAT = "%s-[%s]->%s";
    private Graph graph;
    /**
     * Constructs a new EdgesCommand with the given graph.
     * @param graph
     */
    public EdgesCommand(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void updateGraph(Graph graph) {
        if (graph == null) {
            return;
        }
        this.graph = graph;
    }
    private List<Edge> sortByRelation(List<Edge> edges) {
        HashMap<String, List<Edge>> relationMap = new HashMap<>();
        relationMap.put("contains", new ArrayList<>());
        relationMap.put("contained-in", new ArrayList<>());
        relationMap.put("part-of", new ArrayList<>());
        relationMap.put("has-part", new ArrayList<>());
        relationMap.put("successor-of", new ArrayList<>());
        relationMap.put("predecessor-of", new ArrayList<>());
        for (Edge edge : edges) {
            String relation = edge.relation().toString().toLowerCase().replaceAll("_", "-");
            relationMap.get(relation).add(edge);
        }
        List<Edge> sortedEdges = new ArrayList<>();
        for (List<Edge> edgeList : relationMap.values()) {
            sortedEdges.addAll(edgeList);
        }
        return sortedEdges;
    }
    private TreeMap<String, List<Edge>> sortLeft(List<Edge> edges) {
        TreeMap<String, List<Edge>> leftMap = new TreeMap<>();
        for (Edge edge : edges) {
            String left = edge.left().toString();
            if (!leftMap.containsKey(left)) {
                leftMap.put(left, new ArrayList<>());
            }
            leftMap.get(left).add(edge);
        }
        return leftMap;
    }
    private List<Edge> sort(List<Edge> edges) {
        // 1. sort by right node
        // 2. sort by relation node
        TreeMap<String, List<Edge>> rightMap = new TreeMap<>();
        for (Edge edge : edges) {
            String right = edge.right().toString();
            if (!rightMap.containsKey(right)) {
                rightMap.put(right, new ArrayList<>());
            }
            rightMap.get(right).add(edge);
        }
        List<Edge> sortedEdges = new ArrayList<>();
        for (List<Edge> rightEdges : rightMap.values()) {
            sortedEdges.addAll(sortByRelation(rightEdges));
        }
        return sortedEdges;
    }
    private List<Edge> lsd(List<Edge> edges) {
        //LSD sort the lines first by the relation, then by the right node, then by the left node
        // 1. sort by left node
        // 2. sort by right node
        // 3. sort by relation node
        TreeMap<String, List<Edge>> leftMap = sortLeft(edges);
        List<Edge> sortedEdges = new ArrayList<>();
        for (List<Edge> leftEdges : leftMap.values()) {
            sortedEdges.addAll(sort(leftEdges));
        }
        return sortedEdges;
    }
    private List<String> formatLines(List<Edge> edges) {
        List<String> lines = new ArrayList<>();
        for (Edge edge : edges) {
            lines.add(String.format(LINE_FORMAT, edge.left().toString(),
                    edge.relation().toString().toLowerCase().replaceAll("_", "-"), edge.right().toString()));
        }
        return lines;
    }
    /**
     * Executes the edges command.
     * @param args the arguments to pass to the command
     * @return the result of the command
     */
    @Override
    public CommandResult execute(String[] args) {
        if (graph == null) {
            return new CommandResult(NO_DATA_LOADED, false);
        }
        if (!(args.length == 1 && args[0].isEmpty())) {
            return new CommandResult(NO_ARG_COMMAND, false);
        }
        List<Edge> edges = graph.getEdges();
        List<String> lines = formatLines(lsd(edges));
        return new CommandResult(lineListToString(lines), true);
    }

}
