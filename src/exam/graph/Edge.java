package exam.graph;

/**
 * Represents an edge in a graph.
 * Each edge connects two nodes and has a relation.
 * The relation is directional, meaning that the order of the nodes matters.
 * @param relation the relation between the two nodes
 * @param left the first node
 * @param right the second node
 * @author uepiy
 */

public record Edge(Relation relation, Node left, Node right) {
    /**
     * Checks if the edge contains the given node.
     * @return true if the edge contains the given node
     */
    public boolean contains(Node node) {
        return left.equals(node) || right.equals(node);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return relation == edge.relation && left.equals(edge.left) && right.equals(edge.right);
    }
    @Override
    public int hashCode() {
        return relation.hashCode() + left.hashCode() + right.hashCode();
    }
    @Override
    public String toString() {
        return  "%s -> %s [label=%s]".formatted(left.getName(),
                right.getName(),
                relation.toString().toLowerCase().replace("_", ""));
    }
}
