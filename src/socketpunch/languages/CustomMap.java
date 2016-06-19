/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch.languages;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomMap extends HashMap{
        public CustomMap(){
            super();
        }
       
                    
        public static String[] languages=new String[]{
            "English","Español","Português (Brasil)",
            "中国（简体）","Italiano","Русский","Українська","Deutsch","Français"};
        public ArrayList<String> getKeys(){
            ArrayList<String> keys=new ArrayList<String>();
            for(Object key: keySet().toArray()){
               keys.add(key.toString());
            }
            return keys;
        }
         public void putString(String name, String value){
                        put(name, value);
                    }
                    public String getString(String name){
                        try{
                            return get(name).toString();
                        }
                        catch(NullPointerException ex){
                            //System.out.println("Key: "+name+" not found");
                            return "Error reading language";
                        }
                        
                    }
                    public String getString(String name,String existant){
                        try{
                            return get(name).toString();
                        }
                        catch(NullPointerException ex){
                            return existant;
                        }
                        
                    }
    }
