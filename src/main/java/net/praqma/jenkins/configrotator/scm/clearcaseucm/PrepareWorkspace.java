package net.praqma.jenkins.configrotator.scm.clearcaseucm;

import hudson.FilePath.FileCallable;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import net.praqma.clearcase.ConfigSpec;
import net.praqma.clearcase.Rebase;
import net.praqma.clearcase.exceptions.ClearCaseException;
import net.praqma.clearcase.exceptions.UnableToInitializeEntityException;
import net.praqma.clearcase.ucm.entities.Baseline;
import net.praqma.clearcase.ucm.entities.Project;
import net.praqma.clearcase.ucm.entities.Stream;
import net.praqma.clearcase.ucm.view.GetView;
import net.praqma.clearcase.ucm.view.SnapshotView;
import net.praqma.clearcase.ucm.view.UpdateView;
import net.praqma.jenkins.configrotator.ConfigurationRotator;

public class PrepareWorkspace implements FileCallable<SnapshotView> {

    private Project project;
    private TaskListener listener;
    private String jenkinsProjectName;
    private List<Baseline> baselines;

    public PrepareWorkspace(Project project, List<Baseline> baselines, String jenkinsProjectName, TaskListener listener) {
        this.project = project;
        this.jenkinsProjectName = jenkinsProjectName;
        this.listener = listener;
        this.baselines = baselines;
    }

    @Override
    public SnapshotView invoke(File workspace, VirtualChannel channel) throws IOException, InterruptedException {
        //Viewtag now becomes the jenkinsProjectName + remote computer name
        String viewtag = jenkinsProjectName + "-" + System.getenv("COMPUTERNAME");
        PrintStream out = listener.getLogger();

        out.println(String.format("%sResulting viewtag is: %s", ConfigurationRotator.LOGGERNAME, viewtag));

        SnapshotView view = null;
        File viewroot = new File(workspace, "view");

        /* Changle stream, if exists */
        String streamName = viewtag + "@" + project.getPVob();
        Stream devStream;

        try {
            devStream = Stream.get(streamName);
        } catch (UnableToInitializeEntityException e) {
            throw new IOException("No entity", e);
        }

        /* If the stream exists, change it */
        if (devStream.exists()) {
            out.println(ConfigurationRotator.LOGGERNAME + "Stream exists");

            try {
                view = new GetView(viewroot, viewtag).get();
            } catch (ClearCaseException e) {
                throw new IOException("Could not get view", e);
            }

            try {
                out.println(ConfigurationRotator.LOGGERNAME + "Rebasing stream to " + devStream.getNormalizedName());
                new Rebase(devStream).setViewTag(viewtag).addBaselines(baselines).dropFromStream().rebase(true);                
            } catch (ClearCaseException e) {
                throw new IOException("Could not load " + devStream, e);
            }

            /* The view */
            try {
                out.println(ConfigurationRotator.LOGGERNAME + "View root: " + new File(workspace, "view"));
                out.println(ConfigurationRotator.LOGGERNAME + "View tag : " + viewtag);
                new ConfigSpec(viewroot).addLoadRule(baselines).generate().appy();
                new UpdateView(view).swipe().overwrite().update();
            } catch (ClearCaseException e) {
                throw new IOException("Unable to create view", e);
            }

        } else {
            /* Create new */

            out.println(ConfigurationRotator.LOGGERNAME + "Creating a new environment");

            try {
                out.println(ConfigurationRotator.LOGGERNAME + "Creating new stream");
                devStream = Stream.create(project.getIntegrationStream(), streamName, true, baselines);
            } catch (ClearCaseException e1) {
                throw new IOException("Unable to create stream " + streamName, e1);
            }

            try {
                //view = ViewUtils.createView( devStream, "ALL", new File( workspace, "view" ), viewtag, true );
                view = new GetView(viewroot, viewtag).setStream(devStream).createIfAbsent().get();
                new UpdateView(view).setLoadRules(new SnapshotView.LoadRules(view, SnapshotView.Components.ALL)).generate().update();
            } catch (ClearCaseException e) {
                throw new IOException("Unable to create view", e);
            }
        }

        return view;
    }
}
