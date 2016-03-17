
package socketpunch;

import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.FontWeight;
import com.olmectron.material.components.MaterialButton;
import com.olmectron.material.components.MaterialCard;
import com.olmectron.material.components.MaterialCheckBox;
import com.olmectron.material.components.MaterialComboBox;
import com.olmectron.material.components.MaterialDisplayText;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialFloatingButton;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialLabel;
import com.olmectron.material.components.MaterialProgressBar;
import com.olmectron.material.components.MaterialSelector;
import com.olmectron.material.components.MaterialStandardDialog;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.olmectron.material.components.MaterialStandardList;
import com.olmectron.material.components.MaterialStandardListItem;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTooltip;
import com.olmectron.material.components.RaisedButton;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.layouts.MaterialPane;
import com.olmectron.material.utils.BackgroundTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;
import org.utilities.Dates;
import org.utilities.Hora;
import socketpunch.languages.R;


/**
 *
 * @author olmec
 */
public class SocketPunch extends Application {

    //private MaterialProgressBar progBar;
    private ScrollPane jScrollPane1;
    private ScrollPane jScrollPane3;
    private MaterialTextField ipText;
    private MaterialDisplayText jLabel1;
    private MaterialDisplayText jLabel2;
    private ScrollPane jScrollPane2;
    private MaterialDisplayText jLabel3;
    private MaterialButton startButt;
    //private MaterialDisplayText progText;
    private ScrollPane jScrollPane4;
    private MaterialDisplayText jLabel4;
    private MaterialTextField timeoutVal;
    private MaterialDisplayText jLabel5;
    private MaterialCheckBox saveLogChk;
    private MainLayout layout;
    private CheckBox updateCheck, deleteCheck;
    private CheckBox updatePercentageCheck;
    private CheckBox updateBarCheck;
    public int getTimeoutVal(){
        if(!timeoutVal.getText().trim().equals("")){
            return Integer.parseInt(timeoutVal.getText());
            
        }
        else{
            return 5;
        }
    }
    @Override
    public void start(Stage primaryStage) {
        System.setProperty("prism.text","t2k");
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("javafx.animation.fullspeed","true");
        System.setProperty("prism.vsync","false");
        
        MaterialDesign.setCustomPath("/socketpunch/images/");
        layout=new MainLayout() {
            @Override
            public void removeCompletedFiles(){
                int counter=0;
                 for(int i=0;i<queueList.size();i++){
                            if(queueList.getItem(i).isTransferComplete()){
                                queueList.removeItem(queueList.getItemBox(i));
                                counter++;
                            }
                        }
                 new MaterialToast(R.string.get(R.string.number_files_removed,counter+"")).unhide();
                
            }
            @Override
            public void emptyList() {
                if(!startedSending){
                            queueList.clear();
                            new MaterialToast(R.string.empty_list).unhide();

                        }
                        else{
                            new MaterialToast(R.string.warning_clear_list).unhide();
                        }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void openCia() {
                openCiaChooser(primaryStage);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void punch() {
                startButtActionPerformed().handle(null);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void onLanguageChanged(String selectedLanguage){
                new MaterialToast(R.string.restart_app,MaterialToast.LENGTH_LONG).unhide();
            }
            @Override
            public void onBufferSizeChanged(int value) {
                if(!startedSending){
                    try {
                        new FieldsFile("config.txt").setValue("buffersize", ""+value);
                        bufferSize=value;
                        reprintConsoleBuffer();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                            
                }
                else{
                    new MaterialToast(R.string.warning_buffer_size).unhide();
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void resetQueue() {
                
                for (int i = 0; i < queueList.size(); i++) {
                    InstallableFile item = queueList.getItem(i);
                    
                        if(item.isTransferComplete()){
                            MaterialStandardListItem<InstallableFile> itemContainer=queueList.getItemBox(i);
                    
                            item.setTransferComplete(false);
                            MaterialProgressBar progreso=(MaterialProgressBar)itemContainer.lookup("#progreso");
                            MaterialDisplayText progresoTexto=(MaterialDisplayText)itemContainer.lookup("#progresoTexto");
                            MaterialDisplayText velocidadTexto=(MaterialDisplayText)itemContainer.lookup("#velocidadTexto");
                            String formattedTotal=new DecimalFormat("0.00").format((float)item.getFile().length() / 1048576);
                            velocidadTexto.setText(formattedTotal+"MB");
                            progreso.setProgress(0.0f);
                            progresoTexto.setText("0%");

                        }
                    }
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        layout.setRootView(initAll(primaryStage));
        
        StackPane transparentContainer=new StackPane();
        
       transparentContainer.getStyleClass().add("full-transparent-container");
       
       layout.setTitle(R.string.app_title);
        Scene scene = new Scene(layout, 800,500);
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        
        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        if(file.getName().endsWith(".cia")){
                            queueList.addItem(new InstallableFile(file));
                        }
                        else{
                            new MaterialToast(R.string.no_cia_file_error).unhide();
                        }
                        
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {
                            //System.out.println("juerngviansgvijasndsv");
                            MaterialDropdownMenu m=MaterialDropdownMenu.getLastMenu();
                            if(m!=null){
                                m.hideMenu();
                            }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
        MaterialDesign.setWindowOwner(primaryStage);
        new MaterialDesign().setScene(scene);
        //primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        Platform.setImplicitExit(true);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                //Platform.exit();
                System.exit(0);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        primaryStage.setResizable(false);
        primaryStage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
   
    
    
    



/**
 *
 * @author Josh Johansson
 * Thanks to Steveice10 for FBI and sockfile
 */



    String CONST_PATH = System.getProperty("user.dir") + "\\log.txt";
    String CONST_SETTING_PATH = System.getProperty("user.dir") + "\\config.txt";
    String NEWLINE = System.getProperty("line.separator");

    //private MaterialStandardList<String> dirList;
    private MaterialStandardList<InstallableFile> queueList;
    boolean startedSending = false;
    int transferCount = 0;
    int failCount = 0;
    int bufferSize = 128;
    public void reprintConsoleBuffer(){
           
        consoleWrite(R.string.get(R.string.buffer_set,Integer.toString(bufferSize)+""), true);
        
        }

    public Pane initAll(Stage st) {
        st.setTitle("SocketPunch MOD v0.9");
        StackPane root = new StackPane();
        
        queueList=new MaterialStandardList<InstallableFile>(root) {
        @Override
        public void onItemClick(InstallableFile item, MouseEvent event) {
            
            
        }
        @Override
        public void onItemContainerClick(MaterialStandardListItem<InstallableFile> itemBox,MouseEvent event){
            //MaterialDisplayText tb = (MaterialDisplayText) itemBox.lookup("#progresoTexto");
            if(event.getButton().equals(MouseButton.SECONDARY)){
                MaterialDropdownMenu dropdown=new MaterialDropdownMenu(event.getScreenX(),event.getScreenY());
                dropdown.addItem(new MaterialDropdownMenuItem(R.string.delete_queue_item){
                    @Override
                    public void onItemClick(){
                        //if(!startedSending){
                            queueList.removeItem(itemBox);
                        //}
                    }
                });
                dropdown.unhide();
            }
        }
        
        @Override
        public Node itemConverter(InstallableFile it) {
            File item=it.getFile();
            String hex="";
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(item, "r");
                raf.seek(0x2C20);
                
                for(int i=0;i<4;i++){
                    int part=raf.readByte();
                    if(part<0){
                        part= part & 0xff;
                    }
                    String hexPart=Integer.toHexString(part);
                    if(hexPart.length()==1 && i>0){
                        hexPart="0"+hexPart;
                    }
                    hex=hex+hexPart;
                }
                if(hex.endsWith("00")){
                    hex=hex.substring(0,hex.length()-2);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    raf.close();
                } catch (IOException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             // Goes to 400th byte.
            
            MaterialCard card=new MaterialCard();
            
            
            MaterialDisplayText texto=new MaterialDisplayText(item.getName());
            //MaterialTooltip tooltip=new MaterialTooltip(item.getName(),texto);
            texto.setAlignment(Pos.CENTER_LEFT);
            card.activateShadow(1);
            texto.setColorCode(MaterialColor.material.BLACK_87);
            texto.setFontSize(15);
            
            card.setZ(bufferSize);
            HBox spanBox=new HBox();
            HBox.setHgrow(spanBox, Priority.ALWAYS);
            
           
            
            MaterialDisplayText progText=new MaterialDisplayText("0%");
            progText.setMinWidth(60);
            progText.setAlignment(Pos.CENTER_RIGHT);
            progText.setColorCode(MaterialColor.material.BLUE);
            
            progText.setFontWeight(FontWeight.MEDIUM);
            progText.setId("progresoTexto");
            MaterialDisplayText hexText=new MaterialDisplayText(R.string.get(R.string.initial_code, "0x"+hex.toUpperCase()));
            //hexText.setMinWidth(62);
            hexText.setFontSize(12);
            hexText.setColorCode(MaterialColor.material.BLACK_54);
            HBox box=new HBox(texto,spanBox,progText);
            card.setCardPadding(new Insets(16));
            MaterialProgressBar ciaBar = new MaterialProgressBar(0.0f);
            ciaBar.setId("progreso");
            MaterialCheckBox c=new MaterialCheckBox();
            c.setSelected(false);
            
            c.setId("doneChecker");
           
            ciaBar.setColor(MaterialColor.BLUE);
            VBox progBox=new VBox(ciaBar);
            
             String formattedTotal=new DecimalFormat("0.00").format((float) item.length() / 1048576);
            MaterialDisplayText speedText=new MaterialDisplayText(formattedTotal+"MB");
            //speedText.setMinWidth(60);
            speedText.setAlignment(Pos.CENTER_LEFT);
            speedText.setColorCode(MaterialColor.material.GREEN);
            speedText.setFontSize(12);
            //speedText.setFontWeight(FontWeight.MEDIUM);
            speedText.setId("velocidadTexto");
            progBox.setPadding(new Insets(8,0,4,0));
            VBox speedBox=new VBox(speedText);
            //box.setPrefWidth(1000);
            card.addComponent(new VBox(hexText, box,progBox,speedBox));
            box.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(3,4,5,4));
            root.widthProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    //card.setExactWidth(newValue.doubleValue());
                }
            });
            card.setCardWidth(1000);
            //card.setCardWidth(2000);
            return card;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
        queueList.transparentScroll();
        root.getChildren().add(initComponents(st));
        // Get local ip and set ipText value
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ipText.setText(addr.getHostAddress());
        } catch (UnknownHostException ex) {
            consoleWrite(R.string.network_problem, true);
        }
        GetSettings();
        ipText.textField().textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.trim().equals("")){
                    try {
                        new FieldsFile("config.txt").setValue("ip", newValue);
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    /*ArrayList<String> ipList=new ArrayList<String>();
                    ipList.add(newValue);
                    saveIPAddresses(ipList);*/
                }
            }
        });
        
        // We check if the log file exists
        if(saveLogChk.isSelected()){
        // Create 3 newlines in log if there is already a log. Try to keep things seperate
        File file = new File(CONST_PATH);
        // if file doesnt exists, then create it
        if (file.exists()) {
        	for(int i = 0; i < 3; i++){
                    WriteLog(CONST_PATH, "");
                }
        }
        }
        // Print some stoof
        consoleWrite("==============================", false);
        consoleWrite("         SocketPunch mod v0.9 by Olmectron", false);
        consoleWrite("        Original from Joshtech @GBATemp.net",false);
        consoleWrite("==============================", false);
        consoleWrite(R.string.get(R.string.buffer_set, Integer.toString(bufferSize)), true);
        
        getCiaListing();
        //resetControls();
        transferCount=0;
        MaterialPane pane=new  MaterialPane() {
            @Override
            public void onShown() {
                ipText.requestFocus();
                ipText.textField().selectAll();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onKeyPressed(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        //pane.getStyleClass().add("standard-grey-background");
        MaterialFloatingButton punchButton=new MaterialFloatingButton(MaterialFloatingButton.REFRESH_ICON);
        punchButton.setIcon("punch_icon.png");
        //punchButton.setI(MaterialColor.material.WHITE);
        punchButton.setOnAction(startButtActionPerformed());
        pane.getContentCard().setFloatingButton(punchButton);
        pane.setBackgroundColor("standard-grey-background");
        
        pane.getContentCard().setCardPadding(new Insets(0));
        
        StackPane c=new StackPane(root);
        
        c.setPadding(new Insets(24));
       ScrollPane p=new ScrollPane(c);
       p.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                c.setPrefWidth(newValue.doubleValue()-18);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
       //c.setPrefWidth(p.getPrefViewportWidth());
       p.getStyleClass().add("material-scroll");
        pane.setRootComponent(p);
        return pane;
    }
    private ArrayList<String> loadIPAddresses(){
        Preferences prefs = Preferences.userNodeForPackage(SocketPunch.class);
     String ipAddresses = prefs.get("ipAddresses", null);
     if (ipAddresses != null) {
         ArrayList<String> ipArray=new ArrayList<String>();
         StringTokenizer tokens=new StringTokenizer(ipAddresses,",");
         while(tokens.hasMoreTokens()){
             ipArray.add(tokens.nextToken());
         }
         return ipArray;
     } else {
         return null;
     }
    }
    private void saveIPAddresses(ArrayList<String> ipArray){
        
        Preferences prefs = Preferences.userNodeForPackage(SocketPunch.class);
       if (ipArray != null) {
           String ipsString="";
           for(int i=0;i<ipArray.size();i++){
               ipsString=ipsString+ipArray.get(i);
               if(i<ipArray.size()-1){
                  ipsString=ipsString+","; 
               }
           }
           prefs.put("ipAddresses", ipsString);

           // Update the stage title.
           //primaryStage.setTitle("AddressApp - " + file.getName());
       } else {
           prefs.remove("ipAddresses");

           // Update the stage title.
           //primaryStage.setTitle("AddressApp");
       }
    }
    private String lastDir=null;
    public void GetSettings(){
        try {
            FieldsFile fieldFile=new FieldsFile("config.txt");
            if(!fieldFile.exists() || fieldFile.isEmpty()){
                //fieldFile.createNewFile();
                fieldFile.addField("buffersize", "128");
                fieldFile.addField("savelog", "true");
                fieldFile.addField("ip",ipText.getText());
                fieldFile.addField("update","true,true,true");
                fieldFile.addField("lastdir",System.getProperty("user.dir"));
                GetSettings();
            }
            else{
                StringTokenizer updateToken=new StringTokenizer(fieldFile.getValue("update","true,true,true,false"),",");
                try{
                    boolean updatePercentage=Boolean.parseBoolean(updateToken.nextToken());
                boolean updateBar=Boolean.parseBoolean(updateToken.nextToken());
                boolean updateSpeed=Boolean.parseBoolean(updateToken.nextToken());
                boolean deleteCia=Boolean.parseBoolean(updateToken.nextToken());
                    updatePercentageCheck.setSelected(updatePercentage);
                    updateBarCheck.setSelected(updateBar);
                    updateCheck.setSelected(updateSpeed);
                    deleteCheck.setSelected(deleteCia);
                    
                }catch(NoSuchElementException ex){
                    fieldFile.setValue("update","true,true,true,false");
                    updatePercentageCheck.setSelected(true);
                    updateBarCheck.setSelected(true);
                    updateCheck.setSelected(true);
                    deleteCheck.setSelected(false);
                }
                    bufferSize=Integer.parseInt(fieldFile.getValue("buffersize","128"));
                ipText.setText(fieldFile.getValue("ip",ipText.getText()));
                saveLogChk.setSelected(Boolean.parseBoolean(fieldFile.getValue("savelog","true")));
                lastDir=fieldFile.getValue("lastdir",System.getProperty("user.dir"));
            }
            /*ArrayList<String> IPs=loadIPAddresses();
            if(IPs!=null){
                ipText.setText(IPs.get(0));
            }
            else{
                ArrayList<String> ipList=new ArrayList<String>();
                ipList.add(ipText.getText());
                
                saveIPAddresses(ipList);
            }*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*File tempFile = new File(CONST_SETTING_PATH);
        // if file doesn't exists, then create it
        if (!tempFile.exists()) {
        	consoleWrite("- Settings file could not be found. Creating default.", true);
                WriteTextFile(CONST_SETTING_PATH, "// This file contains settings for SocketPunch" + NEWLINE + "// Please keep in mind that buffersize should match with FBI (default 128)" + NEWLINE + "savelog=true" + NEWLINE + "buffersize=128");
                GetSettings();
        } else {
            // Open settings file and split it by lines
            String[] file = ReadTextFile(CONST_SETTING_PATH).split(NEWLINE);
            // For each line
            for (String fileLine : file) {
                // If it's not a comment
                if (!fileLine.contains("//")) {
                    try {
                        String[] tempSplit = fileLine.split("=");
                        switch(tempSplit[0].toLowerCase()){
                            case "savelog":
                                saveLogChk.setSelected(Boolean.valueOf(tempSplit[1]));
                                break;
                            case "buffersize":
                                bufferSize = Integer.parseInt(tempSplit[1]);
                                break;
                            case "ip":
                                ipText.setText(tempSplit[1]);
                                
                        }
                    }catch (Exception e) {
                        consoleWrite("- There was an issue loading settings!", true);
                    }
                }
            }
        }*/
    }
    
    public void OpenDir(String path){
        File f = new File(path);
        File[] list = f.listFiles();
        for (File file : list){
            try {
                // Check if it's a folder!
                if(file.isDirectory()){
                    OpenDir(file.toString());
                // Guess it's a cia
                } else if (file.isFile() && file.toString().toLowerCase().contains(".cia")){
                    // Add that shit to the model mate! 
                    //queueList.addItem(file);
                }
            } catch (Exception e) {
            }
        }
    }
    
    public String ReadTextFile(String path){
        String tempString = "";
        try {
            Reader file = new FileReader(path);
            int data = file.read();
            while(data != -1){
                tempString = tempString + (char) data;
                data = file.read();
            }
            file.close();
        } catch (Exception e) {
            return "";
        }
        return tempString;
    }
    public void writeLogFile(String text){
        TextFile logFile=new TextFile("log.txt");
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        logFile.setText(text+System.getProperty("line.separator")+logFile.getText());
    }
    public void WriteTextFile(String path, String text){
        BufferedWriter bw = null;
        FileWriter fw;
         try {
            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
        	file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.flush();
 	} catch (IOException e) {
            consoleWrite("- Unable to write file. Weird!", true);
	} finally { // always close the file
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {
                        // just ignore it
            }
        }
    }
    
    
     public void WriteLog(String path, String text){
        writeLogFile(text);
    }
    
    public void getCiaListing(){
       // consoleWrite("- Getting directory listings.", true);
        OpenDir(System.getProperty("user.dir"));
        /*if(dirList.size()== 0){
            dirList.addItem("NO CIA'S FOUND!");
        }*/
        //queueList.setModel(queueModel);
        //queueList.setSele
        //dirList.setModel(model);
        //dirList.setSelectedIndex(0);
    }
    public void resetControls(MaterialProgressBar progBar, MaterialDisplayText progText){
        
        progBar.setProgress(0);
        transferCount = 0;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
        progText.setText(String.format("%.2fmb of %.2fmb @ %%%.2f",0f, 0f,0f));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    // Simple method to add text to console box
    public void consoleWrite(String string, boolean withTime){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                String tempString;
        if(withTime){
            tempString = consoleText.getText() + getTimeStamp() + " - " + string + NEWLINE;
            if(saveLogChk.isSelected()){
                WriteLog(CONST_PATH, getTimeStamp() + " - " + string);
            }
            consoleText.setText(tempString);
        } else {
            tempString = consoleText.getText()+ string + NEWLINE;
            if(saveLogChk.isSelected()){
                WriteLog(CONST_PATH, string);
            }
            consoleText.setText(tempString);
        }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        //consoleText.setCaretPosition(consoleText.getDocument().getLength());
    }
    public String getTimeStamp(){
        
        return Dates.getToday("/")+" - "+Dates.getNow();
    }
    private final IntegerProperty length=new SimpleIntegerProperty(this,"length");
    public IntegerProperty lengthProperty(){
        return length;
    }
    private final LongProperty counter=new SimpleLongProperty(this,"counter");
    private LongProperty counterProperty(){
     
        return counter;
    }
    private final LongProperty cost = new SimpleLongProperty(SocketPunch.class,"cost");
    private LongProperty costProperty(){
        return cost;
    }
    public long getCounter(){
        return counterProperty().get();
    }
    public void setCounter(long val){
        counterProperty().set(val);
    }
    private boolean deleteWasSelected=false;
    public boolean connect(InstallableFile iFile, MaterialProgressBar progressBar, MaterialDisplayText progressText, MaterialDisplayText speedText,MaterialStandardListItem<InstallableFile> container){
		// Create socket
                File file=iFile.getFile();
                Socket socket;
                DataOutputStream out;
                BufferedOutputStream bos;
                PrintStream ps;
               try {
			socket = new Socket(ipText.getText(), 5000);
			out = new DataOutputStream(socket.getOutputStream());
                        bos=new BufferedOutputStream(out,bufferSize);
                                  ps=new PrintStream(bos, false);
		}catch(IOException e) {
                        // Only get 1 failed to open socket per file, else just say retrying
                        if(failCount == 0){
                            
                            consoleWrite(R.string.open_socket_failed, true);
                        } else {
                            consoleWrite(R.string.retrying_transfer, true);
                        }
			
			//resetControls();
			return false;
		}
       
                // Open file for streaming
		FileInputStream in;
                BufferedInputStream bis;
		try {
			in = new FileInputStream(file);
                         bis=           new BufferedInputStream(in); 
		} catch(IOException e) {
			consoleWrite(R.string.file_stream_failed, true);
                        //resetControls();
			return false;
		}
                // Send file
                counterProperty().set(0);
                //long counter = 0;
                int updateCount = 0;
                
			DecimalFormat dc=new DecimalFormat("0.00");
                        
		try {
			consoleWrite(R.string.sending_info, true);
			out.writeLong(file.length());
                        //progBar.((int) file.length());
			consoleWrite(R.string.get(R.string.sending_file,((float) file.length() / 1048576)+""), true);
			byte buffer[] = new byte[1024 * bufferSize];
			int length;
                        costProperty().set(System.currentTimeMillis());
                        lengthProperty().set(0);
                        //total.set(0);
                        
                        final long start = System.currentTimeMillis();
                        long startTime = System.nanoTime();
			while((length = bis.read(buffer)) != -1) {
				ps.write(buffer, 0, length);
                                ps.flush();
                                //total.set(total.get()+length);
                                //if(updateCheck.isSelected()){
                                //    lengthProperty().set(length);
                                    
                                //}
                                counterProperty().set(counterProperty().get() + length);
                                // Left this in just incase
                                if(updateCount < 0){
                                    updateCount++;
                                } else {
                                    updateCount = 0;
                                    if(updateBarCheck.isSelected()){
                                        progressBar.setProgress((double)getCounter()/(double)file.length());
                                    
                                    }
                                    
                                    Platform.runLater(new Runnable(){
                                        @Override
                                        public void run() {
                                            if(updatePercentageCheck.isSelected()){
                                                progressText.setText((int)((((float) getCounter() / 1048576) / ((float) file.length() / 1048576))* 100)+"%");
                                            
                                            }
                                            
                                            
                                            if(updateCheck.isSelected()){
                                                long cost = System.currentTimeMillis() - start;
                                                //long costFrame=System.currentTimeMillis()-costProperty().get();
                                                //costProperty().set(System.currentTimeMillis()); Remove comment for checking speed per text update
                                               //DecimalFormat dc = new DecimalFormat("0.00");
                                               double dProgress=(float) getCounter() / 1048576;
                                               String formattedProgress = dc.format(dProgress);
                                               double dTotal=(float) file.length() / 1048576;
                                               String formattedTotal=dc.format(dTotal);
                                               long lSpeed=(getCounter()/cost);
                                               
                                               double secondsLeft=((double)(dTotal*1000)-(double)(dProgress*1000))/(double)lSpeed;
                                               Hora hora=new Hora((int)secondsLeft);
                                               
                                               //speedText.setText(formattedProgress+"MB of "+formattedTotal+"MB at "+(lengthProperty().get()/cost)+"KB/s");
                                               
                                               //speedText.setText(hora.getAndroidLikeMinutesFormat()+" minutes left, "+formattedProgress+"MB of "+formattedTotal+"MB at "+lSpeed+"KB/s");
                                            
                                               speedText.setText(R.string.get(R.string.speed_update, new String[]{hora.getAndroidLikeMinutesFormat(),
                                               formattedProgress,formattedTotal,lSpeed+""}));
                                            }
                                            
                                            //progressText.setText(String.format("%.2fmb of %.2fmb @ %.2f%%",(float) getCounter() / 1048576, (float) file.length() / 1048576, (((float) getCounter() / 1048576) / ((float) file.length() / 1048576))* 100));
                                        
                                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                        }
                                    });
                                    
                            }
                        }
                        long endTime = System.nanoTime()-startTime;
                        //resetControls();
                        Hora segundosTranscurridos=new Hora((int)(endTime/ 1e9));
                        String minutes=segundosTranscurridos.getAndroidLikeMinutesFormat();
                        //String minutes=dc.format(/60);
                        String size=dc.format((float) file.length() / 1048576);
                        //deleteWasSelected=deleteCheck.isSelected();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                //speedText.setText(size+"MB sent successfully in "+minutes+" minutes at average "+(file.length()/(System.currentTimeMillis()-start))+"KB/s");
                                speedText.setText(R.string.get(R.string.successful_transfer,new String[]{
                                size,
                                    minutes,
                                    
                                (file.length()/(System.currentTimeMillis()-start))+""
                                
                                }));
                                progressText.setText("100%");
                                progressBar.setProgress(1.0f);
                                if(deleteCheck.isSelected() ){
                        
                                    queueList.removeItem(container);
                        
                                }
                                //if(deleteWasSelected){
                                //    container.setVisible(false);
                                            //queueList.removeItem(container);
                                           
                                //}
                                
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                        iFile.setTransferComplete(true);
                        //consoleWrite("- Success! " ++" sent in "++" minutes at average "++"KB/s", true);
                        consoleWrite(R.string.get(R.string.successful_console,new String[]{
                        file.getName(),
                        minutes,
                        (file.length()/(System.currentTimeMillis()-start))+""
                        }), true);
                        
                        return true;
		} catch(IOException e) {
                        //resetControls();
                        //e.printStackTrace();
			consoleWrite(R.string.get(R.string.failed_console,new String[]{((float) getCounter() / 1048576)+"", ((float) file.length() / 1048576)+"", ((((float) getCounter() / 1048576) / ((float) file.length() / 1048576))* 100)+""}), true);
                        return false;
		} finally {
			try {
                            bis.close();
				in.close();
                                
                                out.close();
                                socket.close();
				
			} catch(IOException e) {
			}
		}

    }
    private ArrayList<File> showCiaChooser(Stage mainStage){
        FileChooser fileChooser = new FileChooser();
        if(lastDir!=null){
            fileChooser.setInitialDirectory(new File(lastDir));
        }
       
 fileChooser.setTitle(R.string.open_files);
 fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("CIA Files", "*.cia"));
 List<File> selectedFiles = fileChooser.showOpenMultipleDialog(mainStage);
 if(selectedFiles!=null){
 ArrayList<File> files=new  ArrayList<File>();
 for(int i=0;i<selectedFiles.size();i++){
     files.add(selectedFiles.get(i));
 }
if(files.size()>0){
     lastDir=files.get(0).getParent();
     try {
         new FieldsFile("config.txt").setValue("lastdir",lastDir);
     } catch (FileNotFoundException ex) {
         Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
     }
}
 return files;
 }
 return null;
    }
    
    private void openCiaChooser(Stage stage){
        ArrayList<File> ciaFiles=showCiaChooser(stage);
                if(ciaFiles!=null){
                    if(ciaFiles.size()+queueList.size()<=30){
                        for (File ciaFile : ciaFiles) {
                            queueList.addItem(new InstallableFile(ciaFile));
                        }
                    }
                    else{
                        int i=0;
                        while(queueList.size()<30){
                            queueList.addItem(new InstallableFile(ciaFiles.get(i)));
                                    i++;
                        }
                        new MaterialToast(R.string.queue_limit,MaterialToast.LENGTH_LONG).unhide();
                    }
                    
                }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private Pane initComponents(Stage stage) {
        VBox box=new VBox();
        //progBar = new MaterialProgressBar(0.0f);
        //progBar.setColor(MaterialColor.BLUE);
        jScrollPane1 = new ScrollPane();
        
        //queueList = new javax.swing.JList();
        jScrollPane3 = new ScrollPane();
        ipText = new MaterialTextField(R.string.ip_address){
            @Override
            public boolean onError(String valor){
                
                if(valor.trim().equals("")){
                    return true;
                }
                return false;
            }
        };
        ipText.setErrorText(R.string.write_me);
        
        ipText.allowDot();
        ipText.lockLetters();
        
        
        
        jLabel1 = new MaterialDisplayText("");
        jLabel2 = new MaterialDisplayText("");
        jScrollPane2 = new ScrollPane();
        consoleText = new MaterialDisplayText("");
        
        consoleText.setFontSize(11);
        consoleText.setWrapText(true);
        jLabel3 = new MaterialDisplayText("");
        startButt = new MaterialButton("Punch!");
        //progText = new MaterialDisplayText("");
        jScrollPane4 = new ScrollPane();
        //dirList = new javax.swing.JList();
        jLabel4 = new MaterialDisplayText("");
        autotryChk = new MaterialCheckBox();
        timeoutVal = new MaterialTextField(R.string.timeout_tries){
        @Override
        public boolean onError(String val){
            if(val.trim().equals("")){
                return true;
            }
            return false;
        }
        
        };
        timeoutVal.setErrorText(R.string.a_five);
        jLabel5 = new MaterialDisplayText("");
        saveLogChk = new MaterialCheckBox();
        RaisedButton openButton=new RaisedButton("Open cias");
        openButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                openCiaChooser(stage);
                //System.out.println("aksdjkasjd");
            }
        });
        RaisedButton emptyButton=new RaisedButton("Empty list");
        emptyButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!startedSending){
                    queueList.clear();
                    new MaterialToast("List is empty now").unhide();
                
                }
                else{
                    new MaterialToast("You cannot clear the list with transfer in process").unhide();
                }
                
                //System.out.println("eqwq");
                
            }
        });
        emptyButton.setColor(MaterialColor.RED);
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        /*queueList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                queueListMouseClicked(evt);
            }
        });*/
        //jScrollPane1(queueList);
        VBox leftBox=new VBox();
        //ipText.setText("Enter IP Address");
        //ipText.setToolTipText("");
        //ipText.setName(""); // NOI18N
        //jScrollPane3.setViewportView(ipText);

       // jLabel1.setText("IP Address:   (This should be shown on device)");

        jLabel2.setText("CIA List:   (Double-Click to add to queue)");
        //leftBox.getChildren().addAll(openButton);
        //box.getChildren().addAll(jLabel2, dirList);
        
        //consoleText.setColumns(20);
        //consoleText.setFont(new java.awt.Font("Malgun Gothic", 0, 10)); // NOI18N
        //consoleText.setRows(5);
        //jScrollPane2.setViewportView(consoleText);

        jLabel3.setText("Console Output:");

        //startButt.setText("Punch!");
        startButt.setOnAction(startButtActionPerformed());

        //progText.setText(" ");

        /*dirList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dirListMouseClicked(evt);
            }
        });*/
        //jScrollPane4.setViewportView(dirList);

        jLabel4.setText("CIA's in queue:   (Double-Click to remove from queue)");
        //leftBox.getChildren().add(jLabel4);
        
        layout.setDrawerMenuPadding(new Insets(12));
        
        //box.getChildren().add(jLabel1);
        ipText.setPadding(new Insets(0,0,12,0));
        layout.addNodeAsDrawerItem(new HBox(ipText,timeoutVal));
        updatePercentageCheck=new CheckBox();
        updatePercentageCheck.setSelected(true);
        updatePercentageCheck.setText(R.string.update_percentage);
        updatePercentageCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    
                    new FieldsFile("config.txt").setValue("update", updatePercentageCheck.isSelected()+","+
                            updateBarCheck.isSelected()+","+updateCheck.isSelected()+","+deleteCheck.isSelected());
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        updateBarCheck=new CheckBox();
        updateBarCheck.setSelected(true);
        updateBarCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    
                    new FieldsFile("config.txt").setValue("update", updatePercentageCheck.isSelected()+","+
                            updateBarCheck.isSelected()+","+updateCheck.isSelected()+","+deleteCheck.isSelected());
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        updateBarCheck.setText(R.string.update_progress_bar);
        
        
        
        updateCheck=new CheckBox();
        updateCheck.setSelected(true);
        updateCheck.setText(R.string.update_speed_text);
        updateCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    
                    new FieldsFile("config.txt").setValue("update", updatePercentageCheck.isSelected()+","+
                            updateBarCheck.isSelected()+","+updateCheck.isSelected()+","+deleteCheck.isSelected());
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
          deleteCheck=new CheckBox();
          //deleteCheck.setDisable(true);
        deleteCheck.setSelected(true);
        deleteCheck.setText(R.string.clear_completed);
        deleteCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    
                    new FieldsFile("config.txt").setValue("update", updatePercentageCheck.isSelected()+","+
                            updateBarCheck.isSelected()+","+updateCheck.isSelected()+","+deleteCheck.isSelected());
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
                VBox updateBox=new VBox(updatePercentageCheck,updateBarCheck, updateCheck,deleteCheck);
                layout.addNodeAsDrawerItem(updateBox);
        //layout.addNodeAsDrawerItem(new VBox(updateCheck));
        
        
        ipText.setMinWidth(160);
        timeoutVal.setPadding(new Insets(0,0,0,8));
        HBox emptyBox=new HBox(emptyButton);
        emptyBox.setPadding(new Insets(0,0,0,8));
        //HBox openBox=new HBox(openButton,emptyBox);
        //openBox.setAlignment(Pos.CENTER);
        //layout.addNodeAsDrawerItem(openBox);
        layout.setMiniDrawerSize(0);
        //leftBox.getChildren().add(ipText);
        leftBox.getChildren().add(queueList);
        saveLogChk.setText("Save Log (Will be stored in working directory)");
        box.getChildren().add(new HBox(jLabel3,saveLogChk));
        VBox scrollBox=new VBox();
        consoleText.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                getHostServices().showDocument("file:log.txt");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        ScrollPane scroll=new ScrollPane(consoleText){
            @Override
            public void requestFocus(){
                
            }
        };
        consoleText.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                scroll.setVvalue(1.0);
            }
        });
        scrollBox.getChildren().add(scroll);        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        layout.addNodeAsDrawerItem(scrollBox);
        
        scrollBox.setPadding(new Insets(8,0,0,0));
        MaterialTooltip consoleTooltip=new MaterialTooltip(R.string.open_log,consoleText);
        scroll.setMinHeight(170);
        scroll.setMaxHeight(170);
        
        //box.getChildren().add(scroll);
        box.getChildren().add(startButt);
        autotryChk.setText("Auto-Retry");

        timeoutVal.setText("5");
        timeoutVal.lockLetters();
        
        jLabel5.setText("Timeout Tries:");
        box.getChildren().add(new HBox(jLabel5/*,timeoutVal*/));
        //box.getChildren().add(progBar);

        
        /*saveLogChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLogChkActionPerformed(evt);
            }
        });*/
        leftBox.setMinWidth(300);
       return new HBox(leftBox);
    }// </editor-fold>//GEN-END:initComponents

