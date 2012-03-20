package net.praqma.jenkins.configrotator;

import net.praqma.jenkins.configrotator.ConfigurationRotator.ResultType;
import hudson.model.AbstractBuild;
import hudson.model.Action;

public class ConfigurationRotatorBuildAction implements Action {
	
	private AbstractBuild<?, ?> build;
	private Class<? extends AbstractConfigurationRotatorSCM> clazz;
	private ResultType result = ResultType.UNDETERMINED;
	private AbstractConfigurationActionElement configuration;
	
	public ConfigurationRotatorBuildAction( AbstractBuild<?, ?> build, Class<? extends AbstractConfigurationRotatorSCM> clazz, AbstractConfigurationActionElement configuration ) {
		this.build = build;
		this.clazz = clazz;
		this.configuration = configuration;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	public void setResult( ResultType result ) {
		this.result = result;
	}
	
	public boolean isDetermined() {
		return result.equals( ResultType.COMPATIBLE ) || result.equals( ResultType.INCOMPATIBLE );
	}
	
	public ResultType getResult() {
		return result;
	}

	@Override
	public String getIconFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		return "Config Rotator";
	}

	@Override
	public String getUrlName() {
		return "config-rotator";
	}

	public AbstractBuild<?, ?> getBuild() {
		return build;
	}
	
	public AbstractConfigurationActionElement getConfiguration() {
		return configuration;
	}
}