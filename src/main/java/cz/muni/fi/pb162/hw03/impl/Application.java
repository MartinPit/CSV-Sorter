package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.cmd.CommandLine;
import io.github.jcechace.edu.pb162.csv.CsvParser;
import io.github.jcechace.edu.pb162.csv.CsvToolkit;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Application Runtime
 */
final class Application {

    private final ApplicationOptions options;

    Application(ApplicationOptions options, CommandLine cli) {
        Objects.requireNonNull(options);
        Objects.requireNonNull(cli);

        this.options = options;
    }

    /**
     * Note:    This method represents the runtime logic.
     *          However, you should still use proper decomposition.
     *
     * Application runtime logic
     */
    void run() throws IOException {
        CsvParser parser = CsvToolkit.parser(options.getDelimiter(), options.getCharset());
        try (var reader = parser.openWithHeader(options.getInput())) {
            Map<String, String> line;
            while ((line = reader.read()) != null) {
                System.out.println(line);
            }
        }
    }
}
