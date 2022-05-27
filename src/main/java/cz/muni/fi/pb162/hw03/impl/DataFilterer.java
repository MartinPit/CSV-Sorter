package cz.muni.fi.pb162.hw03.impl;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * A data filter, which filters
 * based on the given file of filters
 *
 * @author Martin Oliver Pitonak
 */
public interface DataFilterer {

    /**
     *
     * Filters the given data saved in a file and outputs
     * the filtered data into the specified directory
     *
     * @param dataFile file filled with data to filter
     * @param outputDir where to put the filtered data
     * @param columnName name of the column with the labels used for filtering
     * @throws IOException throws if reading or writing failed
     */
    void filter(Path dataFile, Path outputDir, String columnName) throws IOException;
}
