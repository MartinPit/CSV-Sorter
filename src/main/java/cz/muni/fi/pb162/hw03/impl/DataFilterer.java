package cz.muni.fi.pb162.hw03.impl;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Martin Oliver Pitonak
 */
public interface DataFilterer {

    /**
     *
     * filters
     * @param dataFile f
     * @param outputDir f
     * @param columnName f
     * @param delim f
     * @throws IOException f
     */
    void filter(Path dataFile, Path outputDir, String columnName, String delim) throws IOException;
}
