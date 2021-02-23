/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Mike
 *
 */
public class StaticTest {

    /*
    110/02/02
    測試全國電子公文欄界接，在寫ODBBS.DownloadFile2時研究為什麼使用
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    而不是
    Connection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    得到結論是，物件如果存取static method，會異動到static variables，之後new的物件就會取得相同的值
    java是允許物件存取static method，但應該還是由類別來存取
    
    class(類別) == 設計圖
    依照class產生的Instance(實例、東西)叫Object(物件)
     */
 /*
            System.out.println("------------------StaticTest-------------------------");
            System.out.println(StaticTest.getA());
            System.out.println("------------------st1-------------------------");
            StaticTest st1 = new StaticTest();
            System.out.println(st1.getA());
            st1.setA("asd");
            System.out.println(st1.getA());
            System.out.println("------------------StaticTest-------------------------");
            System.out.println(StaticTest.getA());
            StaticTest.setA("5");
            System.out.println(StaticTest.getA());
            System.out.println("------------------st2-------------------------");
            StaticTest st2 = new StaticTest();
            System.out.println(st2.getA());
     */
    private static String A = "0";

    public static void setA(String a) {
        A = a;
    }

    public static String getA() {
        return A;
    }

}
