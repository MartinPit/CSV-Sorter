package cz.muni.fi.pb162.hw03.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Represents a file output
 *
 * @author Martin Oliver Pitonak
 */
public class OutputFile implements Output {
    private final Path filePath;
    private final String delim;

    /**
     *
     * Specifies the file into which we want to write
     * and the delimiter between items
     *
     * @param dir path where to put the file
     * @param name name of the file
     * @param delim the delimiter
     */
    public OutputFile(Path dir, String name, String delim) {
        filePath = dir.resolve(name + ".csv");
        this.delim = delim;
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
    public void write(Map<String, String> content) throws IOException {
        writeLine(createLine(new ArrayList<>(content.values())));
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
        try (FileWriter fw = new FileWriter(filePath.toString())) {
            fw.write(line);
        }
    }
}
