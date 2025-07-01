package exam.graph;

/**
 * Represents the relation between two nodes in a graph.
 * The relation is directional, meaning that the order of the nodes matters.
 * @author uepiy
 */

public enum Relation {
    /**
     * The first node is a super category of the second node.
     */
    CONTAINS,
    /**
     * The first node is a sub category of the second node. Inverse of CONTAINS.
     */
    CONTAINED_IN,
    /**
     * The first node is part of the second node.
     */
    PART_OF,
    /**
     * The second node is part of the first node. Inverse of PART_OF.
     */
    HAS_PART,
    /**
     * The first node is a successor of the second node.
     */
    SUCCESSOR_OF,
    /**
     * The first node is a predecessor of the second node. Inverse of SUCCESSOR_OF.
     */
    PREDECESSOR_OF;

    /**
     * Returns the inverse of the relation.
     * @return the inverse of the relation
     * @throws IllegalStateException if the relation is not one of the valid relations
     */
    public Relation inverse() {
        return switch (this) {
            case CONTAINS -> CONTAINED_IN;
            case CONTAINED_IN -> CONTAINS;
            case HAS_PART -> PART_OF;
            case PART_OF -> HAS_PART;
            case SUCCESSOR_OF -> PREDECESSOR_OF;
            case PREDECESSOR_OF -> SUCCESSOR_OF;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
