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
}
