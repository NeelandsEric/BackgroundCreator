package Creator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Sensor implements java.io.Serializable {
    
    private static final long serialVersionUID = 561L;
    private String key;
    private boolean powerScout;
    private int meter;
    private int slave;
    private int register;
    private boolean used;

    public Sensor(){
        this.meter = this.slave = this.register = -1;
        this.key = "";
        this.used = false;
        this.powerScout = false;
    }

    @Override
    public String toString() {
        return "Sensor{" + "key=" + key + ", meter=" + meter + ", slave=" 
                + slave + ", register=" + register + ", used=" + used + ", powerScout="
                + powerScout + '}';
    }
    
    
    
    public Sensor(String key, int meter, int slave, int register, boolean used, boolean powerScout) {
        this.meter = meter;
        this.slave = slave;
        this.register = register;
        this.used = used;
        this.key = key;
        this.powerScout = powerScout;
    }
    
    
    public void removeKey(){
        this.meter = this.slave = this.register = -1;
        this.key = "";
        this.used = false;
        this.powerScout = false;
    }
    
    public void updateKey(String key, int meter, int slave, int register, boolean used, boolean powerScout){
        this.key = key;
        this.meter = meter;
        this.slave = slave;
        this.register = register;
        this.used = used;
        this.powerScout = powerScout;
    }
    
    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMeter() {
        return meter;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public int getSlave() {
        return slave;
    }

    public void setSlave(int slave) {
        this.slave = slave;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isPowerScout() {
        return powerScout;
    }

    public void setPowerScout(boolean powerScout) {
        this.powerScout = powerScout;
    }
    
    
    
    
    
    
    
}
