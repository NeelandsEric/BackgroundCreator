package Creator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public List<String> variables;
    public String fullWidgetText;

    public WidgetCode() {
        this.widgetName = "Empty";
        this.variables = new ArrayList<>();
        this.fullWidgetText = "Empty";
    }

    public WidgetCode(String variableName) {
        this.widgetName = variableName;
        variables = new ArrayList<>();
        this.fullWidgetText = "";
    }

    public WidgetCode(String variableName, ArrayList<String> vars, String fullWidgetText) {
        this.widgetName = variableName;
        variables = vars;
        this.fullWidgetText = fullWidgetText;

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

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public void addVariables(ArrayList<String> vars) {

        for (String s : vars) {
            this.variables.add(s);
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

    
    @Override
    public String toString() {

        String s = this.widgetName + "\n";
        /*
         s += "Widget " + widgetName + " has " + variables.size() + " mappings.\n";
         for (String key : variables.keySet()) {
         s += key + ": " + variables.get(key) + " | ";
         }
         */
        return s;
    }

}
