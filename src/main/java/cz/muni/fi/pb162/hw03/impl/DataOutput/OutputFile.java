package cz.muni.fi.pb162.hw03.impl.DataOutput;

import cz.muni.fi.pb162.hw02.LabelMatcher;
import cz.muni.fi.pb162.hw02.impl.LabeledOperations;
import cz.muni.fi.pb162.hw03.impl.LabeledObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * Represents an output to a file
 *
 * @author Martin Oliver Pitonak
 */
public class OutputFile implements Output {

    private final Path filePath;
    private final LabelMatcher matcher;
    private boolean initialized;

    /**
     *
     * Creates the output with the given expression,
     * which evaluates if the data is adequate for this output
     *
     * @param filePath path to the output file
     * @param expression expression that will be used for matching
     */
    public OutputFile(Path filePath, String expression) {
        this.filePath = filePath;
        this.matcher = LabeledOperations.expressionMatcher(expression);
        this.initialized = false;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize(List<String> header, Charset charset, String delim) throws IOException {
        if (filePath.toFile().exists()) {
            if (! filePath.toFile().delete()) {
                throw new IOException("File deletion");
            }
        }

        if (! filePath.toFile().createNewFile()) {
            throw new IOException("File creation");
        }

        writeLine(createLine(header, delim), charset);
        initialized = true;
    }

    @Override
    public boolean checkMatch(String labels) {
        return matcher.matches(new LabeledObject(labels));
    }

    @Override
    public void write(List<String> parts, Charset charset, String delim) throws IOException {
        writeLine(createLine(parts, delim), charset);
    }

    /**
     *
     * Creates a single string from a list of string, each
     * individual one separated by the given delimiter
     *
     * @param parts list of strings to merge together
     * @param delim delimiter used to separate the strings
     * @return newly created string
     */
    private String createLine(List<String> parts, String delim) {
        StringBuilder line = new StringBuilder();
        line.append(parts.get(0));

        for (int i = 1; i < parts.size(); i++) {
            line.append(delim).append(" ").append(parts.get(i));
        }

        return line.toString();
    }

    /**
     *
     * Writes a singular line to a file
     *
     * @param line line to write
     * @param charset charset used
     * @throws IOException throws if writing fails
     */
    private void writeLine(String line, Charset charset) throws IOException {
        try (OutputStream stream = new FileOutputStream(filePath.toFile(), true);
             Writer streamWriter = new OutputStreamWriter(stream, charset);
             BufferedWriter writer = new BufferedWriter(streamWriter)) {

            writer.write(line);
            writer.newLine();
        }
    }
}
