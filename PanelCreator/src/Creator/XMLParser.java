package Creator;

import java.io.File;
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
    
    private Marshaller marshStore;
    private Marshaller marshWidget;
    
    private Unmarshaller unmarshStore;
    private Unmarshaller unmarshWidget;

    public XMLParser() {
        try {
            contextStore = JAXBContext.newInstance(Store.class);
            marshStore = contextStore.createMarshaller();
            marshStore.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshStore = contextStore.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: store marsh or unmarsher\n" + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            contextWidget = JAXBContext.newInstance(DefaultWidgets.class);
            marshWidget = contextWidget.createMarshaller();
            marshWidget.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshWidget = contextWidget.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: widget marsh or unmarsher\n" + e.getMessage());
            e.printStackTrace();
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

}
