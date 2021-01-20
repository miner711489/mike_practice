package ODBBS;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 *
 * @author Mike
 */
public class httpMethod {

    private int statusCode = -1;

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * GET
     *
     * @param url
     * @param data
     * @param requestProperty
     * @return
     * @throws Exception
     */
    public String doGet(String url, String data, HashMap requestProperty) throws Exception {
        url = url.replace("..", "");
        URL connectto = new URL(url);
        if (connectto.getProtocol().toLowerCase().startsWith("https")) {
            return doHttpsGet(connectto, data, requestProperty);
        } else if (connectto.getProtocol().toLowerCase().startsWith("http")) {
            return doHttpGet(connectto, data, requestProperty);
        } else {
            throw new Exception("不接受的連線協議，應為 https 或 http");
        }
    }

    /**
     * GET HTTP
     *
     * @param url
     * @param data
     * @param contentType application/x-www-form-urlencoded; charset=utf-8
     * @return
     * @throws Exception
     */
    private String doHttpGet(URL connectto, String data, HashMap requestProperty) throws Exception {
        statusCode = -1;
        try {
            HttpURLConnection conn = (HttpURLConnection) connectto.openConnection();
            if (requestProperty != null) {
                requestProperty.keySet().forEach((key) -> {
                    conn.setRequestProperty(key.toString(), requestProperty.get(key).toString());
                });
            }

            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
//            conn.setAllowUserInteraction(false);
            //避免中風險SSRF之禁止30x跳轉
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getRequestProperty("Content-Type") == null
                    || conn.getRequestProperty("Content-Type").isEmpty()) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }
            statusCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("doHttpPost錯誤：" + e.getMessage());
        }
    }

    /**
     * GET HTTPS
     *
     * @param url
     * @param data
     * @param contentType application/x-www-form-urlencoded; charset=utf-8
     * @return
     * @throws Exception
     */
    private String doHttpsGet(URL connectto, String data, HashMap requestProperty) throws Exception {
        statusCode = -1;
        try {
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
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            HttpsURLConnection conn = (HttpsURLConnection) connectto.openConnection();
            if (requestProperty != null) {
                requestProperty.keySet().forEach((key) -> {
                    conn.setRequestProperty(key.toString(), requestProperty.get(key).toString());
                });
            }

            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
//            conn.setAllowUserInteraction(false);
            //避免中風險SSRF之禁止30x跳轉
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getRequestProperty("Content-Type") == null
                    || conn.getRequestProperty("Content-Type").isEmpty()) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }
            statusCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "big5"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("doHttpsPost錯誤：" + e.getMessage());
        }
    }

    /**
     * POST
     *
     * @param url
     * @param data
     * @param requestProperty
     * @return
     * @throws Exception
     */
    public String doPost(String url, HashMap data, HashMap requestProperty) throws Exception {
        url = url.replace("..", "");
        URL connectto = new URL(url);
        Gson gson = new Gson();
        if (connectto.getProtocol().toLowerCase().startsWith("https")) {
            return doHttpsPost(connectto, gson.toJson(data), requestProperty);
        } else if (connectto.getProtocol().toLowerCase().startsWith("http")) {
            return doHttpPost(connectto, gson.toJson(data), requestProperty);
        } else {
            throw new Exception("不接受的連線協議，應為 https 或 http");
        }
    }

    /**
     * POST
     *
     * @param url
     * @param data
     * @param requestProperty
     * @return
     * @throws Exception
     */
    public String doPost(String url, String data, HashMap requestProperty) throws Exception {
        url = url.replace("..", "");
        URL connectto = new URL(url);
        if (connectto.getProtocol().toLowerCase().startsWith("https")) {
            return doHttpsPost(connectto, data, requestProperty);
        } else if (connectto.getProtocol().toLowerCase().startsWith("http")) {
            return doHttpPost(connectto, data, requestProperty);
        } else {
            throw new Exception("不接受的連線協議，應為 https 或 http");
        }
    }

    /**
     * POST HTTP
     *
     * @param url
     * @param data
     * @param contentType application/x-www-form-urlencoded; charset=utf-8
     * @return
     * @throws Exception
     */
    private String doHttpPost(URL connectto, String data, HashMap requestProperty) throws Exception {
        statusCode = -1;
        try {
            HttpURLConnection conn = (HttpURLConnection) connectto.openConnection();
            if (requestProperty != null) {
                requestProperty.keySet().forEach((key) -> {
                    conn.setRequestProperty(key.toString(), requestProperty.get(key).toString());
                });
            }

            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
//            conn.setAllowUserInteraction(false);
            //避免中風險SSRF之禁止30x跳轉
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getRequestProperty("Content-Type") == null
                    || conn.getRequestProperty("Content-Type").isEmpty()) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }
            if (data != null && data.length() > 0) {
                conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.write(data.getBytes(Charset.forName("utf-8")));
                    dos.flush();
                } finally {
                    if (dos != null) {
                        dos.close();
                    }
                }
            }
            statusCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("doHttpPost錯誤：" + e.getMessage());
        }
    }

    /**
     * POST HTTPS
     *
     * @param url
     * @param data
     * @param contentType application/x-www-form-urlencoded; charset=utf-8
     * @return
     * @throws Exception
     */
    private String doHttpsPost(URL connectto, String data, HashMap requestProperty) throws Exception {
        statusCode = -1;
        try {
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
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            HttpsURLConnection conn = (HttpsURLConnection) connectto.openConnection();
            if (requestProperty != null) {
                requestProperty.keySet().forEach((key) -> {
                    conn.setRequestProperty(key.toString(), requestProperty.get(key).toString());
                });
            }

            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
//            conn.setAllowUserInteraction(false);
            //避免中風險SSRF之禁止30x跳轉
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getRequestProperty("Content-Type") == null
                    || conn.getRequestProperty("Content-Type").isEmpty()) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }
            if (data != null && data.length() > 0) {
                conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.write(data.getBytes(Charset.forName("utf-8")));
                    dos.flush();
                } finally {
                    if (dos != null) {
                        dos.close();
                    }
                }
            }
            statusCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("doHttpsPost錯誤：" + e.getMessage());
        }
    }
}
