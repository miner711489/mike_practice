/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.regex.Pattern;

/**
 *
 * @author Mike
 */
public class ValidEmailFormat {

    public String Test() throws Exception {
        String rtnValue = "";
        try {
            String VaildValue = "";
            VaildValue = "123456789";
            VaildValue = "mike@dsic.com.tw";//true
            VaildValue = "mike.lin@dsic.com.tw";//true
            VaildValue = "@dsic.com.tw";//false
            VaildValue = "I@dsic.com.tw";//true
            VaildValue = "I@dsic.com";//true
            VaildValue = "I@dsic";//false
            VaildValue = "I@dsic..com";//false
            VaildValue = "123.1023.123@dsiccom";//false
            VaildValue = "123.1023.123@dsic.com.";//false
            VaildValue = "123.1023.123@dsic.com.";//false
            VaildValue = "123.1023.123@dsic.com.1";//false
            VaildValue = "123.1023.123@dsic.com.w";//true
            VaildValue = "123.1023.123@mail.dsic.com.tw";//true
            VaildValue = "123.1023.123@mail.dsic-com.tw";//true

            String RegExp = "";
            RegExp = "[0-9]*";//0~9，至少出現1次.
            RegExp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z]+$";//要驗證的
            /*
            ＊參考網站1:https://openhome.cc/Gossip/JavaGossip-V1/RegularExpression.htm
            ＊參考網站2:https://ithelp.ithome.com.tw/articles/10209843
            ＊這邊是自已寫的說明
            ＊這東西叫做Regular expression
            ＊\w表示 [a-zA-Z_0-9] 數字或是英文字
            在java裡「\」要用「\\」來表示
            所以變成\\w
            其他符號在網站裡有寫
            ＊{n}表示出現次數
            
             */

            Pattern pattern = Pattern.compile(RegExp);
            pattern.matcher(VaildValue).matches();
            if (pattern.matcher(VaildValue).matches()) {
                System.out.println(VaildValue + " 通過");
            } else {
                System.out.println(VaildValue + " 不通過");
            }

        } catch (Exception e) {
            throw e;
        }
        return rtnValue;
    }

}
