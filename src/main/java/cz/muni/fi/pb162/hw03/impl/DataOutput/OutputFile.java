package cz.muni.fi.pb162.hw03.impl.DataOutput;

import cz.muni.fi.pb162.hw02.LabelMatcher;
import cz.muni.fi.pb162.hw02.impl.LabeledOperations;
import cz.muni.fi.pb162.hw03.impl.LabeledObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * Represents a file output
 *
 * @author Martin Oliver Pitonak
 */
public class OutputFile implements Output {
    private final Path filePath;
    private final String delim;
    private final LabelMatcher matcher;
    private final Charset charset;

    /**
     *
     * f
     * @param dir f
     * @param name f
     * @param delim f
     * @param expression f
     * @param charset f
     */
    public OutputFile(Path dir, String name, String delim, String expression, Charset charset) {
        filePath = dir.resolve(name + ".csv");
        this.delim = delim;
        this.charset = charset;
        this.matcher = LabeledOperations.expressionMatcher(expression);
    }
    @Override
    public void initialize(List<String> header) throws IOException {
        if (! filePath.toFile().createNewFile()) {
            filePath.toFile().delete();
            filePath.toFile().createNewFile();
        }
        writeLine(createLine(header));
    }

    @Override
    public void writeIfMatches(List<String> entry, int index) throws IOException {
        if (matcher.matches(new LabeledObject(entry.get(index)))) {
            writeLine(createLine(entry));
        }
    }

    private String createLine(List<String> parts) {
        StringBuilder line = new StringBuilder();
        line.append(parts.get(0));

        for (int i = 1; i < parts.size(); i++) {
            line.append(delim).append(" ").append(parts.get(i));
        }

        return line.toString();
    }

    private void writeLine(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.write(line + System.lineSeparator());
        }
    }

    @Override
    public String toString() {
        return "OutputFile{" +
                "filePath=" + filePath +
                '}';
    }
}
