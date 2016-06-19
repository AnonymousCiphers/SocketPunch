/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch;

import com.olmectron.material.components.MaterialStandardListItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Edgar
 */
public abstract class XMLReader {
   private MaterialStandardListItem<InstallableFile> listItem;
    
    private InstallableFile getInstallableFile(){
        return listItem.getItem();
    }
    public XMLReader(){
       initSaxReader();
    }
    public abstract void onElementFound(MaterialStandardListItem<InstallableFile> item);
    
    public void findTitleId(MaterialStandardListItem<InstallableFile> listItem){
        this.listItem=listItem;
        try {
            File xmlFile=new File("3dstdb.xml");
            InputStream inputStream=new FileInputStream(xmlFile);
            Reader reader=new  InputStreamReader(inputStream,"UTF-8");
            InputSource inputSource=new InputSource(reader);
            inputSource.setEncoding("UTF-8");
            parser.parse(inputSource,handler);
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    private SAXParser parser;
    private DefaultHandler handler;
    
    private void initSaxReader(){
        try {
            SAXParserFactory factory=SAXParserFactory.newInstance();
            parser=factory.newSAXParser();
            
            handler=new DefaultHandler(){
                boolean boolName=false;
                boolean boolRegion=false;
                boolean boolSerial=false;
                boolean boolLanguages=false;
                boolean boolPublisher=false;
                private String lastName;
                private String lastRegion;
                private String lastSerial;
                private String lastLanguages;
                private String lastPublisher;
                
                public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException{
                    if(qName.equalsIgnoreCase("GAME")){
                        lastName=null; lastRegion=null; lastSerial=null; lastLanguages=null; lastPublisher=null;
                        
                        lastName=attributes.getValue("name");
                        
                        //lastName=attributes.getValue("name");
                    }
                    //System.out.println("Start element: "+qName);
                    /*if(qName.equalsIgnoreCase("NAME")){
                        boolName=true;
                    }*/
                    
                    
                    if(qName.equalsIgnoreCase("REGION")){
                        boolRegion=true;
                    }
                    if(qName.equalsIgnoreCase("ID")){
                        boolSerial=true;
                    }
                    if(qName.equalsIgnoreCase("LANGUAGES")){
                        boolLanguages=true;
                    }
                    if(qName.equalsIgnoreCase("PUBLISHER")){
                        boolPublisher=true;
                    }
                }
                public void endElement(String uri, String localName, String qName) throws SAXException{
                    if(qName.equalsIgnoreCase("GAME")){
                        if(lastSerial!=null && getInstallableFile().getSerial()!=null && getInstallableFile().getSerial().endsWith(lastSerial)){
                            
                            getInstallableFile().setName(lastName);
                            getInstallableFile().setSerial(lastSerial);
                            getInstallableFile().setRegion(lastRegion);
                            getInstallableFile().setPublisher(lastPublisher);
                            getInstallableFile().setLanguages(lastLanguages);
                            onElementFound(listItem);
                        }
                    }
                    //System.out.println("End element: "+qName);
                }
                public void characters(char[] ch, int start, int length) throws SAXException{
                    //String name, region, serial, language, titleId;
                    /*if(boolName){
                        lastName=new String(ch,start,length);
                        //System.out.println("Name: "+name);
                        boolName=false;
                    }*/
                    if(boolRegion){
                        lastRegion=new String(ch,start,length);
                         //System.out.println("Region: "+region);
                        boolRegion=false;
                    }
                    if(boolSerial){
                        lastSerial=new String(ch,start,length);
                         //System.out.println("Serial: "+new String(ch,start,length));
                        boolSerial=false;
                    }
                    if(boolLanguages){
                        lastLanguages=new String(ch,start,length);
                         //System.out.println("Languages: "+new String(ch,start,length));
                        boolLanguages=false;
                    }
                    if(boolPublisher){
                        lastPublisher=new String(ch,start,length);
                         //System.out.println("Title ID: "+new String(ch,start,length));
                        boolPublisher=false;
                    }
                }
            
            
            };
            
           
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    private void initDomReader(){
        
        try {
            File xmlFile = new File("3dsreleases.xml");
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList releasesList = document.getElementsByTagName("release");
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
