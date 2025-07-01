package exam.graph;

import exam.graph.exception.InvalidRelationException;
import exam.terminal.exception.InvalidArgumentException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;


import static exam.parsing.ConfigParser.parseId;

/**
 * Represents a graph.
 * The graph is a collection of nodes and edges.
 * Each node may be a category or a product.
 * Each edge connects two nodes.
 * The graph is directed and may contain multi edges.
 * @author uepiy
*/
public class Graph {
    private static final String NAME_PATTERN = "[a-zA-Z0-9]+";
    private static final String ID_PATTERN = "\\(\\s*id\\s*=\\s*[0-9]+\\s*\\)";
    private HashMap<String, Node> nodeMap;

    /**
     * Constructs a new graph.
     */
    public Graph() {
        this.nodeMap = new HashMap<>();
    }
    /**
     * Gets all nodes in the graph.
     * @return the list of nodes in the graph
     */
    public List<Node> getNodes() {
        return new ArrayList<>(nodeMap.values());
    }
    /**
     * Gets a node from the graph.
     * @param name the name of the node to get
     * @return the node with the given name
     * @throws InvalidArgumentException if the node is not found
     */
    public Node getNode(String name) throws InvalidArgumentException {
        String nodeName = name.toLowerCase();
        if (nodeName.matches(NAME_PATTERN + ID_PATTERN)) {
            String[] parts = nodeName.split("\\(");
            int id = parseId(parts[1]);
            if (!nodeMap.containsKey(parts[0])) {
                throw new InvalidArgumentException("Node not found");
            }
            Product product = (Product) nodeMap.get(parts[0]);
            if (product.getId() != id) {
                throw new InvalidArgumentException("Node not found");
            }
            return product;
        }
        if (!nodeMap.containsKey(nodeName)) {
            throw new InvalidArgumentException("Node not found");
        }
        return nodeMap.get(nodeName);
    }
    /**
     * Removes a node from the graph.
     * @param name the name of the node to remove
     */
    public void removeNode(String name) {
        nodeMap.remove(name);
    }
    /**
     * Returns all category nodes in the graph.
     * @return the list of category nodes in the graph
     */
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        for (Node node : nodeMap.values()) {
            if (node instanceof Category) {
                categories.add((Category) node);
            }
        }
        return categories;
    }
    /**
     * Adds a new edge to the graph.
     * @param source the source node
     * @param target the target node
     * @param relation the relation between the two nodes
     * @throws InvalidRelationException if the relation is not allowed in the context of the graph
     */
    public void addEdge(Node source, Node target, Relation relation) throws InvalidRelationException {
        if (!nodeMap.containsKey(source.getName())) {
            nodeMap.put(source.getName(), source);
        }
        if (!nodeMap.containsKey(target.getName())) {
            nodeMap.put(target.getName(), target);
        }
        Node sourceNode = nodeMap.get(source.getName());
        Node targetNode = nodeMap.get(target.getName());
        Edge edge = new Edge(relation, sourceNode, targetNode);
        Edge inverseEdge = new Edge(relation.inverse(), targetNode, sourceNode);
        sourceNode.addEdge(edge);
        targetNode.addEdge(inverseEdge);

    }
    /**
     * Returns the list of edges in the graph.
     * @return the list of edges in the graph
     */
    public List<Edge> getEdges() {
        List<Edge> allEdges = new ArrayList<>();
        for (Node node : nodeMap.values()) {
            allEdges.addAll(node.getEdges());
        }
        return allEdges;
    }
    /**
     * Returns the siblings of a product node.
     * The siblings are the products that share the same parent category.
     * @param id the id of the product node
     * @return the list of sibling product nodes
     * @throws InvalidArgumentException if the node is not found
     */
    public List<String> getSiblings(int id) throws InvalidArgumentException {
        Product source = null;
        for (Node node : nodeMap.values()) {
            if (node instanceof Product product) {
                if (product.getId() == id) {
                    source = product;
                }
            }
        }
        if (source == null) {
            throw new InvalidArgumentException("Node not found");
        }
        List<String> siblings = new ArrayList<>();
        for (Edge edge : source.getEdges()) {
            if (edge.relation() == Relation.CONTAINED_IN) {
                Category parent = (Category) edge.right();
                for (Edge siblingEdge : parent.getEdges()) {
                    if (siblingEdge.relation() == Relation.CONTAINS) {
                        if (siblingEdge.right().equals(source)) {
                            continue;
                        }
                        siblings.add(siblingEdge.right().toString());
                    }
                }
            }
        }
        return siblings;
    }

    /**
     * Returns the successors of a product node.
     * @param id the id of the product node
     * @return the list of successor product nodes
     * @throws InvalidArgumentException if the node is not found
     */
    public List<String> getSuccessors(int id) throws InvalidArgumentException {
        List<Node> nodes = getNodes();
        Product source = null;
        for (Node node : nodes) {
            if (node instanceof Product product) {
                if (product.getId() == id) {
                    source = product;
                }
            }
        }
        if (source == null) {
            throw new InvalidArgumentException("Node not found");
        }
        List<String> successors = new ArrayList<>();
        for (Edge edge : source.getEdges()) {
            if (edge.relation() == Relation.PREDECESSOR_OF) {
                successors.add(edge.right().toString());
                Product next = (Product) edge.right();
                successors.addAll(getSuccessors(next.getId()));
            }
        }
        successors.remove(source.toString());
        return successors;
    }
    /**
     * Returns the predecessors of a product node.
     * @param id the id of the product node
     * @return the list of predecessor product nodes
     * @throws InvalidArgumentException if the node is not found
     */
    public List<String> getPredecessors(int id) throws InvalidArgumentException {
        Product source = null;
        for (Node node : nodeMap.values()) {
            if (node instanceof Product product) {
                if (product.getId() == id) {
                    source = product;
                }
            }
        }
        if (source == null) {
            throw new InvalidArgumentException("Node not found");
        }
        List<String> predecessors = new ArrayList<>();
        for (Edge edge : source.getEdges()) {
            if (edge.relation() == Relation.SUCCESSOR_OF) {
                predecessors.add(edge.right().toString());
                Product prev = (Product) edge.right();
                predecessors.addAll(getPredecessors(prev.getId()));
            }
        }
        return predecessors;
    }
}
