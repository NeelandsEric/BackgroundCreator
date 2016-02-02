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

    private JAXBContext context;
    private Marshaller marsh;
    private Unmarshaller unmarsh;

    public XMLParser() {
        try {
            context = JAXBContext.newInstance(Store.class);
            marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarsh = context.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("XMLParser constructor error: marsh or unmarsher\n" + e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean writeOut(Store store, String fileOutPath) {

        if (marsh != null) {

            System.out.println(store.ws);

            try {
                marsh.marshal(store, new File(fileOutPath));

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

        if (unmarsh != null) {
            try {

                File f = new File(filepath);
                Store store = (Store) unmarsh.unmarshal(f);

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
