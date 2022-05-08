package cz.muni.fi.pb162.hw03.impl.DataOutput;

import io.github.jcechace.edu.pb162.csv.CsvParser;
import io.github.jcechace.edu.pb162.csv.CsvToolkit;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Oliver Pitonak
 */
public class OutputGroup {

    private final Path directory;
    private final String delim;
    private final List<String> header;
    private final List<Output> outputs;

    /**
     * f
     * @param directory f
     * @param header f
     * @param delim f
     */
    public OutputGroup(Path directory, List<String> header, String delim) {
        this.header = header;
        this.outputs = new ArrayList<>();
        this.directory = directory;
        this.delim = delim;
    }

    /**
     * f
     * @return f
     */
    public List<Output> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    /**
     * f
     * @param filterFile f
     * @param charset f
     * @throws IOException f
     */
    public void initGroup(Path filterFile, Charset charset) throws IOException {
        CsvParser parser = CsvToolkit.parser();

        try (var reader = parser.openWithHeader(filterFile)) {
            Map<String, String> line;
            while ((line = reader.read()) != null) {
                List<String> values = new ArrayList<>(line.values());
                addOutput(values.get(0), values.get(1), charset);
            }
        }
    }

    private void addOutput(String name, String expression, Charset charset) throws IOException {
        Output op = new OutputFile(directory, name, delim, expression, charset);
        op.initialize(header);
        outputs.add(op);
    }
}
