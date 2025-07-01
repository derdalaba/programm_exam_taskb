package exam.parsing;

/**
 * Represents a type of error in the parsing process.
 * Syntax errors are errors that occur when the parser encounters an invalid token or sequence of tokens.
 * Semantic errors are errors that occur when the parser encounters a valid token or sequence of tokens,
 * but the token(s) are not valid in the context in which they are used.
 * @author uepiy
 */

public enum Type {
    /**
     * Represents a syntax error in the parsing process.
     */
    SYNTAX,
    /**
     * Represents a semantic error in the parsing process.
     */
    SEMANTIC
}
