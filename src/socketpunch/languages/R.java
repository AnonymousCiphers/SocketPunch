package socketpunch.languages;

import com.olmectron.material.MaterialDesign;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.files.FilesList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketpunch.SocketPunch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edgar
 */
public class R {
 
    public static class string{
        
        private static CustomMap fileMap;
        private static String languageName;
        private static String getLanguageName(){
            if(languageName==null){
                try {
            FieldsFile config=new FieldsFile("config.txt");
            languageName=config.getValue("language", "english");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SocketPunch.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
            return languageName;
        }
        private static CustomMap getInternalLanguageFile(){
            if(fileMap==null){
                fileMap=new CustomMap();
                
                if(!getLanguageName().toLowerCase().contains(".txt")){
                    InputStreamReader inUTF8 = null;
                    InputStream in=null;
                    BufferedReader input=null;
                    try {
                        System.out.println(getLanguageName().toLowerCase());
                        in = R.class.getResourceAsStream("/socketpunch/languages/files/"+getLanguageName().toLowerCase()+".txt");
                        //inUTF8 = new  InputStreamReader(in,"UTF8");
                        input = new BufferedReader(new  InputStreamReader(in,"UTF8"));
                        input.lines().forEach(new Consumer<String>(){
                            
                            @Override
                            public void accept(String t) {
                                if(t.contains("=")){
                                    StringTokenizer tok=new StringTokenizer(t,"=");
                                    fileMap.putString(tok.nextToken().trim(),tok.nextToken().trim());
                                }
                                //System.out.println("Linea "+t);
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if(inUTF8!=null)
                            inUTF8.close();
                            if(in!=null)
                            in.close();
                            if(input!=null)
                            input.close();
                        } catch (IOException ex) {
                            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                else{
                    try {
                        FieldsFile langFile=new FieldsFile("languages/"+getLanguageName());
                        FilesList fieldsList=langFile.getFieldList();
                        for(int i=0;i<fieldsList.size();i++){
                            
                           fileMap.putString(fieldsList.getKeyAt(i),fieldsList.getValueAt(i));
                            
                        }
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
            return fileMap;
        }
        public static String clear_list=getInternalLanguageFile().getString("clear_list");
        public static String empty_list=getInternalLanguageFile().getString("empty_list");
        public static String warning_clear_list=getInternalLanguageFile().getString("warning_clear_list");
        public static String warning_buffer_size=getInternalLanguageFile().getString("warning_buffer_size");
        public static String no_cia_file_error=getInternalLanguageFile().getString("no_cia_file_error");
        public static String buffer_set=getInternalLanguageFile().getString("buffer_set");
        public static String delete_queue_item=getInternalLanguageFile().getString("delete_queue_item");
        public static String initial_code=getInternalLanguageFile().getString("initial_code");
        public static String network_problem=getInternalLanguageFile().getString("network_problem");
        public static String open_socket_failed=getInternalLanguageFile().getString("open_socket_failed");
        public static String retrying_transfer=getInternalLanguageFile().getString("retrying_transfer");
        public static String file_stream_failed=getInternalLanguageFile().getString("file_stream_failed");
        public static String sending_info=getInternalLanguageFile().getString("sending_info");
        public static String sending_file=getInternalLanguageFile().getString("sending_file");
        public static String speed_update=getInternalLanguageFile().getString("speed_update");
        public static String app_title=getInternalLanguageFile().getString("app_title");
        public static String successful_transfer=getInternalLanguageFile().getString("successful_transfer");
        public static String successful_console=getInternalLanguageFile().getString("successful_console");
        public static String failed_console=getInternalLanguageFile().getString("failed_console");
        public static String open_files=getInternalLanguageFile().getString("open_files");
        public static String queue_limit=getInternalLanguageFile().getString("queue_limit");
        public static String ip_address=getInternalLanguageFile().getString("ip_address");
        public static String write_me=getInternalLanguageFile().getString("write_me");
        public static String timeout_tries=getInternalLanguageFile().getString("timeout_tries");
        public static String a_five=getInternalLanguageFile().getString("a_five");
        public static String update_percentage=getInternalLanguageFile().getString("update_percentage");
        public static String update_progress_bar=getInternalLanguageFile().getString("update_progress_bar");
        public static String update_speed_text=getInternalLanguageFile().getString("update_speed_text");
        public static String clear_completed=getInternalLanguageFile().getString("clear_completed");
        public static String open_log=getInternalLanguageFile().getString("open_log");
        
        public static String you_punched=getInternalLanguageFile().getString("you_punched");
        public static String open_first=getInternalLanguageFile().getString("open_first");
        public static String queue_finished=getInternalLanguageFile().getString("queue_finished");
        public static String transfer_in_progress=getInternalLanguageFile().getString("transfer_in_progress");
        public static String check_fields=getInternalLanguageFile().getString("check_fields");
        public static String advanced_options=getInternalLanguageFile().getString("advanced_options");
        public static String edit_fields=getInternalLanguageFile().getString("edit_fields");
        public static String save=getInternalLanguageFile().getString("save");
        public static String cancel=getInternalLanguageFile().getString("cancel");
        public static String buffer_size=getInternalLanguageFile().getString("buffer_size");
        public static String about=getInternalLanguageFile().getString("about");
        public static String ok=getInternalLanguageFile().getString("ok");
        public static String restart_app=getInternalLanguageFile().getString("restart_app");
        public static String change_language=getInternalLanguageFile().getString("change_language");
        public static String app_language=getInternalLanguageFile().getString("app_language");
        public static String select_language=getInternalLanguageFile().getString("select_language");
        public static String change=getInternalLanguageFile().getString("change");
        public static String import_language=getInternalLanguageFile().getString("import_language");
        public static String import_successful=getInternalLanguageFile().getString("import_successful");
        public static String get(String text, String insert){
            return text.replace("...", insert);
        }
        public static String get(String text, String[] inserts){
            try{
            StringTokenizer tokens=new StringTokenizer(text,"...");
            String texto="";
            if(!text.trim().startsWith("...")){
                texto=tokens.nextToken();
            }
                for (String insert : inserts) {
                    texto = texto + insert;
                    if(tokens.hasMoreTokens()){
                        texto=texto+tokens.nextToken();
                    }
                }
            return texto;
            }
            catch(NoSuchElementException ex){
                System.out.println("You're out of text");
                return "Error in language file";
            }
        }
        
        
    }
    
}
