/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch;

import com.olmectron.material.components.FlatButton;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.utils.BackgroundTask;
import com.olmectron.material.utils.HTTPReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketpunch.languages.R;

/**
 *
 * @author Edgar
 */
    public class Updater {
    public static void checkForUpdates(boolean showIfUpdated){
       
      BackgroundTask updateTask=new BackgroundTask(){
               String latestVersion=null;
               @Override
               public Object onAction() {
                
                   HTTPReader readURL=new HTTPReader("http://olmectron.github.io/socket_update.html");
                   readURL.setShowNetworkFailedToast(showIfUpdated);
                   readURL.setNetworkFailedMessage(R.string.connection_failed); 
                   latestVersion=readURL.readFile();
                  
                    return null;
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void onSucceed(Object valor) {
                   if(latestVersion!=null && latestVersion.trim().toLowerCase().startsWith("v") && !latestVersion.toLowerCase().trim().equals(R.string.version)){
                       FlatButton updateButton=new FlatButton(R.string.update);
                       
                       MaterialToast updateToast=new MaterialToast((R.string.update_available + latestVersion.trim()),updateButton,MaterialToast.LENGTH_UNDEFINED){
                           @Override
                           public void onButtonClicked(){
                               
                                   new MaterialToast(R.string.retrieve_update,MaterialToast.LENGTH_UNDEFINED).unhide();
                                   new BackgroundTask(){
                                       @Override
                                       public Object onAction() {
                                           startUpdate();
                                           return null;
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }

                                       @Override
                                       public void onSucceed(Object valor) {
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }
                                   }.play();
                                           
                               
                               
                               //System.out.println("Actualizando...");
                           }
                       
                       };
                       updateToast.unhide();
                   
                   }
                   else if(latestVersion!=null && latestVersion.toLowerCase().trim().equals(R.string.version)){
                       if(showIfUpdated)
                       new MaterialToast(R.string.already_updated).unhide();
                   }
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }
           };
           updateTask.play();
            
      
    }
    public static void startUpdate(){
         
        try {
            File jarFile=new File("updater/AppUpdater.jar");
            if(jarFile.exists()){
                File exe=new File("../SocketPunch.exe");
                        if(exe.exists()){
                            
                            TextFile file=new TextFile("SocketPunch.cfg");
                            file.setText(file.getText().replace("SocketPunch.jar", "updater/AppUpdater.jar").
                                    replace("socketpunch/SocketPunch","appupdater/AppUpdater"));
                          
                            
                        try {
                            Process process = Runtime.getRuntime().exec("../SocketPunch.exe \"SocketPunch\" \"SocketPunch\" \"SocketPunch\" \"socketPunch/SocketPunch\"");
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        }
                        else{
                String jarName=null;
                  try {
            File c=new File(Updater.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            jarName=(c.getName());
            jarName=jarName.substring(0,jarName.length()-4);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
                  String command="java -jar updater/AppUpdater.jar \""+"SocketPunch"+"\" \""+jarName+"\" \""+"SocketPunch"+"\" \""+"socketpunch/SocketPunch"+"\"";
                  
                  String commandTest="java -jar updater/AppUpdater.jar \""+"SocketPunch"+"\" \""+"SocketPunch"+"\" \""+"SocketPunch"+"\" \""+"socketpunch/SocketPunch"+"\"";
                  System.out.println(command);
                Process process = Runtime.getRuntime().exec(command);
          if(process.isAlive()){
                    System.exit(0);
                }
          
                        }
          
          
          
            }
            else{
                File dir=new File("updater");
                if(!dir.exists()){
                    dir.mkdir();
                }
                HTTPReader downloader=new HTTPReader("http://olmectron.github.io/AppUpdater.jar"){
                    @Override
                    public void onFileDownloaded(){
                        File exe=new File("../SocketPunch.exe");
                        if(exe.exists()){
                            
                            TextFile file=new TextFile("SocketPunch.cfg");
                            file.setText(file.getText().replace("SocketPunch.jar", "updater/AppUpdater.jar").
                                    replace("socketpunch/SocketPunch","appupdater/AppUpdater"));
                          
                            
                        try {
                            Process process = Runtime.getRuntime().exec("../SocketPunch.exe \"SocketPunch\" \"SocketPunch\" \"SocketPunch\" \"socketpunch/SocketPunch\"");
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        }
                        else{
                        try {
                            String jarName=null;
             try {
            File c=new File(Updater.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            jarName=(c.getName());
            jarName=jarName.substring(0,jarName.length()-4);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
             String command="java -jar updater/AppUpdater.jar \""+"SocketPunch"+"\" \""+jarName+"\" \""+"SocketPunch"+"\" \""+"socketpunch/SocketPunch"+"\"";
                  
                            Process process = Runtime.getRuntime().exec(command);
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        }
                    }
                };
                downloader.downloadFile("updater");
                
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


