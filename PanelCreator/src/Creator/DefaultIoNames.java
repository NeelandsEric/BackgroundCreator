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
 *
 * @author EricGummerson
 */
@XmlRootElement(name = "DefaultIoNames")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultIoNames implements java.io.Serializable{


    public static final long serialVersionUID = 41171L; 
    public IoNames ioNames;

    public DefaultIoNames() {
    }

    public DefaultIoNames(IoNames ioNames) {
        this.ioNames = ioNames;
    }

    public IoNames getIoNames() {
        return ioNames;
    }

    public void setIoNames(IoNames ioNames) {
        this.ioNames = ioNames;
    }
    
    
    

        
    
}
