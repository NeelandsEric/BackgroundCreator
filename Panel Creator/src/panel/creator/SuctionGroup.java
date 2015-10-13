/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.util.ArrayList;

/**
 *
 * @author EricGummerson
 */
public class SuctionGroup {
    
    public String   name;
    public int      numCompressors;
    public int      numSystems;
    public ArrayList<String> systemNames;
    public ArrayList<String> compressorNames;

    
    public SuctionGroup(String name) {
        this.name = name;
        this.numCompressors = 1;
        this.numSystems = 1;
        this.systemNames = new ArrayList<>();
        this.systemNames.add("System 1");
        this.compressorNames = new ArrayList<>();
        this.compressorNames.add("Comp 1");
    }
    
    

    
    //===================================================
    //                  Getters and Setters
    //===================================================
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCompressors() {
        return numCompressors;
    }

    public void setNumCompressors(int numCompressors) {
        this.numCompressors = numCompressors;
    }

    public int getNumSystems() {
        return numSystems;
    }

    public void setNumSystems(int numSystems) {
        this.numSystems = numSystems;
    }   

    public ArrayList<String> getSystemNames() {
        return systemNames;
    }
    
    public String getSystemNameIndex(int index){
        if(systemNames.size() > index){
            return systemNames.get(index);
        }else {
            //System.out.println("System index " + index + " does not exist");
            return "System " + (index+1);
        }
    }

    public void setSystemNames(ArrayList<String> systemNames) {
        this.systemNames = systemNames;
    }
    
    public void addSystemNames(int num){
        int size = this.systemNames.size();
        int toAdd = num - size;        
        
        for(int i = 0; i < toAdd; i++){
            //System.out.println("Adding System " + (size + i+1));
            this.systemNames.add("System " + (size + i+1));
            this.numSystems++;
        }        
        
    }    
                
    public void replaceSystemName(String name, int index){
        this.systemNames.set(index, name);
    }
    
    
    public ArrayList<String> getCompressorNames() {
        return compressorNames;
    }
    
    public String getCompressorNameIndex(int index){
        if(compressorNames.size() > index){
            return compressorNames.get(index);
        }else {
            //System.out.println("System index " + index + " does not exist");
            return "Comp " + (index+1);
        }
    }

    public void setCompressorNames(ArrayList<String> compressorNames) {
        this.compressorNames = compressorNames;
    }
    
    public void addCompressorNames(int num){
        int size = this.compressorNames.size();
        int toAdd = num - size;        
        
        for(int i = 0; i < toAdd; i++){
            //System.out.println("Adding System " + (size + i+1));
            this.compressorNames.add("Comp " + (size + i+1));
            this.numCompressors++;
        }        
        
    }
    
                
    public void replaceCompressorName(String name, int index){
        this.compressorNames.set(index, name);
    }
    
    
    
    //===================================================
    //                      Functions
    //===================================================
    
    
    
}
