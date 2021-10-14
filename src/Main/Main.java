package Main;

import AbstractClass.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mike
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        try {
            //DBConnectTest DBT = new DBConnectTest();
            //DBT.DBConnectTestMain();

            CarMain carmain = new CarMain();
            carmain.main();

        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        } finally {
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

    public static String SimpleDateFormatTest(String Date) throws Exception {

        long unixTime = System.currentTimeMillis() / 1000L;
        System.out.println("unixTime:" + unixTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        String sndtime = "110/04/06 16:32:33";
//Date date = dateFormat.parse(sndtime);

        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        System.out.println("date.getTime 1:" + date.getTime());
        System.out.println("date 2:" + timeStamp);

        date.setTime(date.getTime() / 1000L);
        timeStamp = dateFormat.format(date);
        System.out.println("date.getTime 2:" + date.getTime());
        System.out.println("date 2:" + timeStamp);

        return "";
    }

    public static void MD5() throws Exception {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");

            String encode = "今天是7月12號，星期一。";

            System.out.println("encode:" + encode);
            System.out.println("encode.getBytes():" + encode.getBytes());
            //MD5加密
            byte[] cipherData = md5.digest(encode.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte cipher : cipherData) {
                String toHexStr = Integer.toHexString(cipher & 0xff);
                builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
            }
            String decode = builder.toString();
            System.out.println("decode:" + decode);

        } catch (Exception e) {
            throw e;
        }
    }

}
