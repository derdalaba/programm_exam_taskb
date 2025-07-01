package exam.graph.exception;

import exam.parsing.Type;

/**
 * Represents an exception thrown when an invalid relation is given to the graph.
 * The exception message should describe the invalid relation.
 * The relation is invalid if it is not defined in the Relation enum.
 * Relations can also be invalid if they are not allowed in the context of the graph.
 * @see exam.graph.Relation
 * @author uepiy
 */

public class InvalidRelationException extends Exception {
    private static final String PREFIX = "Invalid relation: %s";
    private final Type type;
    /**
     * Constructs a new InvalidRelationException with the given invalid relation.
     * @param message the relation that is invalid
     * @param type the type of the error
     */
    public InvalidRelationException(String message, Type type) {
        super(PREFIX.formatted(message));
        this.type = type;
    }
    /**
     * Returns the type of the error.
     * @return the type of the error
     */
    public Type getType() {
        return type;
    }
}
