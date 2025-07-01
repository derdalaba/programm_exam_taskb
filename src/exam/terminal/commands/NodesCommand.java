package exam.terminal.commands;

import exam.graph.Category;
import exam.graph.Graph;
import exam.graph.Node;
import exam.graph.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the nodes command.
 * Lists all the nodes in the terminal application.
 * @author uepiy
 */

public class NodesCommand implements GraphHoldingCommand {
    private static final String WHITE_SPACE = " ";
    private Graph graph;
    /**
     * Constructs a new NodesCommand with the given graph.
     * @param graph the graph to list nodes from
     */
    public NodesCommand(Graph graph) {
        this.graph = graph;
    }
    @Override
    public void updateGraph(Graph graph) {
        if (graph == null) {
            return;
        }
        this.graph = graph;
    }
    private List<String> getProducts(List<Node> nodes) {
        List<String> productList = new ArrayList<>();
        for (Node node : nodes) { // Add each product to the list as a string
            if (node instanceof Product) {
                productList.add(node.toString());
            }
        }
        Collections.sort(productList); // Sort the products alphabetically
        return productList;
    }
    private List<String> getCategories(List<Node> nodes) {
        List<String> categoryList = new ArrayList<>();
        for (Node node : nodes) { // Add each category to the list as a string
            if (node instanceof Category) {
                categoryList.add(node.toString());
            }
        }
        Collections.sort(categoryList); // Sort the categories alphabetically
        return categoryList;
    }
    private List<String> getNodeList() {
        List<Node> nodes = this.graph.getNodes();
        List<String> nodeList = new ArrayList<>(getProducts(nodes));
        nodeList.addAll(getCategories(nodes));
        return nodeList;
    }
    private String listToLine(List<String> list) {
        StringBuilder line = new StringBuilder();
        for (String item : list) {
            line.append(item).append(WHITE_SPACE);
        }
        return line.toString().trim();
    }
    @Override
    public CommandResult execute(String[] args) {
        if (graph == null) {
            return new CommandResult(NO_DATA_LOADED, false);
        }
        return new CommandResult(listToLine(getNodeList()), true);
    }
}
