package ODBBS;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
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

    public void ODBBSMain() throws IOException, Exception {

        try {
            String XmlString = doHttpGet();
            parseXMLStr(XmlString);
        } catch (Exception e) {
            throw e;
        }
    }

    public void parseXMLStr(String XmlStr) throws Exception {
        int index = 0;

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
            if (ele.selectSingleNode("Attach") != null) {
                List<Element> AttEles = ele.elements("Attach");
                System.out.println("AttEles:" + AttEles.size());
                for (Element attele : AttEles) {
                    System.out.println("AttachName:" + (attele.selectSingleNode("AttachName") == null ? "Null" : attele.selectSingleNode("AttachName").getText()));
                    System.out.println("AttachUrl:" + (attele.selectSingleNode("AttachUrl") == null ? "Null" : attele.selectSingleNode("AttachUrl").getText()));
                }
            } else {
                System.out.println("AttEles:0");
            }

            index++;
        }

    }

    public String doHttpGet() throws IOException, Exception {
        String rtnmsg = "";
        CloseableHttpResponse response2 = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String url = "https://www.odbbs.gov.tw/odbbs/html/getEDoc.jsp?senddate=";
            String senddate = "20210106";
            url += senddate;

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

    public void getElementmethod2(Document document) {
        int index = 0;
        System.out.println("method2:");
        //方法二，使用elementIterator()，轉成Iterator
        index = 0;
        for (Iterator i = document.getRootElement().elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eleName = element.getName();
            if (eleName.toLowerCase().equals("senddate") || eleName.toLowerCase().equals("sendno")) {
                continue;
            }
            System.out.println("Element Name" + String.valueOf(index) + "：" + eleName);

            index++;
        }
    }

}
