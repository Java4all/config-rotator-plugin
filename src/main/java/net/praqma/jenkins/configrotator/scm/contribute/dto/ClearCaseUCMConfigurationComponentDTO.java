/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.jenkins.configrotator.scm.contribute.dto;

import java.io.Serializable;
import net.praqma.jenkins.configrotator.scm.clearcaseucm.ClearCaseUCMConfigurationComponent;

/**
 *
 * I had to create this class to actually be able to store the information about our baselines. I couldn't get it to work
 * by just storing 
 * 
 * @author Mads
 */
public class ClearCaseUCMConfigurationComponentDTO implements Serializable {
    
    private String component;
    private String stream;
    private String baseline;
    private String plevel;
    
    public ClearCaseUCMConfigurationComponentDTO() { }
    
    public ClearCaseUCMConfigurationComponentDTO(String component, String stream, String baseline, String plevel)  {
        this.component = component;
        this.stream = stream;
        this.baseline = baseline;
        this.plevel = plevel;
    }
           
    
    public static ClearCaseUCMConfigurationComponentDTO fromComponent(ClearCaseUCMConfigurationComponent comp) {
        ClearCaseUCMConfigurationComponentDTO dto =  new ClearCaseUCMConfigurationComponentDTO();
        dto.setBaseline(comp.getBaseline().getFullyQualifiedName());
        dto.setPlevel(comp.getPlevel().name());
        dto.setComponent(comp.getBaseline().getComponent().getFullyQualifiedName());
        dto.setStream(comp.getBaseline().getStream().getFullyQualifiedName());
        return dto;
    }
    

    /**
     * @return the baseline
     */
    public String getBaseline() {
        return baseline;
    }

    /**
     * @param baseline the baseline to set
     */
    public void setBaseline(String baseline) {
        this.baseline = baseline;
    }

    /**
     * @return the plevel
     */
    public String getPlevel() {
        return plevel;
    }

    /**
     * @param plevel the plevel to set
     */
    public void setPlevel(String plevel) {
        this.plevel = plevel;
    }

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the stream
     */
    public String getStream() {
        return stream;
    }

    /**
     * @param stream the stream to set
     */
    public void setStream(String stream) {
        this.stream = stream;
    }
    
}
