package exam.parsing;

import exam.graph.Category;
import exam.graph.Edge;
import exam.graph.Graph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Converts a graph to a list of strings in dot format.
 * The dot format is a simple text format for describing graphs.
 * @author uepiy
 */

public final class DotView {
    private static final String GRAPH_START = "digraph {";
    private static final String GRAPH_END = "}";
    private static final String CATEGORY_FORMAT = "%s [shape=box]";

    private DotView() {
    }
    private static List<String> getAllEdges(Graph graph) {
        List<Edge> edges = graph.getEdges();
        List<String> lines = new ArrayList<>();
        for (Edge edge : edges) {
            lines.add(edge.toString());
        }
        Collections.sort(lines);
        return lines;
    }
    private static List<String> getAllCategories(Graph graph) {
        List<Category> categories = graph.getCategories();
        List<String> categoryLines = new ArrayList<>();
        for (Category category : categories) {
            categoryLines.add(String.format(CATEGORY_FORMAT, category.getName()));
        }
        Collections.sort(categoryLines);
        return categoryLines;
    }

    /**
     * Converts the given graph to a list of strings in dot format.
     * @param graph the graph to convert
     * @return the list of strings in dot format
     */
    public static List<String> toDot(Graph graph) {
        List<String> dot = new ArrayList<>();
        dot.add(GRAPH_START);
        dot.addAll(getAllEdges(graph));
        dot.addAll(getAllCategories(graph));
        dot.add(GRAPH_END);
        return dot;
    }
}
