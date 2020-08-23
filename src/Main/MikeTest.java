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
            //Mike Add Message
            ValidEmailFormat Valid = new ValidEmailFormat();
            System.out.println(Valid.Test());

            System.out.println(Valid.Test() + "，123");
        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        }

    }

}
