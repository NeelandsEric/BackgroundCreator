package panel.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author EricGummerson
 */
public class Widget {
    
    String widgetName;
    Map<String, Object> variables;

    public Widget(String variableName) {
        this.widgetName = variableName;
        variables = new HashMap<>();
    }
    
    public Widget(String variableName, ArrayList<String> vars) {
        this.widgetName = variableName;
        variables = new HashMap<>();
        for(String s: vars){
            this.variables.put(s, new Object());
        }
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String variableName) {
        this.widgetName = variableName;
    }
    
    public Set<String> getKeys(){
        return this.variables.keySet();
    }
   
    

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
    
    public Object getValue(String key){
        if(variables.containsKey(key)){
            return variables.get(key);
        }else {
            return null;
        }
    }
    
    
    public void setVariable(String key, Object value){
        Object replaced = variables.put(key, value);
        if(replaced != null){
            System.out.println(key + " replaced " + replaced + " with " + value);
        }        
    }
    
    
    public void addVariables(ArrayList<String> vars){
        
        for(String s: vars){
            this.variables.put(s, new Object());
        }
    }
    
    
    @Override
    public String toString(){
        
        String s = "";
        s += "Widget " + widgetName + " has " + variables.size() + " mappings.\n";
        for(String key: variables.keySet()){
            s += key + ": " + variables.get(key) + "\n";
        }
        return s;
    }
    
}
