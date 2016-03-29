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
    
    private Marshaller marshStore;
    private Marshaller marshWidget;
    private Marshaller marshIoNames;
    
    private Unmarshaller unmarshStore;
    private Unmarshaller unmarshWidget;
    private Unmarshaller unmarshIoNames;

    public XMLParser() {
        
        // Store
        
        try {
            contextStore = JAXBContext.newInstance(Store.class);
            marshStore = contextStore.createMarshaller();
            marshStore.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshStore = contextStore.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: store marsh or unmarsher\n" + e.getMessage());
            //e.printStackTrace();
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

}
