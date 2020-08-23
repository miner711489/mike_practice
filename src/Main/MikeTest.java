package Main;

/**
 *
 * @author Mike
 */
public class MikeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ValidEmailFormat Valid = new ValidEmailFormat();
            System.out.println(Valid.Test());
        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        }

    }

}
