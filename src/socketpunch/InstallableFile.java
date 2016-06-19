/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch;

import java.io.File;

/**
 *
 * @author Edgar
 */
public class InstallableFile {
    private File installableFile;    
    public InstallableFile(){
        
    }
    public InstallableFile(File file){
        this.installableFile=file;
    }
    public File getFile(){
        return installableFile;
    }
    private boolean done=false;
    public boolean isTransferComplete(){
        return done;
    }
    public void setTransferComplete(boolean complete){
        this.done=complete;
    }
    private String titleId;
    public void setTitleId(String titleId){
        this.titleId=titleId;
    }
    public String getTitleId(){
        return this.titleId;
    }
    private String serial;
    public void setSerial(String titleId){
        this.serial=titleId;
    }
    public String getSerial(){
        return this.serial;
    }
    private String region;
    public void setRegion(String titleId){
        this.region=titleId;
    }
    public String getRegion(){
        return this.region;
    }
    private String languages;
    public void setLanguages(String languages){
        this.languages=languages;
    }
    public String getLanguages(){
        return this.languages;
    }
    private String name;
    public void setName(String titleId){
        this.name=titleId;
    }
    public String getName(){
        return this.name;
    }
    private boolean detailShowing=false;
    public boolean isShowingDetails(){
        return detailShowing;
    }
    public void setShowingDetails(boolean showing){
        detailShowing=showing;
    }
    private String publisher;
    public void setPublisher(String publisher) {
        this.publisher=publisher;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getPublisher(){
        return this.publisher;
    }
}
