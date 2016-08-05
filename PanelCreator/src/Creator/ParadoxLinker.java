/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author EricGummerson
 */
public class ParadoxLinker {
    
    private Map<String, String> knownNameLinks;
    private ControlSettings cs;
    private Map<String, List> nameMappings;
    private String fileNameToLoad;

    public ParadoxLinker(ControlSettings cs, Map<String, List> nameMappings) {
        this.cs = cs;
        this.nameMappings = nameMappings;
        this.fileNameToLoad = "/Creator/textFiles/Paradox-KeyLinks.xlsx";
        this.loadKnownNames();
    }

    public ParadoxLinker(ControlSettings cs, Map<String, List> nameMappings, String fileName) {
        this.cs = cs;
        this.nameMappings = nameMappings;
        this.fileNameToLoad = fileName;
        this.loadKnownNames();
    }

    public Map<String, String> getKnownNameLinks() {
        return knownNameLinks;
    }

    public void setKnownNameLinks(Map<String, String> knownNameLinks) {
        this.knownNameLinks = knownNameLinks;
    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        this.cs = cs;
    }

    public Map<String, List> getNameMappings() {
        return nameMappings;
    }

    public void setNameMappings(Map<String, List> nameMappings) {
        this.nameMappings = nameMappings;
    }
    
    public List getNameMappingsFor(String key){
        if(nameMappings.containsKey(key)){
            return nameMappings.get(key);
        }else {
            return null;
        }
    }
   

    public String getFileNameToLoad() {
        return fileNameToLoad;
    }

    public void setFileNameToLoad(String fileNameToLoad) {
        this.fileNameToLoad = fileNameToLoad;
    }
    
    
    
    
    public String getParadoxKeyForIo(String ioName){
        if(knownNameLinks.containsKey(ioName)){
            return knownNameLinks.get(ioName);
        }else {
            return "";
        }
    }
    
    
    
    
    
    
    
    /**
     * Reads a file and loads the links between io names and paradox 
     */    
    private void loadKnownNames(){
        
        // Load file 
        knownNameLinks = new TreeMap<>();                
       
        InputStream loc = this.getClass().getResourceAsStream(fileNameToLoad);
        
        
    }
    
    
    
}
