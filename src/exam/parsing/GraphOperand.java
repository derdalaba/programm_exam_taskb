package exam.parsing;

import exam.graph.Edge;
import exam.graph.Graph;
import exam.graph.Node;
import exam.parsing.exception.GraphOperandException;
import exam.parsing.exception.SyntaxException;
import exam.terminal.exception.InvalidArgumentException;

import exam.graph.Relation;

import static exam.parsing.ConfigParser.getPredicate;

/**
 * Represents a utility class for graph operands.
 * An operand is a command that modifies the graph.
 * @author uepiy
 */

public final class GraphOperand {
    private static final String PREDICATE_GROUP_PATTERN = "(contains|contained-in|part-of|has-part|"
            + "successor-of|predecessor-of)";
    private GraphOperand() {
    }
    private static Relation getRelation(String edge) throws SyntaxException {
        String predicate = getPredicate(edge);
        return Relation.valueOf(predicate.toUpperCase().replace("-", "_"));
    }
    private static void removeEdge(Graph graph, Edge edgeToRemove, Node source) throws GraphOperandException {
        if (!source.removeEdge(edgeToRemove)) {
            throw new GraphOperandException("remove: Edge not found");
        }
    }

    /**
     * Removes an edge from the graph. Removes nodes if they have no edges.
     * @param graph the graph
     * @param edge the edge to remove
     * @throws GraphOperandException if the edge is not found
     */
    public static void removeEdge(Graph graph, String edge) throws GraphOperandException {
        try {
            Node source = graph.getNode(edge.split(PREDICATE_GROUP_PATTERN)[0]);
            Node target = graph.getNode(edge.split(PREDICATE_GROUP_PATTERN)[1]);
            if (source == null) {
                throw new GraphOperandException("remove: Source node not found: " + edge.split(PREDICATE_GROUP_PATTERN)[0]);
            }
            if (target == null) {
                throw new GraphOperandException("remove: Target node not found: " + edge.split(PREDICATE_GROUP_PATTERN)[1]);
            }
            Edge edgeToRemove = new Edge(getRelation(edge), source, target);
            Edge inverseEdgeToRemove = new Edge(getRelation(edge).inverse(), target, source);
            removeEdge(graph, edgeToRemove, source);
            removeEdge(graph, inverseEdgeToRemove, target);
            if (source.getEdges().isEmpty()) {
                graph.removeNode(source.getName());
            }
            if (target.getEdges().isEmpty()) {
                graph.removeNode(target.getName());
            }
        } catch (InvalidArgumentException | SyntaxException e) {
            throw new GraphOperandException(e.getMessage());
        }
    }
}
