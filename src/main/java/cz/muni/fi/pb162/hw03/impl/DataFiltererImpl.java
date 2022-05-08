package cz.muni.fi.pb162.hw03.impl;


import cz.muni.fi.pb162.hw03.impl.DataOutput.Output;
import cz.muni.fi.pb162.hw03.impl.DataOutput.OutputGroup;
import io.github.jcechace.edu.pb162.csv.CsvParser;
import io.github.jcechace.edu.pb162.csv.CsvToolkit;
import io.github.jcechace.edu.pb162.csv.reader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Martin Oliver Pitonak
 */
public class DataFiltererImpl implements DataFilterer {

    private final Path filterFile;
    private final Charset charset;

    /**
     *  f
     * @param filterFile f
     * @param charset f
     */
    public DataFiltererImpl(Path filterFile, Charset charset) {
        this.filterFile = filterFile;
        this.charset = charset;
    }

    @Override
    public void filter(Path dataFile, Path outputDir, String columnName, String delim) throws IOException {
        CsvParser parser = CsvToolkit.parser(delim, charset);

        try (CsvReader<List<String>> reader = parser.open(dataFile)) {

            List<String> header = getHeader(dataFile, delim, reader);
            OutputGroup outputs = new OutputGroup(outputDir, header, delim);
            outputs.initGroup(filterFile, charset);

            int expressionIndex = getIndex(header, columnName);

            List<String> line;
            while ((line = reader.read()) != null) {
                evaluate(outputs, line, expressionIndex);
            }
        }


    }

    private void evaluate(OutputGroup outputs, List<String> line, int expressionIndex) throws IOException {
        for (Output op : outputs.getOutputs()) {
            op.writeIfMatches(line, expressionIndex);
        }
    }

    private int getIndex(List<String> header, String columnName) {
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equals(columnName)) {
                return i;
            }
        }
        return -1;  // ? Maybe throw an exception?
    }

    private List<String> getHeader(Path dataFile, String delim, CsvReader<List<String>> reader) throws IOException {
            return reader.read();
    }
}
