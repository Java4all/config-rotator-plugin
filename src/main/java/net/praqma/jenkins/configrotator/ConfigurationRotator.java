package net.praqma.jenkins.configrotator;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.scm.ChangeLogParser;
import hudson.scm.PollingResult;
import hudson.scm.SCMDescriptor;
import hudson.scm.SCMRevisionState;
import hudson.scm.SCM;

public class ConfigurationRotator extends SCM {
	
	private AbstractConfigurationRotatorSCM acrs;
	
	public ConfigurationRotator( AbstractConfigurationRotatorSCM acrs ) {
		this.acrs = acrs;
	}
	
	public AbstractConfigurationRotatorSCM getAcrs() {
		return acrs;
	}

	@Override
	public SCMRevisionState calcRevisionsFromBuild( AbstractBuild<?, ?> arg0, Launcher arg1, TaskListener arg2 ) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkout( AbstractBuild<?, ?> arg0, Launcher arg1, FilePath arg2, BuildListener arg3, File arg4 ) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected PollingResult compareRemoteRevisionWith( AbstractProject<?, ?> arg0, Launcher arg1, FilePath arg2, TaskListener arg3, SCMRevisionState arg4 ) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeLogParser createChangeLogParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Extension
	public static final class RotatorDescriptor extends SCMDescriptor<ConfigurationRotator> {

		public RotatorDescriptor() {
			super( ConfigurationRotator.class, null );
		}

		@Override
		public String getDisplayName() {
			return "Config rotator";
		}

		@Override
		public SCM newInstance( StaplerRequest req, JSONObject formData ) throws FormException {
			System.out.println( formData.toString( 2 ) );
			SCM instance =  super.newInstance( req, formData );
			//Class.forName( (String) formData.get( "stapler-class" ) );
			
			//ConfigurationRotator instance = req.bindJSON( ConfigurationRotator.class, formData );
			System.out.println( "---->"  + instance );
			return instance;
		}

		public List<ConfigurationRotatorSCMDescriptor> getSCMs() {
			return AbstractConfigurationRotatorSCM.getDescriptors();
		}
	}

}
