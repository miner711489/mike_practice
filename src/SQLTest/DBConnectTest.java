/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLTest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Mike
 */
public class DBConnectTest {

    public void getDBConnectMessage() {

        //<editor-fold defaultstate="collapsed" desc="comment"> 
        System.out.println("-----This is a DBTest-----");
        try {
            //<editor-fold defaultstate="collapsed" desc="DB連線範例1">
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=TCBKOP;user=TCBKOP;password=TCBKOP;");

            String sql = ""; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            PreparedStatement pst = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY);
            pst.setString(1, "1080000001");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("")); //將結果用while印出
            }
            //</editor-fold>
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception ee) {
            System.out.print(ee.getMessage());
        } finally {
        }
    }
}
