package exam.parsing;

import exam.terminal.exception.InvalidArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a utility class for handling file operations.
 * This class provides a method to read all lines from a file.
 * @author uepiy
 */

public final class FileHandle {
    private static final String PATH_REGEX = ".*\\.txt";
    private FileHandle() {
    }
    /**
     * Reads all lines from the file at the given path.
     * @param path the path to the file
     * @return a list of strings representing the lines in the file
     * @throws InvalidArgumentException if the path is invalid
     */
    public static List<String> getLines(String path) throws InvalidArgumentException {
        if (!path.matches(PATH_REGEX)) {
            throw new InvalidArgumentException(path);
        }
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new InvalidArgumentException(path);
        }
    }
}
