
package socketpunch;

import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.FlatButton;
import com.olmectron.material.components.FontWeight;
import com.olmectron.material.components.MaterialButton;
import com.olmectron.material.components.MaterialCard;
import com.olmectron.material.components.MaterialCheckBox;
import com.olmectron.material.components.MaterialComboBox;
import com.olmectron.material.components.MaterialDisplayText;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialFloatingButton;
import com.olmectron.material.components.MaterialFlowList;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialLabel;
import com.olmectron.material.components.MaterialProgressBar;
import com.olmectron.material.components.MaterialSelector;
import com.olmectron.material.components.MaterialStandardDialog;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import org.w3c.dom.NodeList;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.olmectron.material.components.MaterialStandardList;
import com.olmectron.material.components.MaterialStandardListItem;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTooltip;
import com.olmectron.material.components.RaisedButton;
import com.olmectron.material.components.menu.MaterialMenuItem;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.files.WatchDir;
import com.olmectron.material.layouts.MaterialPane;
import com.olmectron.material.utils.BackgroundTask;
import com.olmectron.material.utils.HTTPReader;
import com.olmectron.material.utils.MultipartUtility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
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
    private MaterialCheckBox updateCheck, deleteCheck;
    private MaterialCheckBox updatePercentageCheck;
    private MaterialCheckBox updateBarCheck;
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
        //R.string.initLanguagePack();
        MaterialDesign.setCustomPath("/socketpunch/images/");
        layout=new MainLayout() {
            @Override
            public void removeCompletedFiles(){
                int counter=0;
                
                 for(int i=queueList.size()-1;i>0;i--){
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

            @Override
            public void onCheckUpdate() {
                Updater.checkForUpdates(true);//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void showSettings() {
                
                
          
                updateDialog.unhide();
          
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        layout.setRootView(initAll(primaryStage));
        
        StackPane transparentContainer=new StackPane();
        
       transparentContainer.getStyleClass().add("full-transparent-container");
       
        Scene scene = new Scene(layout, 1050,600);
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
        
        //initDirectoryWatcher();
        
        //primaryStage.setResizable(false);
       layout.setTitle(R.string.app_title+" "+R.string.version);
       
        primaryStage.setOnShown(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                    Updater.checkForUpdates(false);
       
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        primaryStage.show();
       
    }
    
    
    private void initDirectoryWatcher(){
        try {
        File dir=new File("cia");
        if(!dir.exists()){
            dir.mkdir();
        }
            String rootPath="cia/";
        
        Path pathc = Paths.get(rootPath);
        WatchDir watchDir ;
        
            watchDir = new WatchDir(pathc, false);
        
watchDir.setNotificationExecutor(Platform::runLater);

watchDir.setOnCreate(path -> addCia(path.toString())); /* or path -> listView.getItems().add(path) */
Thread watchThread = new Thread(watchDir::processEvents);
watchThread.setDaemon(true);
watchThread.start();
} catch (IOException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    static String CTR="4354522D";
    static String GUION="2D";
    static String NCCH="4E434348";
    static String CTAP="43544150";
    //private MaterialStandardList<String> dirList;
    private MaterialFlowList<InstallableFile> queueList;
    boolean startedSending = false;
    int transferCount = 0;
    int failCount = 0;
    int bufferSize = 256;
    private XMLReader dbReader;
           public XMLReader getDatabaseReader(){
               if(dbReader==null){
                   dbReader=new XMLReader() {
                       @Override
                       public void onElementFound(MaterialStandardListItem<InstallableFile> item) {
                           //MaterialCard card=(MaterialCard)item.lookup("#the_card");
                           FileInfoPane p=new FileInfoPane(item.getItem());
                           outPane.setRight(p);
                           //card.addComponent(p);
                           //System.out.println("Name: "+foundFile.getName());
                           //System.out.println("Serial: "+foundFile.getSerial());
                           //System.out.println("Region: "+foundFile.getRegion());
                           
                           //System.out.println("Languages: "+foundFile.getLanguages());
                           
                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                       }
                   };
               }
               return dbReader;
           }
    public void reprintConsoleBuffer(){
           
        consoleWrite(R.string.get(R.string.buffer_set,Integer.toString(bufferSize)+""), true);
        
        }
    public static String getStringFromHex(String hex){
        if(hex.length()%2!=0){
            System.out.println(hex.length()%2+" "+hex);
            return null;
        }
        else{
            String result="";
            for(int i=0;i<hex.length();i+=2){
                String hexPair=hex.substring(i,i+2);
                if(!hexPair.equals("00")){
                    char newChar=(char)getIntFromHex(hexPair);
                    result=result+String.valueOf(newChar);
                }
            }
            return result;
        }
    }
    public static int getIntFromHex(String hex){
        if(!hex.startsWith("0x")){
            return Integer.decode("0x"+hex);
        }
        else{
            return Integer.decode(hex);
        }
    }

    private String findSerial(String searchBlock){
        int start=searchBlock.indexOf(NCCH);
        String CTRBlock=searchBlock.substring(start+(0x50*2), start+(0x50*2)+(0x0A*2));
        if(CTRBlock.startsWith(CTR) && CTRBlock.substring(10,12).equals(GUION)){
            return getStringFromHex(CTRBlock);
        }
        
        return null;
    }
    public Pane initAll(Stage st) {
        st.setTitle("SocketPunch MOD "+R.string.version);
        StackPane root = new StackPane();
        
        queueList=new MaterialFlowList<InstallableFile>(root) {
        @Override
        public void onItemClick(InstallableFile item, MouseEvent event) {
            
        }
        @Override
        public void onItemContainerClick(MaterialStandardListItem<InstallableFile> itemBox,MouseEvent event){
            //MaterialDisplayText tb = (MaterialDisplayText) itemBox.lookup("#progresoTexto");
            String detailString=R.string.show_details;
            if(itemBox.getItem().isShowingDetails()){
                detailString=R.string.hide_details;
            }
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
                dropdown.addItem(new MaterialDropdownMenuItem(detailString){
                    @Override
                    public void onItemClick(){
                        getDatabaseReader().findTitleId(itemBox);
                        //if(!startedSending){
                            //queueList.removeItem(itemBox);
                        //}
                    }
                });
                dropdown.unhide();
            }
        }
        
        @Override
        public Node itemConverter(InstallableFile it,MaterialStandardListItem<InstallableFile> container) {
            container.setOnMousePressed(onCardPressed);
            container.setOnMouseDragged(onCardDragged);
            container.setOnMouseReleased(onCardReleased);
            
            File item=it.getFile();
            String hex="";
            String serial="";
                String titleId="";
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(item, "r");
                raf.seek(0x2C1C);
                for(int i=0;i<8;i++){
                    int part=raf.readByte();
                    if(part<0){
                        part= part & 0xff;
                    }
                    String hexPart=Integer.toHexString(part);
                    if(hexPart.length()==1){
                        hexPart="0"+hexPart;
                    }
                    titleId=titleId+hexPart;
                }
                titleId=titleId.toUpperCase();
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
                raf.seek(0x3000);
                String search="";
                for(int i=0;i<0x1000;i++){
                    int part=raf.readByte();
                    if(part<0){
                        part= part & 0xff;
                    }
                    String hexPart=Integer.toHexString(part).toUpperCase();
                    if(hexPart.length()==1){
                        hexPart="0"+hexPart;
                    }
                    search=search+hexPart;
                }
                serial=findSerial(search);
                
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
            it.setSerial(serial);
            it.setTitleId(titleId);
             // Goes to 400th byte.
            
            MaterialCard card=new MaterialCard();
            card.setId("the_card");
            
            MaterialDisplayText texto=new MaterialDisplayText(item.getName());
            //MaterialTooltip tooltip=new MaterialTooltip(item.getName(),texto);
            texto.setAlignment(Pos.CENTER_LEFT);
            card.activateShadow(1);
            texto.setColorCode(MaterialColor.material.BLACK_87);
            texto.setFontSize(15);
            
            //card.setZ(bufferSize);
            HBox spanBox=new HBox();
            HBox.setHgrow(spanBox, Priority.ALWAYS);
            
           
            
            MaterialDisplayText progText=new MaterialDisplayText("0%");
            progText.setMinWidth(60);
            progText.setAlignment(Pos.CENTER_RIGHT);
            progText.setColorCode(MaterialColor.material.BLUE);
            
            progText.setFontWeight(FontWeight.MEDIUM);
            progText.setId("progresoTexto");
            //MaterialDisplayText hexText=new MaterialDisplayText(titleId);
            MaterialDisplayText hexText=new MaterialDisplayText(R.string.get(R.string.initial_code, "0x"+hex.toUpperCase()));
            if(it.getSerial()!=null){
                hexText.setText(hexText.getText()+" "+it.getSerial());
            }
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
            card.setCardWidth(500);
            //card.setExactWidth(orgSceneX);
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
        consoleWrite("         SocketPunch MOD "+R.string.version+" by Olmectron", false);
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
       
         outPane=new BorderPane();
         outPane.setCenter(p);
         
          pane.setRootComponent(outPane);
          
        return pane;
    }
    
    private BorderPane outPane;
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
                fieldFile.addField("buffersize", "256");
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
                bufferSize=Integer.parseInt(fieldFile.getValue("buffersize","256"));
                
                    if(fieldFile.getValue("first_time","true").equals("true")){
                        fieldFile.setValue("buffersize", "256");
                        bufferSize=256;
                        fieldFile.setValue("first_time","false");
                    }
                    
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
            tempString = consoleText.getText() + getTimeStamp(false) + " - " + string + NEWLINE;
            if(saveLogChk.isSelected()){
                WriteLog(CONST_PATH, getTimeStamp(true) + " - " + string);
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
    public String getTimeStamp(boolean includeDate){
        if(includeDate)
        return Dates.getToday("/")+" - "+Dates.getNow();
        else
            return Dates.getNow();
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
    
    //Sockfile 2.0 socket method
    //startSteveStream()
    //Original from Steveice10. Thanks a lot!
    private void startSteveStream(){
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        BufferedOutputStream bos=null;
        ArrayList<MaterialStandardListItem<InstallableFile>> sendItems=new ArrayList<>();
        for(int i=0;i<queueList.size();i++){
            if(!queueList.getItem(i).isTransferComplete()){
                sendItems.add(queueList.getItemBox(i));
            }
        }
        try {
            socket = new Socket(ipText.getText(), 5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());   
            bos=new BufferedOutputStream(out,bufferSize);
            out.writeInt(sendItems.size());
        } catch(IOException e) {
            try {
                socket.close();
            } catch(Exception e1) {
            }

            //System.err.println("Failed to open socket and send header.");
            System.err.println(R.string.open_socket_failed);
            e.printStackTrace();
            return;
        }

        System.out.println(R.string.sending_info);
        
        
        for(int i = 0; i < sendItems.size(); i++) {
            FileInputStream fileIn = null;
            MaterialProgressBar progressBar=(MaterialProgressBar)sendItems.get(i).lookup("#progreso");
MaterialDisplayText progressText=(MaterialDisplayText)sendItems.get(i).lookup("#progresoTexto");
MaterialDisplayText speedText=(MaterialDisplayText)sendItems.get(i).lookup("#velocidadTexto");   
            BufferedInputStream bis=null;
            long fileLength=0;
            try {
                 File file = sendItems.get(i).getItem().getFile();
                 fileLength=file.length();
                /*if(!file.exists()) {
                    System.err.println("File \"" + file.getName() + "\" does not exist.");
                    continue;
                }*/

                int ack = in.readByte();
                if(ack == 0) {
                    
                    System.out.println(R.string.send_cancelled_remote);
                    consoleWrite(R.string.send_cancelled_remote,true);
                
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                     
                    new MaterialToast(R.string.send_cancelled_remote).unhide();
                        }});
                    break;
                }
                fileIn = new FileInputStream(file);
                bis=new BufferedInputStream(fileIn);
                //System.out.println("Sending info for \"" + file.getName() + "\"...");
                consoleWrite(R.string.get(R.string.sending_file_info, file.getName()),true);
                
                out.writeLong(file.length());

                //System.out.println("Sending data for \"" + file.getName() + "\"...");
                consoleWrite(R.string.get(R.string.sending_file, new DecimalFormat("0.00").format(((float) file.length() / 1048576))),true);
                byte buffer[] = new byte[1024 * 256];
                int length = 0;
                counterProperty().set(0);
                final long start = System.currentTimeMillis();
                long startTime = System.nanoTime();
                while((length = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                    
                    counterProperty().set(counterProperty().get() + length);
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            if(updatePercentageCheck.isSelected()){
                                                progressText.setText((int)((((float) getCounter() / 1048576) / ((float) file.length() / 1048576))* 100)+"%");
                                            
                                            }
                             if(updateBarCheck.isSelected()){
                                        progressBar.setProgress((double)getCounter()/(double)file.length());
                                    
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
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    
                }
                
                
                long endTime = System.nanoTime()-startTime;
                        //resetControls();
                        Hora segundosTranscurridos=new Hora((int)(endTime/ 1e9));
                        String minutes=segundosTranscurridos.getAndroidLikeMinutesFormat();
                        //String minutes=dc.format(/60);
                        String size=dc.format((float) file.length() / 1048576);
                        
                sendItems.get(i).getItem().setTransferComplete(true);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        speedText.setText(R.string.get(R.string.successful_transfer,new String[]{
                                size,
                                    minutes,
                                    
                                (file.length()/(System.currentTimeMillis()-start))+""
                                
                                }));
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                //System.out.println("File \"" + file.getName() + "\" sent successfully.");
                
                
                 consoleWrite(R.string.get(R.string.successful_console,new String[]{
                        file.getName(),
                        minutes,
                        (file.length()/(System.currentTimeMillis()-start))+""
                        }), true);
                 
                if(i == sendItems.size() - 1) {
                    //System.out.println("All files sent successfully.");
                    consoleWrite(R.string.queue_finished, true);
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            new MaterialToast(R.string.queue_finished).unhide();
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                     
                    
                }
            } catch(IOException e) {
                //System.err.println("Failed to send file \"" + sendItems.get(i).getItem().getFile().getName() + "\".");
                String text=R.string.get(R.string.failed_console,new String[]{((float) getCounter() / 1048576)+"", ((float) fileLength / 1048576)+"", ((((float) getCounter() / 1048576) / ((float) fileLength / 1048576))* 100)+""});
			consoleWrite(text, true);
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                new MaterialToast(text).unhide();
                        
                                speedText.setText(text);
                                speedText.setColorCode(MaterialColor.material.RED);
                            }
                        });
                e.printStackTrace();
                return;
            } finally {
                try {
                    
                    fileIn.close();
                    bis.close();
                    
                } catch(Exception e) {
                }
            }
        }

        try {out.close();
                    bos.close();
            socket.close();
        } catch(Exception e) {
        }
    }private static DecimalFormat dc=new DecimalFormat("0.00");
    public void startStreaming(){
		// Create socket
                //File file=iFile.getFile();
                Socket socket=null;
                DataOutputStream out=null;
                DataInputStream in=null;
                BufferedOutputStream bos=null;
                //PrintStream ps;
               try {
			socket = new Socket(ipText.getText(), 5000);
                        //socket.setSendBufferSize(bufferSize);
                        //socket.setSoTimeout(20000);
                       in =new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
                        bos=new BufferedOutputStream(out,bufferSize);
                                  //ps=new PrintStream(bos, false);
                        out.writeInt(queueList.size());
		}catch(IOException e) {
                        // Only get 1 failed to open socket per file, else just say retrying
                        if(failCount == 0){
                            
                            consoleWrite(R.string.open_socket_failed, true);
                        } else {
                            consoleWrite(R.string.retrying_transfer, true);
                        }
			
			//resetControls();
		}
       
                // Open file for streaming
		MaterialStandardListItem<InstallableFile> installContainer=hasInstallableFilesUncomplete(queueList);
                while(installContainer!=null){
                    File file=installContainer.getItem().getFile();
                    
MaterialProgressBar progressBar=(MaterialProgressBar)installContainer.lookup("#progreso");
MaterialDisplayText progressText=(MaterialDisplayText)installContainer.lookup("#progresoTexto");
MaterialDisplayText speedText=(MaterialDisplayText)installContainer.lookup("#velocidadTexto");                  
                    
                    
                BufferedInputStream bis;
                FileInputStream fis;
		try {
                    /*int ack=in.readByte();
                    if(ack==0){
                        new MaterialToast(R.string.send_cancelled_remote);
                    }*/
			fis= new FileInputStream(file);
                         bis=           new BufferedInputStream(in); 
		} catch(IOException e) {
			consoleWrite(R.string.file_stream_failed, true);
                        //resetControls();
                        continue;
			//return false;
		}
                // Send file
                counterProperty().set(0);
                //long counter = 0;
                int updateCount = 0;
                
			DecimalFormat dc=new DecimalFormat("0.00");
                        
		try {
			consoleWrite(R.string.sending_info, true);
			out.writeLong(file.length());
                        //out.flush();
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
				bos.write(buffer, 0, length);
                                //bos.flush();
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
                        
                                    queueList.removeItem(installContainer);
                        
                                }
                                //if(deleteWasSelected){
                                //    container.setVisible(false);
                                            //queueList.removeItem(container);
                                           
                                //}
                                
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       
                        installContainer.getItem().setTransferComplete(true);
                        //consoleWrite("- Success! " ++" sent in "++" minutes at average "++"KB/s", true);
                        consoleWrite(R.string.get(R.string.successful_console,new String[]{
                        file.getName(),
                        minutes,
                        (file.length()/(System.currentTimeMillis()-start))+""
                        }), true);
                        
                        //return true;
		} 
                catch(SocketTimeoutException ex){
                    System.err.println(ex.getMessage());
                    continue;
                }
                catch(IOException e) {
                        //resetControls();
                        //e.printStackTrace();
                        String text=R.string.get(R.string.failed_console,new String[]{((float) getCounter() / 1048576)+"", ((float) file.length() / 1048576)+"", ((((float) getCounter() / 1048576) / ((float) file.length() / 1048576))* 100)+""});
			new MaterialToast(text).unhide();
                        consoleWrite(text, true);
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                //speedText.setText(size+"MB sent successfully in "+minutes+" minutes at average "+(file.length()/(System.currentTimeMillis()-start))+"KB/s");
                                speedText.setText(text);
                                speedText.setColorCode(MaterialColor.material.RED);
                                //progressText.setText("100%");
                                //progressBar.setProgress(1.0f);
                                //if(deleteCheck.isSelected() ){
                        
                                //    queueList.removeItem(container);
                        
                                //}
                                //if(deleteWasSelected){
                                //    container.setVisible(false);
                                            //queueList.removeItem(container);
                                           
                                //}
                                
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                        
                        
                        continue;
		} finally {
			try {
                            bis.close();
				
				
			} catch(IOException e) {
			}
		}
                }
                
                try{
                    in.close();
                                
                                out.close();
                                socket.close();
                }
                catch(IOException ex){
                    
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
 
 
 
 
 List<File> selectedFiles = null;
 
 try{selectedFiles=fileChooser.showOpenMultipleDialog(mainStage);}
 catch(IllegalArgumentException ex){
     fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
     selectedFiles=fileChooser.showOpenMultipleDialog(mainStage);
 }
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
    private void addCia(String path){
        //System.out.println(path+" --- Watch path file");
        File f=new File(path);
        if(f.getName().toLowerCase().endsWith(".cia")){
           
              System.out.println(f.getName());
              
            
            
        }
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
        public boolean connect(InstallableFile iFile, MaterialProgressBar progressBar, MaterialDisplayText progressText, MaterialDisplayText speedText,MaterialStandardListItem<InstallableFile> container){
		// Create socket
                File file=iFile.getFile();
                Socket socket;
                DataOutputStream out;
                BufferedOutputStream bos;
                //PrintStream ps;
               try {
			socket = new Socket(ipText.getText(), 5000);
                        //socket.setSendBufferSize(bufferSize);
                        socket.setSoTimeout(20000);
                       
			out = new DataOutputStream(socket.getOutputStream());
                        bos=new BufferedOutputStream(out,bufferSize);
                                  //ps=new PrintStream(bos, false);
                                  
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
                        out.flush();
                        //progBar.((int) file.length());
			consoleWrite(R.string.get(R.string.sending_file,((float) file.length() / 1048576)+""), true);
			byte buffer[] = new byte[1024 * bufferSize];
			int length;
                        costProperty().set(System.currentTimeMillis());
                        lengthProperty().set(0);
                        //total.set(0);
                        
                        final long start = System.currentTimeMillis();
                        long startTime = System.nanoTime();
			while((length = bis.read(buffer)) >= 0) {
				bos.write(buffer, 0, length);
                                //ps.flush();
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
		} 
                catch(SocketTimeoutException ex){
                    System.err.println(ex.getMessage());
                    return false;
                }
                catch(IOException e) {
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
        private boolean startStreamingLegacy(){
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
        
        private void legacySendFiles(){
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
                    consoleWrite(R.string.sending_info,true);
                    new MaterialToast(R.string.you_punched).unhide();
                
               
                        
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
                
                 boolean started=startStreamingLegacy();
                 while(!started){
                     startStreamingLegacy();
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
        }
        private void newSendFiles(){
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
                    consoleWrite(R.string.sending_info,true);
                    new MaterialToast(R.string.you_punched).unhide();
                
               
                        
                BackgroundTask task=new BackgroundTask() {
                    @Override
                    public Object onAction() {
                //        if(!startedSending){
                // Set flag to true so we don't get multiple connects
                //startedSending = true;
                failCount = 0;
                //oolean connectStatus;
                // For each file in queue
                //Socket socket;
		//DataOutputStream out;
                
                 startSteveStream();
                 //while(!started){
                 //    startStreaming();
                 //}
                // Set flag false so a new send queue can be init
               // startedSending = false;
                //consoleWrite(R.string.queue_finished, true);
                /*Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                         new MaterialToast(R.string.queue_finished).unhide();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });*/
               
            /*} else {
                consoleWrite(R.string.transfer_in_progress, true);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                         new MaterialToast(R.string.transfer_in_progress).unhide();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            }*/
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
        }
        private RadioButton legacyRadio, newRadio;
        private MaterialStandardDialog updateDialog;
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
        
       ToggleGroup group = new ToggleGroup();
       String selectedRadio=null; 
       try {
            selectedRadio=new FieldsFile("config.txt").getValue("transfer_mode","new");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
        }

       
legacyRadio = new RadioButton(R.string.legacy_mode);
legacyRadio.setToggleGroup(group);
if(selectedRadio.equals("legacy")){
    legacyRadio.setSelected(true);
    consoleWrite(R.string.legacy_mode,true);
    
}

newRadio = new RadioButton(R.string.newest_mode);
if(selectedRadio.equals("new")){
    newRadio.setSelected(true);
    
    consoleWrite(R.string.newest_mode,true);
}

legacyRadio.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    FieldsFile confFile=new FieldsFile("config.txt");
                    if(legacyRadio.isSelected()){
                        confFile.setValue("transfer_mode","legacy");
                        
                    consoleWrite(R.string.legacy_mode,true);
                    }
                    else if(newRadio.isSelected()){
                        confFile.setValue("transfer_mode","new");
                        
    consoleWrite(R.string.newest_mode,true);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    newRadio.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    FieldsFile confFile=new FieldsFile("config.txt");
                    if(legacyRadio.isSelected()){
                        confFile.setValue("transfer_mode","legacy");
                        
    consoleWrite(R.string.legacy_mode,true);
                    }
                    else if(newRadio.isSelected()){
                        confFile.setValue("transfer_mode","new");
    consoleWrite(R.string.newest_mode,true);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    


VBox radios=new VBox(legacyRadio, newRadio);
legacyRadio.getStyleClass().add("material-radio-button");
newRadio.getStyleClass().add("material-radio-button");

radios.setAlignment(Pos.CENTER);
legacyRadio.setPadding(new Insets(0,8,8,8));
newRadio.setPadding(new Insets(8));
radios.setPadding(new Insets(0,0,12,0));
new MaterialTooltip(R.string.legacy_compatible,legacyRadio);
new MaterialTooltip(R.string.newest_compatible,newRadio);


layout.addNodeAsDrawerItem(radios);

newRadio.setToggleGroup(group);
 
        layout.addNodeAsDrawerItem(new HBox(ipText,timeoutVal));
        updatePercentageCheck=new MaterialCheckBox();
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
        updateBarCheck=new MaterialCheckBox();
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
        
        
        
        updateCheck=new MaterialCheckBox();
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
          deleteCheck=new MaterialCheckBox();
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
        
        updateDialog=new MaterialStandardDialog() {
            @Override
            public void onPressedKey(KeyEvent event) {
                
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onReleasedKey(KeyEvent event) {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCreate(Pane pane) {
                this.setCardPadding(new Insets(16));
                VBox updateBox=new VBox(updatePercentageCheck,updateBarCheck, updateCheck,deleteCheck);
                
                addNode(updateBox);
               /*MaterialButton updateClose= new MaterialButton(R.string.ok);
               updateClose.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        dismiss();
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                addNode(updateClose);*/
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDialogShown() {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDialogHidden() {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDialogKeyReleased(KeyEvent event) {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDialogKeyPressed(KeyEvent event) {
                 //To change body of generated methods, choose Tools | Templates.
            }
        };
               
                
                //layout.addNodeAsDrawerItem(updateButton);
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
                    if(legacyRadio.isSelected()){
                        legacySendFiles();
                    }
                    else if(newRadio.isSelected()){
                        newSendFiles();
                    }
                   /* if(legacy){
                        legacySendFiles();
                    }
                    else{
                        newSendFiles();
                    }*/
                    //send files
                 }
                else{
                    new MaterialToast(R.string.open_first).unhide();
                
                }
                }else{
                    new MaterialToast(R.string.check_fields).unhide();
                }
   //thread.start();//To change body of generated methods, choose Tools | Templates.
            }
            
        };
    }//GEN-LAST:event_startButtActionPerformed
    private boolean startStreamingOld(){
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
                    
                   // connectStatus = connect(item,(MaterialProgressBar)installContainer.lookup("#progreso"),(MaterialDisplayText)installContainer.lookup("#progresoTexto"),(MaterialDisplayText)installContainer.lookup("#velocidadTexto"),installContainer);
                    
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
    private MaterialStandardListItem<InstallableFile> hasInstallableFilesUncomplete(MaterialFlowList<InstallableFile> list){
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
    private double orgSceneX=0, orgSceneY=0;
    private double orgTranslateX=0, orgTranslateY=0;
    private int pressedIndex=-1;
    private double itemHeight=0;
    private double itemWidth=0;
    
    
    private int getIndexOfItem(MaterialStandardListItem<InstallableFile> item){
        for(int i=0;i<queueList.size();i++){
            if(queueList.getItemBox(i).equals(item)){
                return i;
            }
        }
        return -1;
    }
    
    private EventHandler<MouseEvent> onCardPressed=new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            orgSceneX=event.getSceneX();
            orgSceneY=event.getSceneY();
            orgTranslateX=((MaterialStandardListItem<InstallableFile>)event.getSource()).getTranslateX();
            orgTranslateY=((MaterialStandardListItem<InstallableFile>)event.getSource()).getTranslateY();
            pressedIndex=getIndexOfItem(((MaterialStandardListItem<InstallableFile>)event.getSource()));
            itemHeight=(((MaterialStandardListItem<InstallableFile>)event.getSource()).getHeight());
            itemWidth=(((MaterialStandardListItem<InstallableFile>)event.getSource()).getWidth());
            
            //System.out.println(orgTranslateY+" y-move");
            //System.out.println(((MaterialStandardListItem<InstallableFile>)event.getSource()).getLayoutY()+" y-layout");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    private void swapNodes(int index1, int index2){
        boolean continuar=true;
        if(index1>index2){
            int aux=index2;
            index2=index1;
            index1=aux;
        }
        else if(index1==index2){
            continuar=false;
            System.err.println("Both indexes are the same. They should be different");
        }
        if(continuar){
            MaterialStandardListItem<InstallableFile> item2=queueList.removeItem(index2);
            MaterialStandardListItem<InstallableFile> item1=queueList.removeItem(index1);
            queueList.insertItemAt(item2, index1);
            queueList.insertItemAt(item1, index2);
            
        }
            
    }
    private void moveOriginal(MaterialStandardListItem<InstallableFile> pressedItem,MaterialStandardListItem<InstallableFile> cycleItem, int indexToGo){
        Timeline timeline=new Timeline();

                        KeyValue kv1=new KeyValue(pressedItem
                        .translateXProperty(),cycleItem.getLayoutX()-pressedItem.getLayoutX());
                        KeyValue kv2=new KeyValue(pressedItem
                        .translateYProperty(),cycleItem.getLayoutY()-pressedItem.getLayoutY());
                        KeyFrame kf=new KeyFrame(Duration.millis(130),kv2,kv1);
                        timeline.getKeyFrames().add(kf);
                        timeline.setOnFinished(new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent event) {
                                    pressedItem.setTranslateX(0);
                                    pressedItem.setTranslateY(0);
                                    cycleItem.setTranslateX(0);
                                    cycleItem.setTranslateY(0);
                                    swapNodes(indexToGo, pressedIndex);
                                    
                                    //queueList.insertItemAt(queueList.removeItem(draggedFile), 1);
                                    //queueList.getItemBox(0).setTranslateY(0);
                                    //queueList.getItemBox(0).setTranslateX(0);
                                    //queueList.getItemBox(1).setTranslateY(0);
                                    //queueList.getItemBox(1).setTranslateX(0);



                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                        timeline.play();
    }
    private void moveReplaced(MaterialStandardListItem<InstallableFile> cycleItem,MaterialStandardListItem<InstallableFile> releasedItem, int myIndex){
        Timeline timeline=new Timeline();

        KeyValue kv1=new KeyValue(cycleItem
                        .translateXProperty(),releasedItem.getLayoutX()-cycleItem.getLayoutX());
                        KeyValue kv2=new KeyValue(cycleItem
                        .translateYProperty(),releasedItem.getLayoutY()-cycleItem.getLayoutY());
                        KeyFrame kf=new KeyFrame(Duration.millis(250),kv2,kv1);
                        timeline.getKeyFrames().add(kf);
                        timeline.setOnFinished(new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent event) {
                                    moveOriginal(releasedItem,cycleItem,myIndex);
                                    //queueList.insertItemAt(queueList.removeItem(draggedFile), 1);
                                    //queueList.getItemBox(0).setTranslateY(0);
                                    //queueList.getItemBox(0).setTranslateX(0);
                                    //queueList.getItemBox(1).setTranslateY(0);
                                    //queueList.getItemBox(1).setTranslateX(0);



                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                        timeline.play();
    }
    private EventHandler<MouseEvent> onCardReleasedTest=new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
        MaterialStandardListItem<InstallableFile> releasedItem=((MaterialStandardListItem<InstallableFile>)event.getSource());
            //System.out.println(releasedItem.getLayoutY()+" great layout Y");
        }};
    private EventHandler<MouseEvent> onCardReleased=new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            
            
            
            
            MaterialStandardListItem<InstallableFile> releasedItem=((MaterialStandardListItem<InstallableFile>)event.getSource());
            boolean replace=false;
            //System.out.println(releasedItem.getTranslateY()+" y-released");
                double newLayoutY=releasedItem.getLayoutY()+releasedItem.getTranslateY();
                    double newLayoutX=releasedItem.getLayoutX()+releasedItem.getTranslateX();
                
            for(int i=0;i<queueList.size();i++){
                if(!queueList.getItemBox(i).equals(releasedItem)){
                    MaterialStandardListItem<InstallableFile> cycleItem=queueList.getItemBox(i);
                    if((newLayoutY<cycleItem.getLayoutY()+33 && newLayoutY>cycleItem.getLayoutY()-33) &&
                            (newLayoutX<cycleItem.getLayoutX()+33 && newLayoutX>cycleItem.getLayoutX()-33)){
                            replace=true;
                            moveReplaced(cycleItem,releasedItem,i);
                            
                        }
                }
            }
            if(!replace){
                Timeline timeline=new Timeline();
            KeyValue kv=new KeyValue(((MaterialStandardListItem<InstallableFile>)event.getSource())
            .translateXProperty(),orgTranslateX);
            KeyValue kv2=new KeyValue(((MaterialStandardListItem<InstallableFile>)event.getSource())
            .translateYProperty(),orgTranslateY);
            KeyFrame kf=new KeyFrame(Duration.millis(250),kv,kv2);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            }
            
            
            
            
            //System.out.println(+" y-move");
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    };
    private EventHandler<MouseEvent> onCardDragged=new  EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            double offsetX=event.getSceneX()-orgSceneX;
            double offsetY=event.getSceneY()-orgSceneY;
            double newTranslateX=orgTranslateX+offsetX;
            double newTranslateY=orgTranslateY+offsetY;
            //System.out.println(newTranslateY+" y-dragged");
            MaterialStandardListItem<InstallableFile> draggedFile=((MaterialStandardListItem<InstallableFile>)event.getSource());
            draggedFile.setTranslateX(newTranslateX);
            draggedFile.setTranslateY(newTranslateY);     
            
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
        }
    };
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
