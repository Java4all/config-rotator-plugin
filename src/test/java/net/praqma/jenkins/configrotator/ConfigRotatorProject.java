package net.praqma.jenkins.configrotator;

import hudson.model.FreeStyleProject;
import hudson.model.Hudson;
import hudson.model.Project;
import hudson.model.TopLevelItem;
import hudson.scm.SCM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cwolfgang
 */
public class ConfigRotatorProject {

    private Project project;
    private AbstractConfigurationRotatorSCM crSCM;

    private List<AbstractTarget> targets = new ArrayList<AbstractTarget>();

    Class<? extends TopLevelItem> projectClass = FreeStyleProject.class;

    private Project<?, ?> jenkinsProject;

    public ConfigRotatorProject( String name, AbstractConfigurationRotatorSCM crSCM ) throws IOException {
        this.crSCM = crSCM;

        crSCM.setTargets( targets );

        SCM scm = new ConfigurationRotator( crSCM );
        jenkinsProject = (Project) Hudson.getInstance().createProject( projectClass, name );
        jenkinsProject.setScm( scm );
    }

    public ConfigurationRotator getConfigurationRotator() {
        return (ConfigurationRotator) jenkinsProject.getScm();
    }

    public ConfigRotatorProject reconfigure() {

        targets = new ArrayList<>();

        crSCM.setConfiguration( null );
        crSCM.setTargets( targets );
        getConfigurationRotator().reconfigure = true;

        return this;
    }

    public ConfigRotatorProject addTarget( AbstractTarget target ) {
        targets.add( target );
        return this;
    }

    public ConfigRotatorProject clearTargets() {
        targets.clear();
        return this;
    }

    public Project<?, ?> getJenkinsProject() {
        return jenkinsProject;
    }

    public static String getSafeName( String name ) {
        return name.replaceAll( "[^\\w]", "_" );
    }

    /**
     * @return the crSCM
     */
    public AbstractConfigurationRotatorSCM getCrSCM() {
        return crSCM;
    }
}
