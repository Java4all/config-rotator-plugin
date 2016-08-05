package net.praqma.jenkins.configrotator.scm.git;

import hudson.FilePath;
import hudson.model.TaskListener;
import net.praqma.jenkins.configrotator.AbstractConfigurationComponent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.Objects;

public class GitConfigurationComponent extends AbstractConfigurationComponent implements Cloneable {

    public static final long serialVersionUID = 101;
    private String commitId;
    private String name;
    private String branch;
    private String repository;

    private GitConfigurationComponent( String name, String repository, String branch, String commitId, boolean fixed ) {
        super( fixed );
        this.name = name;
        this.repository = repository;
        this.branch = branch;
        this.commitId = commitId;
    }

    public GitConfigurationComponent( String name, String repository, String branch, RevCommit commit, boolean fixed ) {
        super( fixed );
        if( commit != null ) {
            this.commitId = commit.getName();
        }
        this.name = name;
        this.branch = branch;
        this.repository = repository;
    }

    public void checkout( FilePath workspace, TaskListener listener ) throws IOException, InterruptedException {
        workspace.act( new Checkout( name,  branch, commitId ) );
    }

    public String getBranch() {
        return branch;
    }

    public String getRepository() {
        return repository;
    }

    public String getName() {
        return name;
    }

    public void setCommitId( String commitId ) {
        this.commitId = commitId;
    }

    public String getCommitId() {
        return commitId;
    }

    @Override
    public String getComponentName() {
        return repository;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        GitConfigurationComponent gcc = (GitConfigurationComponent)super.clone();
        gcc.name = this.name;
        gcc.repository = this.repository;
        gcc.branch = this.branch;
        gcc.commitId = this.commitId;
        gcc.fixed = this.fixed;
        return  gcc;
    }

    @Override
    public String getFeedId() {
        return repository;
    }

    @Override
    public String getFeedName() {
        return repository;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commitId, name, repository);
    }

    @Override
    public boolean equals( Object other ) {
        if( other == this ) {
            return true;
        }

        if( other instanceof GitConfigurationComponent ) {
            GitConfigurationComponent o = (GitConfigurationComponent) other;
            return ( o.commitId.equals( commitId ) && ( o.isFixed() == fixed ) );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "GC[" + commitId + "]";
    }

    @Override
    public String prettyPrint() {
        return name + ": " + repository + ", " + branch + ", " + commitId;
    }

    @Override
    public String toHtml() {

        StringBuilder builder = new StringBuilder();

        return getBasicHtml( builder, new Element( repository, isChangedLast() ), new Element( branch, isChangedLast() ), new Element( commitId, isChangedLast() ), new Element( fixed+"", isChangedLast() ) );
    }

}