    private EventHandler<ActionEvent> startButtActionPerformed() {//GEN-FIRST:event_startButtActionPerformed
        return new EventHandler<ActionEvent>(){
        

            @Override
            public void handle(ActionEvent event) {
                if(!ipText.getText().trim().equals("")){
                if(queueList.size()>0){
                    //for (int i = 0; i < queueList.size(); i++) {
                    //InstallableFile item = queueList.getItem(i);
                    //MaterialStandardListItem<InstallableFile> itemContainer=queueList.getItemBox(i);
                    
                    //queueList.setSelectedIndex(i);
                    //MaterialProgressBar progreso=(MaterialProgressBar)itemContainer.lookup("#progreso");
                    //MaterialDisplayText progresoTexto=(MaterialDisplayText)itemContainer.lookup("#progresoTexto");
                    //MaterialDisplayText velocidadTexto=(MaterialDisplayText)itemContainer.lookup("#velocidadTexto");
                     //String formattedTotal=new DecimalFormat("0.00").format((float)     item.getFile().length() / 1048576);
                    //velocidadTexto.setText(formattedTotal+"MB");
                    //progreso.setProgress(0.0f);
                    //progresoTexto.setText("0%");
                    //}
                    new MaterialToast(R.string.you_punched).unhide();
                
                }
                else{
                    new MaterialToast(R.string.open_first).unhide();
                
                }
                        
                BackgroundTask task=new BackgroundTask() {
                    @Override
                    public Object onAction() {
                        if(!startedSending){
                // Set flag to true so we don't get multiple connects
                startedSending = true;
                failCount = 0;
                boolean connectStatus;
                // For each file in queue
                Socket socket;
		DataOutputStream out;
                
                 boolean started=startStreaming();
                 while(!started){
                     startStreaming();
                 }
                // Set flag false so a new send queue can be init
                startedSending = false;
                consoleWrite(R.string.queue_finished, true);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                         new MaterialToast(R.string.queue_finished).unhide();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
               
            } else {
                consoleWrite(R.string.transfer_in_progress, true);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                         new MaterialToast(R.string.transfer_in_progress).unhide();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            }
                        return null;
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void onSucceed(Object valor) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                /*Thread thread = new Thread("Connect") {
                    
        public void run(){
            
        }
   };*/
                task.play();
                }else{
                    new MaterialToast(R.string.check_fields).unhide();
                }
   //thread.start();//To change body of generated methods, choose Tools | Templates.
            }
            
        };
    }//GEN-LAST:event_startButtActionPerformed
    private boolean startStreaming(){
        boolean connectStatus;
        //Socket socket=null;
        //DataOutputStream out=null;
        
                
                 
        MaterialStandardListItem<InstallableFile> installContainer=hasInstallableFilesUncomplete(queueList);
        
        String fileName=(installContainer.getItem().getFile().getName());
        String lastFileName=installContainer.getItem().getFile().getName();
        int lap=0;
        while(installContainer!=null) {
                    if(lap>0 && fileName.equals(lastFileName) && failCount<getTimeoutVal()-1){
                        
                        failCount++;
                        
                    }
                    else if(lap>0){
                        lastFileName=fileName;
                        failCount=0;
                    }
                    //InstallableFile item = queueList.getItem(i);
                    InstallableFile item=installContainer.getItem();
                    
                    //queueList.setSelectedIndex(i);
                    
                    connectStatus = connect(item,(MaterialProgressBar)installContainer.lookup("#progreso"),(MaterialDisplayText)installContainer.lookup("#progresoTexto"),(MaterialDisplayText)installContainer.lookup("#velocidadTexto"),installContainer);
                    
                    installContainer=hasInstallableFilesUncomplete(queueList);
                    lap++;
                    if(installContainer!=null){
                        fileName=installContainer.getItem().getFile().getName();
                    }
//out.flush();      
                    if(failCount>=getTimeoutVal()-1){
                            break;
                        }
                    /*if(!connectStatus && autotryChk.isSelected()){
                        i--;
                    }else if(!connectStatus && failCount < getTimeoutVal() - 1){
                        // Lazy way to retry file, but since there is no real way
                        // to tell why it errored we will try set times each file
                        i--;
                        failCount++;
                    } else {
                        failCount = 0;
                    }*/
                }
                failCount=0;
           
                 return true;
    }
    private MaterialStandardListItem<InstallableFile> hasInstallableFilesUncomplete(MaterialStandardList<InstallableFile> list){
        for(int i=0;i<list.size();i++){
            if(!list.getItem(i).isTransferComplete()){
                return list.getItemBox(i);
            }
        }
        return null;
    }
    /*private void dirListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dirListMouseClicked
        if (evt.getClickCount() == 2) {
            // Double-click detected
            queueModel.addElement(dirList.getSelectedValue());
        } 
    }//GEN-LAST:event_dirListMouseClicked

    private void queueListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_queueListMouseClicked
        if (evt.getClickCount() == 2) {
            // Double-click detected
            queueModel.removeElement(queueList.getSelectedValue());
        }
    }//GEN-LAST:event_queueListMouseClicked*/

    private void saveLogChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLogChkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveLogChkActionPerformed

    /**
     * @param args the command line arguments
     */
    
    MaterialDisplayText consoleText;
    MaterialCheckBox autotryChk;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*JCheckBox autotryChk;
    JList dirList;
    JTextPane ipText;
    JLabel jLabel1;
    JLabel jLabel2;
    JLabel jLabel3;
    JLabel jLabel4;
    JLabel jLabel5;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane3;
    JScrollPane jScrollPane4;
    JProgressBar progBar;
    JLabel progText;
    JList queueList;
    JCheckBox saveLogChk;
    JButton startButt;
    JSpinner timeoutVal;*/
    // End of variables declaration//GEN-END:variables


    
    
    
    
    
    
    
    
    
    
    
    
}
