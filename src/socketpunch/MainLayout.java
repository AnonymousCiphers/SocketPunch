
package socketpunch;

import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.MaterialConfirmDialog;
import com.olmectron.material.components.MaterialDatePicker;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialLabel;
import com.olmectron.material.components.MaterialMainUserItem;
import com.olmectron.material.components.MaterialSelector;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTooltip;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.layouts.MaterialEditableLayout;
import com.olmectron.material.layouts.MaterialStandardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import org.utilities.Fecha;
import socketpunch.languages.R;

/**
 *
 * @author olmec
 */
public abstract class MainLayout extends MaterialEditableLayout {
    
    public void reselectUser(){};
    public MainLayout(){
        super("",false,false,true);
        setDrawerWidth(300);
        
        addToolbarActionButton(MaterialIconButton.RESTORE_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                resetQueue();
            
            }
        
        
        },R.string.reset_queue);
        addToolbarActionButton(MaterialIconButton.DELETE_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                 
                
                MaterialDropdownMenu clearMenu=new MaterialDropdownMenu((Region)event.getSource());
                clearMenu.addItem(new MaterialDropdownMenuItem(R.string.clear_all_files){
                    @Override
                    public void onItemClick(){
                        emptyList();
                    }
                
                
                });
                clearMenu.addItem(new MaterialDropdownMenuItem(R.string.clear_complete_files){
                    @Override
                    public void onItemClick(){
                       removeCompletedFiles();
                    }
                
                
                });
                
                
                clearMenu.unhide();
               
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },R.string.clear_list);
        addToolbarActionButton(MaterialIconButton.FOLDER_OPEN_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                openCia();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },R.string.open_files);
       /* addToolbarActionButton(MaterialIconButton.REFRESH_ICON,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                punch();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },"Punch!").getIconButton().setIcon("punch_icon.png");*/
        
    }
    public abstract void removeCompletedFiles();
    public abstract void emptyList();
    public abstract void openCia();
    public abstract void punch();
    public abstract void resetQueue();
    public abstract void onBufferSizeChanged(int value);
    public abstract void onLanguageChanged(String selectedLang);
    @Override
    public void onMenuButtonPressed(Button button) {
        MaterialDropdownMenu menu=new MaterialDropdownMenu(button);
        
        menu.addItem(new MaterialDropdownMenuItem(R.string.advanced_options){
            @Override
            public void onItemClick(){
                try {
                    
                    MaterialTextField bufferField=new MaterialTextField(R.string.buffer_size);
                    MaterialConfirmDialog advancedDialog=
                            new MaterialConfirmDialog(R.string.advanced_options,R.string.edit_fields,R.string.save,R.string.cancel){
                                @Override
                                public void onPositiveButton(){
                                    
                                        if(!bufferField.getText().trim().equals("")){
                                            dismiss();
                                            onBufferSizeChanged(Integer.parseInt(bufferField.getText()));
                                        }
                                        else{
                                            bufferField.setErrorText(R.string.write_me);
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
                menu.addItem(new MaterialDropdownMenuItem(R.string.change_language){
            @Override
            public void onItemClick(){
                try {
                    
                    MaterialSelector<String> languageSelector=new MaterialSelector<String>();
                    languageSelector.setPrefWidth(500);
                    languageSelector.setConverter(new  StringConverter<String>(){
                        @Override
                        public String toString(String object) {
                            return object;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public String fromString(String string) {
                            return null;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    languageSelector.addItem("English");
                    languageSelector.addItem("Español");
                    File dir=new File("languages");
                    if(dir.exists() && dir.isDirectory()){
                        File[] fileList=dir.listFiles();
                        for(File langFile: fileList){
                            languageSelector.addItem(langFile.getName());
                        }
                    }
                    for (int i=0;i<languageSelector.getItems().size();i++) {
                        String item=languageSelector.getItems().get(i);
                        FieldsFile f=new FieldsFile("config.txt");
                        String lang=f.getValue("language", "english");
                        if(item.toLowerCase().equals(lang)){
                            languageSelector.getSelectionModel().select(i);
                        }
                    }
                    
                    MaterialLabel label=new MaterialLabel(R.string.app_language);
                    languageSelector.setLabel(label);
                    MaterialConfirmDialog advancedDialog=
                            new MaterialConfirmDialog(R.string.change_language,R.string.select_language,R.string.change,R.string.cancel){
                                @Override
                                public void onPositiveButton(){
                                    try {
                                        FieldsFile config=new FieldsFile("config.txt");
                                        String selectedLanguage=languageSelector.getSelectionModel().getSelectedItem().toLowerCase();
                                        config.setValue("language", selectedLanguage);
                                        dismiss();
                                        onLanguageChanged(selectedLanguage);
                                        
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(MainLayout.class.getName()).log(Level.SEVERE, null, ex);
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
                    //bufferField.lockLetters();
                    //bufferField.setText(new FieldsFile("config.txt").getValue("buffersize","128"));
                    VBox advancedOptionsBox=new VBox(label,languageSelector);
                    advancedDialog.setCustomContent(advancedOptionsBox);
                    advancedDialog.unhide();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
                 menu.addItem(new MaterialDropdownMenuItem(R.string.import_language){
            @Override
            public void onItemClick(){
                menu.setOnHidden(new EventHandler<WindowEvent>(){
                    @Override
                    public void handle(WindowEvent event) {
                        
                        
                        
                        ArrayList<File> textFiles=showTextFileChooser();
                if(textFiles!=null){
                    File dir=new File("languages");
                        if(!dir.exists()){
                            dir.mkdir();
                        }
                    for(int i=0;i<textFiles.size();i++){
                        
                        try {
                            Files.copy(textFiles.get(i).toPath(), new File("languages/"+textFiles.get(i).getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            
                        } catch (IOException ex) {
                            Logger.getLogger(MainLayout.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    if(textFiles.size()>0){
                        new MaterialToast(R.string.import_successful).unhide();
                    }
                }
                        
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                
            
            }
                 
                 
                 });
                 
                  menu.addItem(new MaterialDropdownMenuItem(R.string.export_language){
            @Override
            public void onItemClick(){
                try {
                    
                    MaterialSelector<String> languageSelector=new MaterialSelector<String>();
                    languageSelector.setPrefWidth(500);
                    languageSelector.setConverter(new  StringConverter<String>(){
                        @Override
                        public String toString(String object) {
                            return object;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public String fromString(String string) {
                            return null;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    languageSelector.addItem("English");
                    languageSelector.addItem("Español");
                    File dir=new File("languages");
                    if(dir.exists() && dir.isDirectory()){
                        File[] fileList=dir.listFiles();
                        for(File langFile: fileList){
                            languageSelector.addItem(langFile.getName());
                        }
                    }
                    for (int i=0;i<languageSelector.getItems().size();i++) {
                        String item=languageSelector.getItems().get(i);
                        FieldsFile f=new FieldsFile("config.txt");
                        String lang=f.getValue("language", "english");
                        if(item.toLowerCase().equals(lang)){
                            languageSelector.getSelectionModel().select(i);
                        }
                    }
                    
                    MaterialLabel label=new MaterialLabel(R.string.export_language);
                    languageSelector.setLabel(label);
                    MaterialConfirmDialog advancedDialog=
                            new MaterialConfirmDialog(R.string.export_language,R.string.select_language,R.string.export,R.string.cancel){
                                private boolean exportWhenHidden=false;
                                private String selectedLanguage=null;
                                @Override
                                public void onPositiveButton(){
                                    selectedLanguage=languageSelector.getSelectionModel().getSelectedItem().toLowerCase();
                                    exportWhenHidden=true;
                                    dismiss();
                                        
                                        
                                    
                                        
                                }
                                @Override
                                public void onDialogShown() {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                                
                                @Override
                                public void onDialogHidden() {
                                    if(exportWhenHidden){
                                        R.string.saveExportFile(selectedLanguage);
                                    }
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
                    //bufferField.lockLetters();
                    //bufferField.setText(new FieldsFile("config.txt").getValue("buffersize","128"));
                    VBox advancedOptionsBox=new VBox(label,languageSelector);
                    advancedDialog.setCustomContent(advancedOptionsBox);
                    advancedDialog.unhide();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
                 
                 
                 
        menu.addItem(new MaterialDropdownMenuItem(R.string.about){
            @Override
            public void onItemClick(){
               new MaterialConfirmDialog(R.string.about,"This is a mod of Joshtech's SocketPunch app by Olmectron. You can send and install CIA files through a local network!",R.string.ok){
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
    
    private ArrayList<File> showTextFileChooser(){
        FileChooser fileChooser = new FileChooser();
        
 fileChooser.setTitle(R.string.open_files);
 fileChooser.getExtensionFilters().addAll(
         new FileChooser.ExtensionFilter("Text Files", "*.txt"));
 List<File> selectedFiles = fileChooser.showOpenMultipleDialog(MaterialDesign.primary);
 if(selectedFiles!=null){
 ArrayList<File> files=new  ArrayList<File>();
 for(int i=0;i<selectedFiles.size();i++){
     files.add(selectedFiles.get(i));
 }

 return files;
 }
 return null;
    }

    
    
}
