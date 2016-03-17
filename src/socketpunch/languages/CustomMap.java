/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch.languages;

import java.util.HashMap;

public class CustomMap extends HashMap{
        public CustomMap(){
            super();
        }
         public void putString(String name, String value){
                        put(name, value);
                    }
                    public String getString(String name){
                        try{
                            return get(name).toString();
                        }
                        catch(NullPointerException ex){
                            return "Error reading language";
                        }
                        
                    }
    }
