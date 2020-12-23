/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 * 取得小寫
 *
 * @author Mike
 */
public class LowerCase {

    public String getLowerCase(String InputString) {
        InputString = InputString.toLowerCase();
        InputString = InputString.replace(",", "\n");
        return InputString;
    }

}
