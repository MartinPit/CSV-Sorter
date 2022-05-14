package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.cmd.CommandLine;

import java.io.IOException;
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
        DataFilterer filterer = new DataFiltererImpl(options.getFilters(),
                                                     options.getCharset(),
                                                     options.getDelimiter());
        filterer.filter(options.getInput(),
                        options.getOutput(),
                        options.getLabelColumn());
    }
}
