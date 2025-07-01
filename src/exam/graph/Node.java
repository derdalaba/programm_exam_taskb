package exam.graph;

import exam.graph.exception.InvalidRelationException;

import java.util.List;

/**
 * Represents a node in a graph.
 * Each node also holds a list of edges that connect it to other nodes.
 * @author uepiy
 */

public interface Node {
    /**
     * Returns the name of the node.
     * @return the name of the node
     */
    String getName();
    /**
     * Returns the list of edges connected to this node.
     * @return the list of edges connected to this node
     */
    List<Edge> getEdges();
    /**
     * Adds an edge to the list of edges connected to this node.
     * @param edge the edge to add
     * @throws InvalidRelationException if the relation is not allowed in the context of the graph
     */
    void addEdge(Edge edge) throws InvalidRelationException;
    /**
     * Removes an edge from the list of edges connected to this node.
     * @param edge the edge to remove
     * @return true if the edge was removed, false otherwise
     */
    boolean removeEdge(Edge edge);
    /**
     * Returns a string representation of the node.
     * @return a string representation of the node
     */
    String toString();
}
