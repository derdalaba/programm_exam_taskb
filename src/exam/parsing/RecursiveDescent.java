package exam.parsing;

import exam.graph.Graph;
import exam.terminal.exception.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recursive descent parser for a simple grammar.
 * The grammar is as follows:
 * term -> final | INTERSECTION(term, term) | UNION(term, term)
 * final -> STRATEGY_PATTERN PRODUCT_ID_PATTERN
 * STRATEGY_PATTERN -> S1 | S2 | S3
 * PRODUCT_ID_PATTERN -> [0-9]+
 * @author uepiy
 */

public final class RecursiveDescent {
    private static final String INTERSECTION_TOKEN = "INTERSECTION";
    private static final String UNION_TOKEN = "UNION";
    private static final String STRATEGY_PATTERN = "(S1|S2|S3)";
    private static final String PRODUCT_ID_PATTERN = "[0-9]+";
    private static final String FINAL_PATTERN = STRATEGY_PATTERN + " " + PRODUCT_ID_PATTERN;
    private RecursiveDescent() {
    }
    private static List<String> intersection(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        for (String item : list1) {
            if (list2.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }
    private static List<String> union(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>(list1);
        for (String item : list2) {
            if (!result.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }
    private static List<String> leftTerm(String[] input, int position, Graph graph) throws InvalidArgumentException {

        if (input[position].matches(STRATEGY_PATTERN)) {
            return executeStrategy(input[position] + " " + input[position + 1], graph);
        }
        if (input[position].startsWith(INTERSECTION_TOKEN) || input[position].startsWith(UNION_TOKEN)) {
            if (input[position].matches(INTERSECTION_TOKEN)) {
                if (input[position + 1].matches(STRATEGY_PATTERN)) {
                    return intersection(executeStrategy(input[position + 1] + input[position + 2], graph),
                            rightTerm(input, position + 3, graph));
                } else {
                    if (input[position + 1].matches(STRATEGY_PATTERN)) {
                        return intersection(leftTerm(input, position + 1, graph), rightTerm(input, position + 3, graph));
                    } else {
                        return intersection(leftTerm(input, position + 1, graph), leftTerm(input, position + 3, graph));
                    }
                }
            } else {
                if (input[position].startsWith(UNION_TOKEN)) {
                    return union(executeStrategy(input[position].split(",")[0] + " " + input[position + 1].split(",")[0], graph),
                            executeStrategy(input[position + 1].split(",")[1] + " " + input[position + 2], graph));
                } else
                    if (input[position + 1].matches(STRATEGY_PATTERN)) {
                        return intersection(executeStrategy(input[position + 1] + input[position + 2], graph),
                            rightTerm(input, position + 3, graph));
                    } else {
                        if (input[position + 1].matches(STRATEGY_PATTERN)) {
                            return union(leftTerm(input, position + 1, graph), rightTerm(input, position + 3, graph));
                        } else {
                            return union(leftTerm(input, position + 1, graph), leftTerm(input, position + 3, graph));
                        }
                    }
            }
        } else {
            throw new InvalidArgumentException("Invalid input: " + input[position]);
        }
    }
    private static List<String> rightTerm(String[] input, int position, Graph graph) throws InvalidArgumentException {
        if (input[position].startsWith(STRATEGY_PATTERN)) {
            return executeStrategy(input[position] + " " + input[position + 1], graph);
        }
        return leftTerm(input, position, graph);
    }
    private static List<String> executeStrategy(String input, Graph graph) throws InvalidArgumentException {
        String inputFormatted = input.replaceAll("(UNION\\(|INTERSECTION\\()", "");
        String[] parts = inputFormatted.split(" ");
        String strategy = parts[0];
        try {
            int productId = Integer.parseInt(parts[1].replaceAll("([,)])", ""));
            return switch (strategy) {
                case "S1" -> graph.getSiblings(productId);
                case "S2" -> graph.getSuccessors(productId);
                case "S3" -> graph.getPredecessors(productId);
                default -> throw new InvalidArgumentException("Invalid strategy: " + strategy);
            };
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Invalid product id");
        }
    }
    /**
     * Parses the given input using recursive descent.
     * @param input the input to parse
     * @param graph the graph to use for getting the results
     * @return a list of strings representing the parsed input
     * @throws InvalidArgumentException if the input is invalid
     */
    public static List<String> descent(String[] input, Graph graph) throws InvalidArgumentException {
        if (input[0].matches(STRATEGY_PATTERN)) {
            return executeStrategy(input[0] + " " + input[1], graph);
        }
        return leftTerm(input, 0, graph);
    }
}
