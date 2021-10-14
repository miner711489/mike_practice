package ODBBS;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 界接全國電子公佈欄開發測試
 *
 * @author Mike
 */
public class ODBBS {

    final private String BBS_URL = "https://www.odbbs.gov.tw/odbbs/html/getEDoc.jsp?senddate=";
    private String senddate = "";

    public ODBBS() {
        this.senddate = "20210701";
    }

    public ODBBS(String senddate) {
        this.senddate = senddate;
    }

    public void ODBBSMain() throws IOException, Exception {

        try {
            String XmlString = doHttpGet();
            XmlString = doHttpGet2();
            parseXMLStr(XmlString);
        } catch (Exception e) {
            throw e;
        }
    }

    public void parseXMLStr(String XmlStr) throws Exception {
        String desDir = "C:\\Users\\Mike\\Desktop\\OBBBS";
        Document document = DocumentHelper.parseText(XmlStr);
        Element rootElm = document.getRootElement();
        System.out.println("method1:");
        //方法一，使用elements()，轉成List(感覺這方法比較多人用)
        List<Element> eles = rootElm.elements();
        for (Element ele : eles) {
            if (ele.getName().toLowerCase().equals("senddate") || ele.getName().toLowerCase().equals("sendno")) {
                continue;
            }
            System.out.println("--------------------------------------------------");
            System.out.println("SendUnit:" + (ele.selectSingleNode("SendUnit") == null ? "Null" : ele.selectSingleNode("SendUnit").getText()));
            System.out.println("SendUnitName:" + (ele.selectSingleNode("SendUnitName") == null ? "Null" : ele.selectSingleNode("SendUnitName").getText()));
            System.out.println("DocDate:" + (ele.selectSingleNode("DocDate") == null ? "Null" : ele.selectSingleNode("DocDate").getText()));
            System.out.println("SendDate:" + (ele.selectSingleNode("SendUnit") == null ? "Null" : ele.selectSingleNode("SendDate").getText()));
            System.out.println("DueDate:" + (ele.selectSingleNode("DueDate") == null ? "Null" : ele.selectSingleNode("DueDate").getText()));
            System.out.println("SendNo:" + (ele.selectSingleNode("SendNo") == null ? "Null" : ele.selectSingleNode("SendNo").getText()));
            System.out.println("Subject:" + (ele.selectSingleNode("Subject") == null ? "Null" : ele.selectSingleNode("Subject").getText()));
            System.out.println("DiPortableName:" + (ele.selectSingleNode("DiPortableName") == null ? "Null" : ele.selectSingleNode("DiPortableName").getText()));
            System.out.println("DiPortableUrl:" + (ele.selectSingleNode("DiPortableUrl") == null ? "Null" : ele.selectSingleNode("DiPortableUrl").getText()));
            System.out.println("DiName:" + (ele.selectSingleNode("DiName") == null ? "Null" : ele.selectSingleNode("DiName").getText()));
            System.out.println("DiUrl:" + (ele.selectSingleNode("DiUrl") == null ? "Null" : ele.selectSingleNode("DiUrl").getText()));

            //要下載的檔案資訊放到list，再用迴圈處理
            List<Map> FileList = new ArrayList();
            HashMap<String, String> FileMap = new HashMap();

            String di_url = ele.selectSingleNode("DiUrl") == null ? "Null" : ele.selectSingleNode("DiUrl").getText();
            String di_name = ele.selectSingleNode("DiName") == null ? "Null" : ele.selectSingleNode("DiName").getText();
            FileMap.put("type", "");
            FileMap.put("url", di_url);
            FileMap.put("filename", di_name);
            FileList.add(FileMap);

            String pdf_url = ele.selectSingleNode("DiPortableUrl") == null ? "Null" : ele.selectSingleNode("DiPortableUrl").getText();
            String pdf_name = ele.selectSingleNode("DiPortableName") == null ? "Null" : ele.selectSingleNode("DiPortableName").getText();
            FileMap = new HashMap();
            FileMap.put("type", "pdf");
            FileMap.put("url", pdf_url);
            FileMap.put("filename", pdf_name);
            FileList.add(FileMap);

            if (ele.selectSingleNode("Attach") != null) {
                List<Element> AttEles = ele.elements("Attach");
                System.out.println("AttEles:" + AttEles.size());
                for (Element attele : AttEles) {
                    String AttachUrl = attele.selectSingleNode("AttachUrl") == null ? "Null" : attele.selectSingleNode("AttachUrl").getText();
                    String AttachName = attele.selectSingleNode("AttachName") == null ? "Null" : attele.selectSingleNode("AttachName").getText();
                    FileMap = new HashMap();
                    FileMap.put("type", "attch");
                    FileMap.put("url", AttachUrl);
                    FileMap.put("filename", AttachName);
                    FileList.add(FileMap);
                }
            }

            for (Map<String, String> m : FileList) {
                String type = m.get("type");
                String download_url = m.get("url");
                String download_filename = m.get("filename");
                DownloadFile2(desDir + "\\" + type, download_url, download_filename);
            }

        }
    }

