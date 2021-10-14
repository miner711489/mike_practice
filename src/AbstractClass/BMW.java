package AbstractClass;

/**
 *
 * @author Mike
 */
public class BMW extends Car {
    
    public BMW() {
        super.setCountryName("德國");
    }

    @Override
    public void accelerate(int acc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void slowdown(int sw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
