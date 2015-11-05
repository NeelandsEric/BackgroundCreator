package panel.creator;

/**
 *
 * @author EricGummerson
 */
public class Slave {

    public int type;
    public String name;
    public String[] register;
    
    public Slave(){
        this.type = 1;
        this.name = "Empty";
        this.register = new String[3];        
    }
    
    public Slave(int type, String name) {
        this.type = type;
        this.name = name;
        // 3 cycle 
        if (type == 1) {
            register = new String[3];            
        } else { // 1 cycle
            register = new String[1];            
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if (type == 1) {
            register = new String[3];            
        } else { // 1 cycle
            register = new String[1];            
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRegister() {
        return register;
    }

    public String getRegisterIndex(int index) {
        if (register[index] != null || !register[index].equals("")){
            return register[index];
        } else {
            return "Empty";
        }
    }

    public void setRegisterIndex(String reg, int index) {
        this.register[index] = reg;
    }
   
}
