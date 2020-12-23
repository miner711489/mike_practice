package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            //ValidEmailFormat Valid = new ValidEmailFormat();
            //System.out.println(Valid.Test());

            ImportXml importxml = new ImportXml();
            System.out.println(importxml.Import());

        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        }

    }

    public static int maximumWealth(int[][] accounts) {

        int[][] a = {{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}};
        accounts = a;
        List<Integer> lst = new ArrayList();
        for (int i = 0; i < accounts.length; i++) {
            int sum_temp = 0;
            for (int j = 0; j < accounts[i].length; j++) {
                sum_temp += accounts[i][j];
            }
            lst.add(sum_temp);
        }
        Collections.sort(lst, Collections.reverseOrder());

        return lst.get(0);
    }

}
