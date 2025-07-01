package exam.graph;

import exam.graph.exception.InvalidRelationException;
import exam.parsing.Type;

import java.util.ArrayList;


/**
 * Represents a product in the graph.
 * Each product may be connected to other products or categories through edges.
 * A product cannot contain categories or subcategories.
 * @author uepiy
 */

public class Product implements Node {
    private static final String SEPARATOR = ":";
    private final String name;
    private final int id;
    private final ArrayList<Edge> edges;

    /**
     * Constructs a new product node with the given name and id.
     * @param name the name of the node
     * @param id the id of the node
     */
    public Product(String name, int id) {
        this.name = name.toLowerCase();
        this.id = id;
        this.edges = new ArrayList<>();
    }

    /**
     * Returns the name of the node.
     * @return the name of the node
     */
    @Override
    public String getName() {
        return name;
    }
    /**
     * Returns the id of the node.
     * @return the id of the node
     */
    public int getId() {
        return id;
    }
    /**
     * Returns the list of edges connected to this node.
     * @return the list of edges connected to this node
     */
    @Override
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    /**
     * Adds an edge to the list of edges connected to this node.
     * @param edge the edge to add
     * @throws InvalidRelationException if the relation is not allowed in the context of the graph
     */
    public void addEdge(Edge edge) throws InvalidRelationException {
        // check if edge already exists or is inverse of another existing edge
        if (edges.contains(edge)) {
            return;
        }
        if (edges.contains(new Edge(edge.relation().inverse(), edge.left(), edge.right()))) {
            throw new InvalidRelationException(edge.relation().toString().toLowerCase(), Type.SEMANTIC);
        }
        edges.add(edge);
    }

    @Override
    public boolean removeEdge(Edge edge) {
        for (Edge e : edges) {
            if (e.equals(edge)) {
                edges.remove(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return name.equals(product.name) && id == product.id;
    }
    @Override
    public int hashCode() {
        return (name + id).hashCode();
    }
    @Override
    public String toString() {
        return name + SEPARATOR + id;
    }
}
