/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Default IO Names is a class to load and save default IO variables to a users
 * home directory. This is needed to contain the IoNames class by itself
 *
 * @author EricGummerson
 */
@XmlRootElement(name = "DefaultIoNames")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultIoNames implements java.io.Serializable {

    public static final long serialVersionUID = 41171L;
    public IoNames ioNames;

    /**
     * Default constructor
     */
    public DefaultIoNames() {
    }

    /**
     * Constructor with IoName parameter
     *
     * @param ioNames
     */
    public DefaultIoNames(IoNames ioNames) {
        this.ioNames = ioNames;
    }

    /**
     * Get the IoNames
     *
     * @return IoNames
     */
    public IoNames getIoNames() {
        return ioNames;
    }

    /**
     * Set the IoNames
     *
     * @param ioNames IoNames
     */
    public void setIoNames(IoNames ioNames) {
        this.ioNames = ioNames;
    }

}
