package cz.muni.fi.pb162.hw03.impl.DataOutput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Represents an output where we can write
 * the filtered data, which also stores the
 * matcher necessary to determine if the data
 * is appropriate for this output
 *
 * @author Martin Oliver Pitonak
 */
public interface Output {
    /**
     * Initializes the output and writes a header line
     *
     * @param header  the first header line
     * @param charset charset used
     * @param delim   delimiter used between the columns
     * @throws IOException if the initialization or writing fails
     */
    void initialize(List<String> header, Charset charset, String delim) throws IOException;

    /**
     * Checks if the output is initialized (i.e. file is created,
     * or if it's generally ready to write to)
     *
     * @return true if it is initialized, false otherwise
     */
    boolean isInitialized();

    /**
     * Checks if the given {@link String} of labels
     * matches the expression saved
     *
     * @param labels string to check
     * @return true if there's a match, false otherwise
     */
    boolean checkMatch(String labels);

    /**
     *
     * Writes the data into the output, must be called after
     * at least one call to initialize.
     *
     * @param parts List of columns to write, separated by the delimiter
     * @param charset charset used
     * @param delim delimiter that separates the columns
     * @throws IOException throws if writing fails
     */
    void write(List<String> parts, Charset charset, String delim) throws IOException;


}