    //使用apache的HttpClients來連線
    public String doHttpGet() throws IOException, Exception {
        String rtnmsg = "";
        CloseableHttpResponse response2 = null;
        try {
            String url = BBS_URL + senddate;

            CloseableHttpClient httpclient = HttpClients.createDefault();
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
            if (response2 != null) {
                response2.close();
            }
        }
        return rtnmsg;
    }

    //使用HttpsURLConnection來連線
    public String doHttpGet2() throws IOException, Exception {
        String rtnmsg = "";
        try {
            String url = BBS_URL + senddate;

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            URL connectto = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) connectto.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getRequestProperty("Content-Type") == null
                    || conn.getRequestProperty("Content-Type").isEmpty()) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }

            try (InputStream inputStream = conn.getInputStream()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "big5"))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    rtnmsg = sb.toString();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return rtnmsg;
    }

    //使用apache的HttpClients
    public void DownloadFile(String FilePath, String Url, String FileName) throws IOException, Exception {
        CloseableHttpClient httpclient = null;
        httpclient = HttpClients.createDefault();
        FileOutputStream fos = null;
        InputStream is = null;
        CloseableHttpResponse response = null;
        try {
            URI DataUrl = new URI(Url);
            //建立HttpPost物件
            HttpGet httpGet = new HttpGet(DataUrl);
            //執行Post動作並取得回傳資料
            response = httpclient.execute(httpGet);
            File fFileUrl = new File(FilePath);
            if (!fFileUrl.exists()) {
                fFileUrl.mkdirs();
            }
            HttpEntity entity = response.getEntity();//取得回傳的資料
            response.getStatusLine().getStatusCode();
            response.getAllHeaders();
            if (entity != null) {
                is = entity.getContent();
                fos = new FileOutputStream(new File(fFileUrl, FileName));
                int inbyte;
                while ((inbyte = is.read()) != -1) {
                    fos.write(inbyte);
                }
            }
            EntityUtils.consume(entity);//釋放資源
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
            if (response != null) {
                response.close();
            }
        }

    }

    //使用Java原本的HttpsURLConnection
    public void DownloadFile2(String FilePath, String FileUrl, String FileName) throws IOException, Exception {

        try {
            if (FileUrl.toLowerCase().startsWith("https")) {
                //準備連線Https需要的物件
                SSLContext sslContext = SSLContext.getInstance("TLS");
                javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
                    new javax.net.ssl.X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
                };
                sslContext.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

                URL url = new URL(FileUrl);
                HttpsURLConnection Connection = (HttpsURLConnection) url.openConnection();
                Connection.setRequestMethod("GET");
                Connection.setUseCaches(false);
                Connection.setDoInput(true);
                Connection.setDoOutput(true);

                if (Connection.getRequestProperty("Content-Type") == null
                        || Connection.getRequestProperty("Content-Type").isEmpty()) {
                    Connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                }

                if (Connection.getResponseCode() == 200) {
                    File TargetFile = new File(FilePath);
                    if (!TargetFile.exists()) {
                        TargetFile.mkdirs();
                    }
                    TargetFile = new File(TargetFile.getPath(), FileName);

                    try (InputStream inputStream = Connection.getInputStream()) {
                        byte[] data2 = new byte[4096];
                        int bytesRead = -1;
                        try (FileOutputStream output = new FileOutputStream(TargetFile)) {
                            while ((bytesRead = inputStream.read(data2)) != -1) {
                                output.write(data2, 0, bytesRead);
                            }
                        }
                    }
                }
            } else if (FileUrl.toLowerCase().startsWith("http")) {

                URL url = new URL(FileUrl);
                HttpURLConnection Connection = (HttpURLConnection) url.openConnection();
                Connection.setRequestMethod("POST");
                Connection.setUseCaches(false);
                Connection.setDoInput(true);
                Connection.setDoOutput(true);

                if (Connection.getRequestProperty("Content-Type") == null
                        || Connection.getRequestProperty("Content-Type").isEmpty()) {
                    Connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                }

                //將資料傳輸過去
                if (!FileUrl.isEmpty()) {
                    Connection.setRequestProperty("Content-Length", String.valueOf(FileUrl.length()));
                    try (DataOutputStream dos = new DataOutputStream(Connection.getOutputStream());) {
                        dos.write(FileUrl.getBytes(Charset.forName("utf-8")));
                        dos.flush();
                    }
                }

                if (Connection.getResponseCode() == 200) {
                    File TargetFile = new File(FilePath);
                    if (!TargetFile.exists()) {
                        TargetFile.mkdirs();
                    }
                    TargetFile = new File(TargetFile.getPath(), FileName);

                    try (InputStream inputStream = Connection.getInputStream()) {
                        byte[] data2 = new byte[4096];
                        int bytesRead = -1;
                        try (FileOutputStream output = new FileOutputStream(TargetFile)) {
                            while ((bytesRead = inputStream.read(data2)) != -1) {
                                output.write(data2, 0, bytesRead);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {

        }

    }
}
