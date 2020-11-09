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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
            CloseableHttpClient httpclient = null;
            httpclient = HttpClients.createDefault();
            String url = "https://tcgbusfs.blob.core.windows.net/blobtcmsv/TCMSV_allavailable.gz";
            URI DataUrl = new URI(url);
            //建立HttpPost物件
            HttpGet httpGet = new HttpGet(DataUrl);
            //建立參數物件
            //執行Post動作並取得回傳資料
            response2 = httpclient.execute(httpGet);

            File fFileUrl = new File(FileUrl);
            if (!fFileUrl.exists()) {
                fFileUrl.mkdirs();
            }
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

    /**
     * post方式提交表單（模擬使用者登入請求）
     */
    public void postForm() {
        // 建立預設的httpClient例項.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 建立httppost    
        HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");
        // 建立引數佇列    
        List formparams = new ArrayList();
        formparams.add(new BasicNameValuePair("username", "admin"));
        formparams.add(new BasicNameValuePair("password", "123456"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("————————————–");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("————————————–");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 關閉連線,釋放資源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
