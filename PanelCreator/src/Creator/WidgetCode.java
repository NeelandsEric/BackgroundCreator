package Creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetCode implements java.io.Serializable {

    public static final long serialVersionUID = 43217L;
    public String widgetName;
    public Map<String, String> variables;
    public String fullWidgetText;
    public String filePath;

    public WidgetCode() {
        this.widgetName = "Empty";
        this.variables = new HashMap<>();
        this.fullWidgetText = "Empty";
        this.filePath = "Empty";
    }

    public WidgetCode(String variableName) {
        this.widgetName = variableName;
        variables = new HashMap<>();
        this.fullWidgetText = "";
        this.filePath = "";
    }

    public WidgetCode(String variableName, ArrayList<String> vars, String fullWidgetText, String filePath) {
        this.widgetName = variableName;
        variables = new HashMap<>();
        for (String s : vars) {
            this.variables.put(s, "");
        }
        this.fullWidgetText = fullWidgetText;
        this.filePath = filePath;

        if (this.widgetName.endsWith(".txt")) {
            this.widgetName = this.widgetName.replace(".txt", "");
        }
    }

    public String getWidgetName() {

        return widgetName;
    }

    public void setWidgetName(String variableName) {
        this.widgetName = variableName;
    }

    public Set<String> getKeys() {
        return this.variables.keySet();
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public Object getValue(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        } else {
            return null;
        }
    }

    public void setVariable(String key, String value) {
        Object replaced = variables.put(key, value);
        if (replaced != null) {
            System.out.println(key + " replaced " + replaced + " with " + value);
        }
    }

    public void addVariables(ArrayList<String> vars) {

        for (String s : vars) {
            this.variables.put(s, "");
        }
    }

    public String getFullWidgetText() {
        return fullWidgetText;
    }

    public void setFullWidgetText(String fullWidgetText) {
        this.fullWidgetText = fullWidgetText;
    }

    public void addFullWidgetText(String text) {
        this.fullWidgetText += text;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {

        String s = this.widgetName + ": " + variables.size() + " mappings";
        /*
         s += "Widget " + widgetName + " has " + variables.size() + " mappings.\n";
         for (String key : variables.keySet()) {
         s += key + ": " + variables.get(key) + " | ";
         }
         */
        return s;
    }

}
