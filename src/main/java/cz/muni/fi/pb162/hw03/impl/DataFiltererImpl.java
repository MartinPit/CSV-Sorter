package cz.muni.fi.pb162.hw03.impl;


import cz.muni.fi.pb162.hw03.impl.DataOutput.Output;
import cz.muni.fi.pb162.hw03.impl.DataOutput.OutputFile;
import io.github.jcechace.edu.pb162.csv.CsvParser;
import io.github.jcechace.edu.pb162.csv.CsvToolkit;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Implementation of the {@link DataFilterer} interface
 *
 * @author Martin Oliver Pitonak
 */
public class DataFiltererImpl implements DataFilterer {

    private final Path filterFile;
    private final Charset charset;
    private final String delim;
    private final List<Output> outputs;

    /**
     *
     *  Construct a new {@link DataFilterer}
     *
     * @param filterFile file of filters to filter by
     * @param charset charset used
     * @param delim delimiter used for the separation of the columns
     */
    public DataFiltererImpl(Path filterFile, Charset charset, String delim) {
        this.filterFile = filterFile;
        this.charset = charset;
        this.delim = delim;
        this.outputs = new ArrayList<>();
    }

    @Override
    public void filter(Path dataFile, Path outputDir, String labels) throws IOException {
        CsvParser parser = CsvToolkit.parser(delim, charset);
        fillOutputList(outputDir, filterFile);

        try (var reader = parser.openWithHeader(dataFile)) {
            Map<String, String> line;
            while ((line = reader.read()) != null) {
                evaluateAndWrite(line, labels);
            }
        }
    }

    /**
     *
     * Evaluates the given line by its labels and if it matches, then
     * it writes it into the output
     *
     * @param line line to evaluate
     * @param labels name of the label column
     * @throws IOException throws of writing fails
     */
    private void evaluateAndWrite(Map<String, String> line, String labels) throws IOException {
        for (Output op : outputs) {
            if (! op.checkMatch(line.get(labels))) {
                continue;
            }

            if (! op.isInitialized()) {
                op.initialize(new ArrayList<>(line.keySet()), charset, delim);
            }

            op.write(new ArrayList<>(line.values()), charset, delim);
        }
    }

    /**
     *
     * Helper method for the constructor, that simply
     * fills a list attribute with the outputs specified
     * in the filter file
     *
     * @param dir directory where the files should be written
     * @param filters file where the filters are saved
     * @throws IOException throws if reading fails
     */
    private void fillOutputList(Path dir, Path filters) throws IOException {
        CsvParser parser = CsvToolkit.parser(delim, charset);

        try (var reader = parser.open(filters)) {
            reader.read();  // * read the first header line
            List<String> line;
            while ((line = reader.read()) != null) {
                addOutput(dir, line);
            }
        }
    }

    /**
     *
     * Helper method for the fillOutputList method, that
     * crates a single output and adds it to the list.
     *
     * @param dir directory of the outputs
     * @param line one filter line read from the filter file containing
     *             a name and expression to filter by
     */
    private void addOutput(Path dir, List<String> line) {
        Path filepath = dir.resolve(line.get(0) + ".csv");
        Output op = new OutputFile(filepath, line.get(1));
        outputs.add(op);
    }
}
