package exam.graph;

import exam.graph.exception.InvalidRelationException;
import exam.parsing.Type;

import java.util.ArrayList;


/**
 * Represents a category in the graph.
 * Each category may contain subcategories and products.
 * @author uepiy
 */

public class Category implements Node {
    private final String name;
    private final ArrayList<Edge> edges;
    /**
     * Constructs a new category node with the given name.
     * @param name the name of the node
     */
    public Category(String name) {
        this.name = name.toLowerCase();
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
     * @throws InvalidRelationException if the relation is not contained_in or contains
     */
    public void addEdge(Edge edge) throws InvalidRelationException {
        if (edges.contains(edge)) {
            return;
        }
        if (edges.contains(new Edge(edge.relation().inverse(), edge.left(), edge.right()))) {
            throw new InvalidRelationException(edge.relation().toString().toLowerCase(), Type.SEMANTIC);
        }
        if (edge.relation() != Relation.CONTAINED_IN && edge.relation() != Relation.CONTAINS) {
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
        Category category = (Category) obj;
        return name.equals(category.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public String toString() {
        return name;
    }
}
