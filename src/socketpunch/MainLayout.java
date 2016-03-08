
package socketpunch;

import com.olmectron.material.components.MaterialConfirmDialog;
import com.olmectron.material.components.MaterialDatePicker;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialMainUserItem;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTooltip;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.layouts.MaterialEditableLayout;
import com.olmectron.material.layouts.MaterialStandardLayout;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.utilities.Fecha;

/**
 *
 * @author olmec
 */
public abstract class MainLayout extends MaterialEditableLayout {
    
    public void reselectUser(){};
    public MainLayout(){
        super("",false,false,true);
        setDrawerWidth(300);
        
        addToolbarActionButton(MaterialIconButton.DELETE_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                emptyList();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },"Clear List");
        addToolbarActionButton(MaterialIconButton.FOLDER_OPEN_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                openCia();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },"Open CIA Files");
       /* addToolbarActionButton(MaterialIconButton.REFRESH_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                punch();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },"Punch!").getIconButton().setIcon("punch_icon.png");*/
        
    }
    public abstract void emptyList();
    public abstract void openCia();
    public abstract void punch();
    public abstract void onBufferSizeChanged(int value);
    @Override
    public void onMenuButtonPressed(Button button) {
        MaterialDropdownMenu menu=new MaterialDropdownMenu(button);
        
        menu.addItem(new MaterialDropdownMenuItem("Advanced options"){
            @Override
            public void onItemClick(){
                try {
                    
                    MaterialTextField bufferField=new MaterialTextField("Buffer Size");
                    MaterialConfirmDialog advancedDialog=
                            new MaterialConfirmDialog("Advanced Options","Edit the fields you want to change.","Save","Cancel"){
                                @Override
                                public void onPositiveButton(){
                                    
                                        if(!bufferField.getText().trim().equals("")){
                                            dismiss();
                                            onBufferSizeChanged(Integer.parseInt(bufferField.getText()));
                                        }
                                        else{
                                            bufferField.setErrorText("You must write me a value");
                                            bufferField.showError();
                                        }
                                    
                                }
                                @Override
                                public void onDialogShown() {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                                
                                @Override
                                public void onDialogHidden() {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                                
                                @Override
                                public void onDialogKeyReleased(KeyEvent event) {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                                
                                @Override
                                public void onDialogKeyPressed(KeyEvent event) {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            };
                    bufferField.lockLetters();
                    bufferField.setText(new FieldsFile("config.txt").getValue("buffersize","128"));
                    VBox advancedOptionsBox=new VBox(bufferField);
                    advancedDialog.setCustomContent(advancedOptionsBox);
                    advancedDialog.unhide();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        menu.addItem(new MaterialDropdownMenuItem("About"){
            @Override
            public void onItemClick(){
               new MaterialConfirmDialog("About...","This is a mod of Joshtech's SocketPunch app by Olmectron. You can send and install CIA files through a local network!","Ok"){
                   @Override
                   public void onPositiveButton(){
                       dismiss();
                   }
                   @Override
                   public void onDialogShown() {
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public void onDialogHidden() {
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public void onDialogKeyReleased(KeyEvent event) {
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public void onDialogKeyPressed(KeyEvent event) {
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               }.unhide();
            }
            
        
        });
        
        
        menu.unhide();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
