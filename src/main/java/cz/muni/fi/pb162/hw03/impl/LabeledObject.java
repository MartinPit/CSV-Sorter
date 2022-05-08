package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw02.HasLabels;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Martin Oliver Pitonak
 */
public class LabeledObject implements HasLabels {

    private final Set<String> labels;

    /**
     * f
     * @param labels f
     */
    public LabeledObject(String labels) {
        this.labels = Arrays.stream(labels.split("\\s+")).collect(Collectors.toSet());
    }
    @Override
    public Set<String> getLabels() {
        return labels;
    }
}
