package Creator;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author EricGummerson
 */
public class XMLParser {

    private JAXBContext contextStore;
    private JAXBContext contextWidget;
    private JAXBContext contextIoNames;
    private JAXBContext contextParadoxKeyMap;
    private JAXBContext contextGVLinks;
    
    private Marshaller marshStore;
    private Marshaller marshWidget;
    private Marshaller marshIoNames;
    private Marshaller marshParadoxKeyMap;
    private Marshaller marshGVLinks;
    
    private Unmarshaller unmarshStore;
    private Unmarshaller unmarshWidget;
    private Unmarshaller unmarshIoNames;
    private Unmarshaller unmarshParadoxKeyMap;
    private Unmarshaller unmarshGVLinks;

    public XMLParser() {
        
        // Store
        
        try {
            contextStore = JAXBContext.newInstance(Store.class);
            marshStore = contextStore.createMarshaller();
            marshStore.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshStore = contextStore.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: store marsh or unmarsher\n" + e.getMessage());
            e.printStackTrace();
        }
        
        // Widgets
        
        try {
            contextWidget = JAXBContext.newInstance(DefaultWidgets.class);
            marshWidget = contextWidget.createMarshaller();
            marshWidget.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshWidget = contextWidget.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: widget marsh or unmarsher\n" + e.getMessage());
            //e.printStackTrace();
        }
        
        // IO Names
        
        try {
            contextIoNames = JAXBContext.newInstance(DefaultIoNames.class);
            marshIoNames = contextIoNames.createMarshaller();
            marshIoNames.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshIoNames = contextIoNames.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: io names marsh or unmarsher\n" + e.getMessage());
            //e.printStackTrace();
        }
        
        // ParadoxKeyMap
        
        try {
            contextParadoxKeyMap = JAXBContext.newInstance(ParadoxKeyMap.class);
            marshParadoxKeyMap = contextParadoxKeyMap.createMarshaller();
            marshParadoxKeyMap.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshParadoxKeyMap = contextParadoxKeyMap.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: ParadoxKeyMap marsh or unmarsher\n" + e.getMessage());
            //e.printStackTrace();
        }
        
        // ParadoxKeyMap
        
        try {
            contextGVLinks = JAXBContext.newInstance(GenericVariableLinks.class);
            marshGVLinks = contextGVLinks.createMarshaller();
            marshGVLinks.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshGVLinks = contextGVLinks.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: GVLinks marsh or unmarsher\n" + e.getMessage());
            //e.printStackTrace();
        }

    }
    
     
    public boolean writeOut(Store store, String fileOutPath) {

        if (marshStore != null) {
            

            try {
                marshStore.marshal(store, new File(fileOutPath));

                return true;

            } catch (JAXBException e) {
                System.out.println("XMLParser write out error\n" + e.getMessage());
                e.printStackTrace();
                return false;

            }
        } else {
            System.out.println("Marshaller has not been correctly made.");
            return false;
        }

    }

    public Store readFile(String filepath) {

        if (unmarshStore != null) {
            try {

                File f = new File(filepath);
                Store store = (Store) unmarshStore.unmarshal(f);

                return store;

            } catch (JAXBException ex) {
                System.out.println("XMLParser read file error\n" + ex.getMessage());
                System.out.println("Filepath: " + filepath);
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }
    
    public ParadoxKeyMap readParadoxKeyMapFile(String filepath) {

        if (unmarshParadoxKeyMap != null) {
            try {

                File f = new File(filepath);
                ParadoxKeyMap paradoxMappings = (ParadoxKeyMap) unmarshParadoxKeyMap.unmarshal(f);

                return paradoxMappings;

            } catch (JAXBException ex) {
                System.out.println("XMLParser for paradox keymap read file error\n" + ex.getMessage());
                System.out.println("Filepath: " + filepath);
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }catch (Exception e){
                System.out.println("Other exception caught trying to read paradox keymap file");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

    }
       
    
    public boolean writeOutDefaultWidgets(DefaultWidgets dw, String fileOutPath){
        
        if (marshWidget != null) {
            
            try {
                marshWidget.marshal(dw, new File(fileOutPath));

                return true;

            } catch (JAXBException e) {
                System.out.println("XMLParser write out error\n" + e.getMessage());
                e.printStackTrace();
                return false;

            }
        } else {
            System.out.println("Marshaller has not been correctly made.");
            return false;
        }
        
        
    }
    
    public DefaultWidgets readWidgetsFile(InputStream loc){
        
        if (unmarshWidget != null) {
            try {
                
                DefaultWidgets dw = (DefaultWidgets) unmarshWidget.unmarshal(loc);

                return dw;

            } catch (JAXBException ex) {
                System.out.println("XMLParser read file error with Default Widgets from the jar\n" + ex.getMessage());
                System.out.println("Filepath: " + loc.toString());
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
    
    public DefaultWidgets readWidgetsFile(String filepath){
        
        if (unmarshWidget != null) {
            try {

                File f = new File(filepath);
                DefaultWidgets dw = (DefaultWidgets) unmarshWidget.unmarshal(f);

                return dw;

            } catch (JAXBException ex) {
                System.out.println("XMLParser read file error with Default Widgets\n" + ex.getMessage());
                System.out.println("Filepath: " + filepath);
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
    
    public boolean writeOutDefaultIoNames(DefaultIoNames names, String fileOutPath){
        
        if (marshIoNames != null) {
            
            try {
                marshIoNames.marshal(names, new File(fileOutPath));

                return true;

            } catch (JAXBException e) {
                System.out.println("XMLParser write out error\n" + e.getMessage());
                e.printStackTrace();
                return false;

            }
        } else {
            System.out.println("Marshaller has not been correctly made.");
            return false;
        }
        
        
    }
    
    public IoNames readIoNamesFile(String filepath){
        
        if (unmarshIoNames != null) {
            try {

                File f = new File(filepath);
                DefaultIoNames names = (DefaultIoNames) unmarshIoNames.unmarshal(f);

                return names.getIoNames();

            } catch (JAXBException ex) {
                System.out.println("XMLParser read file error with Default Widgets\n" + ex.getMessage());
                System.out.println("Filepath: " + filepath);
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
    
    public boolean writeOutGVLinks(GenericVariableLinks gvl, String fileOutPath){
        
        if (marshIoNames != null) {
            
            try {
                marshGVLinks.marshal(gvl, new File(fileOutPath));

                return true;

            } catch (JAXBException e) {
                System.out.println("XMLParser write out error\n" + e.getMessage());
                e.printStackTrace();
                return false;

            }
        } else {
            System.out.println("Marshaller has not been correctly made.");
            return false;
        }
        
        
    }
    
    
    public GenericVariableLinks readGVLinksFile(String filepath) {

        if (unmarshGVLinks != null) {
            try {

                File f = new File(filepath);
                GenericVariableLinks gvLinks = (GenericVariableLinks) unmarshGVLinks.unmarshal(f);

                return gvLinks;

            } catch (JAXBException ex) {
                System.out.println("XMLParser for GVLinks keymap read file error\n" + ex.getMessage());
                System.out.println("Filepath: " + filepath);
                ex.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                System.out.println("File not found\n" + e.getMessage());
                return null;
            }catch (Exception e){
                System.out.println("Other exception caught trying to read paradox keymap file");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

    }

}
