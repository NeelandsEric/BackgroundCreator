/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author EricGummerson
 */
public class TableQueries {
    
    private String tableName;
    private Map<String, String> params;
    private Map<String, List<String>> vars;
    private String query;
    private int numVals;

    public TableQueries(String tableName) {
        this.tableName = tableName;
        this.params = new HashMap<>();
        this.vars = new HashMap<>();
        this.numVals = 0;
    }
    
    
    

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }    
   

    public int sizeParams() {
        return params.size();
    }

    public boolean isEmptyParams() {
        return params.isEmpty();
    }

    public boolean containsKeyParams(String key) {
        return params.containsKey(key);
    }

    public String putParams(String key, String value) {
        return params.put(key, value);
    }

    public String removeParams(String key) {
        return params.remove(key);
    }

    public void clearParams() {
        params.clear();
    }

    public Collection<String> valuesParams() {
        return params.values();
    }

    public Map<String, List<String>> getVars() {
        return vars;
    }

    public void setVars(Map<String, List<String>> vars) {
        this.vars = vars;
    }

    public int getNumVals() {
        return numVals;
    }

    public void setNumVals(int numVals) {
        this.numVals = numVals;
    }

    public boolean containsKeyVars(String key) {
        return vars.containsKey(key);
    }

    public List<String> getVars(String key) {
        return vars.get(key);
    }

    public List<String> putVars(String key, List<String> value) {
        return vars.put(key, value);
    }

    public List<String> removeVars(Object key) {
        return vars.remove(key);
    }

    public Collection<List<String>> valuesVars() {
        return vars.values();
    }

    public Set<Entry<String, List<String>>> entrySetVars() {
        return vars.entrySet();
    }

    public List<String> replaceVars(String key, List<String> value) {
        return vars.replace(key, value);
    }

    public Set<Entry<String, String>> entrySetParams() {
        return params.entrySet();
    }

    public String getParam(String key) {
        return params.get(key);
    }
    
    
    
    
        
}
