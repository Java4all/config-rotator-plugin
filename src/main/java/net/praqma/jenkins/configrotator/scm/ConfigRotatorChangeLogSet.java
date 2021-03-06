package net.praqma.jenkins.configrotator.scm;

import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Praqma
 * @param <T>
 */
public class ConfigRotatorChangeLogSet<T extends ConfigRotatorChangeLogEntry> extends ChangeLogSet<T> {

    protected List<T> entries;
    private String headline;
    static final long serialVersionUID = 102L;
    public static final String EMPTY_DESCRIPTOR = "New configuration - No changes";


    public ConfigRotatorChangeLogSet( AbstractBuild<?, ?> build, List<T> entries ) {
        super(build);
        this.entries = entries;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Iterator<T> iterator() {
        return entries.iterator();
    }

    @Override
    public String toString() {
        return entries.toString();
    }

    public ConfigRotatorChangeLogSet(AbstractBuild<?,?> build) {
        super(build);
        entries = new ArrayList<>();
    }

    @Override
    public boolean isEmptySet() {
        return entries.isEmpty();
    }

    /**
     * Adds the entry to the changelogset
     * @param entry The entry to add
     */
    public void add(T entry) {
        entries.add(entry);
    }

    public List<T> getEntries() {
        return entries;
    }

    /**
     * @return the headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @param headline the headline to set
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
