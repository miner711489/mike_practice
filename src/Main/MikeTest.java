/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
