/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpenData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Mike
 */
public class getData {

    public String getOpenData() throws IOException, Exception {
        String rtnmsg = "";
        CloseableHttpResponse response2 = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String url = "https://tcgbusfs.blob.core.windows.net/blobtcmsv/TCMSV_allavailable.gz";
            String Url2 = "https://tcgbusfs.blob.core.windows.net/blobyoubike/YouBikeTP.json";
            url = Url2;
            URI DataUrl = new URI(url);
            //建立HttpGet物件
            HttpGet httpGet = new HttpGet(DataUrl);
            //取得回傳資料
            response2 = httpclient.execute(httpGet);

            HttpEntity entity2 = response2.getEntity();//取得回傳的資料
            response2.getStatusLine().getStatusCode();
            response2.getAllHeaders();
            if (entity2 != null) {
                if (response2.getStatusLine().getStatusCode() == 200) {
                    rtnmsg = EntityUtils.toString(response2.getEntity());
                } else {
                    return "Http StatusCode not 200";
                }
            }
            EntityUtils.consume(entity2);//釋放資源
        } catch (Exception e) {
            throw e;
        } finally {
            response2.close();
        }
        return rtnmsg;
    }

    public String getOpenDataFile() throws IOException, Exception {
        String rtnmsg = "";
        CloseableHttpResponse response2 = null;
        FileOutputStream fos = null;
        InputStream is = null;
        String FileUrl = "D:\\Project\\__MikeTest\\test";
        String FileName = "TCMSV_allavailable.gz";
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String url = "https://tcgbusfs.blob.core.windows.net/blobtcmsv/TCMSV_allavailable.gz";
            URI DataUrl = new URI(url);
            //建立HttpPost物件
            HttpGet httpGet = new HttpGet(DataUrl);
            //建立參數物件
            //執行Post動作並取得回傳資料
            response2 = httpclient.execute(httpGet);

            HttpEntity entity2 = response2.getEntity();//取得回傳的資料
            response2.getStatusLine().getStatusCode();
            response2.getAllHeaders();
            if (entity2 != null) {
                is = entity2.getContent();
                fos = new FileOutputStream(new File(FileUrl, FileName));
                int inbyte;
                while ((inbyte = is.read()) != -1) {
                    fos.write(inbyte);
                }
            }
            EntityUtils.consume(entity2);//釋放資源
            is.close();
            fos.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
            response2.close();
        }
        return rtnmsg;
    }
}
