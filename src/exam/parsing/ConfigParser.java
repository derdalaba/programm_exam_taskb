package exam.parsing;

import exam.graph.Category;
import exam.graph.Graph;
import exam.graph.Node;
import exam.graph.Product;
import exam.graph.Relation;
import exam.graph.exception.InvalidRelationException;
import exam.parsing.exception.GraphParseException;
import exam.parsing.exception.SyntaxException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a parser for dot graph configuration files.
 * @author uepiy
 */

public final class ConfigParser {
    private static final String ERROR_TYPE_FORMAT = "%s error: ";
    private static final String NUMERIC_PATTERN = "[0-9]+";
    private static final String NAME_PATTERN = "[a-zA-Z0-9]+";
    private static final String ID_PATTERN = "\\(\\s*id\\s*=\\s*[0-9]+\\s*\\)";
    private static final String POSITION_FORMAT = " at line %d";
    private static final String WHITE_SPACE_PATTERN = " ";
    private static final String OPTIONAL_WHITESPACE_PATTERN = "\\s*?";
    private static final String PREDICATE_GROUP_PATTERN = "(contains|contained-in|part-of|has-part|"
        + "successor-of|predecessor-of)";

    private ConfigParser() {
    }

    /**
     * Parses the given list of lines into a graph.
     * @param lines the list of lines to parse
     * @return the graph
     * @throws GraphParseException if a syntax/semantic error is encountered
     */
    public static Graph parse(List<String> lines) throws GraphParseException {
        if (lines == null || lines.isEmpty()) {
            throw new GraphParseException("file is empty");
        }
        Graph graph = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            try {
                parseLine(graph, lines.get(i));
            } catch (SyntaxException e) {
                throw new GraphParseException(e.getMessage() + POSITION_FORMAT.formatted(i + 1));
            } catch (InvalidRelationException e) {
                throw new GraphParseException(ERROR_TYPE_FORMAT.formatted(e.getType()) + e.getMessage() + POSITION_FORMAT.formatted(i + 1));
            }
        }
        return graph;
    }
    private static String getSubject(String line) {
        return line.split(PREDICATE_GROUP_PATTERN)[0].trim();
    }
    /**
     * Returns the predicate from the given line.
     * @param line the line to parse
     * @return the predicate
     * @throws SyntaxException if the predicate is invalid
     */
    public static String getPredicate(String line) throws SyntaxException {
        if (line.contains("contains")) {
            return "contains";
        }
        if (line.contains("contained-in")) {
            return "contained-in";
        }
        if (line.contains("part-of")) {
            return "part-of";
        }
        if (line.contains("has-part")) {
            return "has-part";
        }
        if (line.contains("successor-of")) {
            return "successor-of";
        }
        if (line.contains("predecessor-of")) {
            return "predecessor-of";
        }
        throw new SyntaxException("invalid predicate: " + line);
    }
    private static String getObject(String line) {
        return line.split(PREDICATE_GROUP_PATTERN)[1].trim();
    }
    /**
     * Parses the id from the given node id string.
     * @param id the node id string to parse
     * @return the id
     */
    public static int parseId(String id) {
        String idPart = id.substring(id.indexOf("id"));
        Matcher matcher = Pattern.compile(NUMERIC_PATTERN).matcher(idPart);
        return matcher.find() ? Integer.parseInt(matcher.group()) : -1;
    }
    private static Node parseNode(String node) throws SyntaxException {
        if (node.startsWith(PREDICATE_GROUP_PATTERN)) {
            throw new SyntaxException("invalid token: " + node);
        }
        if (node.matches(NAME_PATTERN)) {
            return new Category(node);
        }
        if (node.matches(NAME_PATTERN + OPTIONAL_WHITESPACE_PATTERN + ID_PATTERN)) {
            String formatted = node.replaceAll(WHITE_SPACE_PATTERN, "");
            String name = node.trim().substring(0, formatted.indexOf("("));
            int idValue = parseId(node);
            if (idValue == -1) {
                throw new SyntaxException("invalid id: " + node);
            }
            return new Product(name, idValue);
        }
        throw new SyntaxException("invalid node : " + node);
    }

    /**
     * Parses the given line and adds the corresponding edge to the graph.
     * @param graph the graph to add the edge to
     * @param line the line to parse
     * @throws SyntaxException if the line is not in the correct format
     * @throws InvalidRelationException if the relation is not allowed in the context of the graph
     */
    public static void parseLine(Graph graph, String line) throws SyntaxException, InvalidRelationException {
        String subject = getSubject(line);
        String predicate = getPredicate(line);
        String object = getObject(line);
        Node source = parseNode(subject);
        Node target = parseNode(object);
        graph.addEdge(source, target, Relation.valueOf(predicate.toUpperCase().replace("-", "_")));
    }
}
