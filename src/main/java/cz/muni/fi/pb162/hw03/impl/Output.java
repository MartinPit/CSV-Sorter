package cz.muni.fi.pb162.hw03.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
     *
     * Writes our data specified as a map into the output,
     *
     * @param content what data to write
     * @throws IOException if the writing fails
     */
    void write(Map<String, String> content) throws IOException;
}
