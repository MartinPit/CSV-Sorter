package cz.muni.fi.pb162.hw03.impl.DataOutput;

import java.io.IOException;
import java.util.List;

/**
 *
 * Represents an output where we can write
 * the filtered data
 *
 * @author Martin Oliver Pitonak
 */
public interface Output {
    /**
     *
     * Initializes the output with a header line
     *
     * @param header the first line
     * @throws IOException if the initialization or writing fails
     */
    void initialize(List<String> header) throws IOException;

    /**
     * f
     * @param line f
     * @param index f
     * @throws IOException f
     */
    void writeIfMatches(List<String> line, int index) throws IOException;

}
