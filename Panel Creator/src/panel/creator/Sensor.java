package panel.creator;

/**
 *
 * @author EricGummerson
 */
public class Sensor implements java.io.Serializable {
    
    private static final long serialVersionUID = 561L;
    private String key;
    private int meter;
    private int slave;
    private int register;
    private boolean used;

    public Sensor(){
        this.meter = this.slave = this.register = -1;
        this.key = "";
        this.used = false;
    }
    
    
    public Sensor(String key, int meter, int slave, int register, boolean used) {
        this.meter = meter;
        this.slave = slave;
        this.register = register;
        this.used = used;
        this.key = key;
    }
    
    
    public void removeKey(){
        this.meter = this.slave = this.register = -1;
        this.key = "";
        this.used = false;
    }
    
    public void updateKey(int meter, int slave, int register, boolean used){
        this.meter = meter;
        this.slave = slave;
        this.register = register;
        this.used = used;        
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
    
    
    
    
    
    
    
}
