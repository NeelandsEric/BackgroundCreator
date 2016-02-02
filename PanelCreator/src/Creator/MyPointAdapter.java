/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MyPointAdapter extends XmlAdapter<MyPoint, Point> {
    
    /**
     * Java => XML
     * @param val
     * @return 
     * @throws java.lang.Exception
     */    
    @Override
    public MyPoint marshal(Point val) throws Exception {
        return new MyPoint((int) val.getX(), (int) val.getY());
    }
    
    /**
     * XML => Java
     * @param val
     * @return 
     * @throws java.lang.Exception
     */
    @Override
    public Point unmarshal(MyPoint val) throws Exception {
        return new Point(val.getX(), val.getY());
    }
}
