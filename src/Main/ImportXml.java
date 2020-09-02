package Main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 *
 * @author Mike
 */
public class ImportXml {

    public String Import() throws Exception {
        String rtnMessage = "";
        Map rtnmap = new HashMap();

        try {
            byte[] data = null;
            InputStream inputStream = null;

            //匯入xml
            String FilePath = "D:\\Project\\Mike\\__MikeTest\\test\\xml";
            String FileNam = "本院人事行政總處21_000467.txt";
            FileNam = "本院人事行政總處34_000027.txt";
            File xmlFile = new File(FilePath, FileNam);

            if (!xmlFile.isFile()) {
                return "檔案不存在";
            }

            inputStream = new FileInputStream(xmlFile);
            data = IOUtils.toByteArray(inputStream);

            org.dom4j.Document didom = XmlTool.loadDI(data);

            String NSt = "";
            List<Node> Nodes2 = didom.getRootElement().selectNodes("//立委質詢/案");
            for (Node N : Nodes2) {
                NSt += N.selectSingleNode("案號").getText() + "\n";
            }

            Element root = didom.getRootElement();

            for (Iterator it = root.elementIterator(); it.hasNext();) {
                Element element_1 = (Element) it.next();
                element_1.getQName().getName();

                for (Iterator it_2 = element_1.elementIterator(); it_2.hasNext();) {

                    Element element_2 = (Element) it_2.next();
                    element_2.getQName().getName();

                }

            }

            String DocDesc = "";
            List<Node> Nodes = didom.getRootElement().selectNodes("//案/案由/段落");
            for (Node node : Nodes) {
                if (!node.selectSingleNode("文字").getText().isEmpty()) {
                    DocDesc += node.selectSingleNode("文字").getText() + "\n";
                }
            }
            //<editor-fold defaultstate="collapsed" desc="不重要的東西">
            String OrgDocNO_Char = XmlTool.getNodeValue(didom, "案/來函字").trim();
            String OrgDocNO_Num = XmlTool.getNodeValue(didom, "案/來函號").trim();
            String OrgDocNO = OrgDocNO_Char + "字第" + OrgDocNO_Num + "號";

            String OrgSndDate = XmlTool.getNodeValue(didom, "//案/交辦日期/年月日").trim();
            OrgSndDate = OrgSndDate.replace("年", "/").replace("月", "/").replace("日", "");
            String Member = XmlTool.getNodeValue(didom, "//案/委員").trim();

            rtnmap.put("docdesc", DocDesc);
            rtnmap.put("orgdocno", OrgDocNO);
            rtnmap.put("orgsnddate", OrgSndDate);
            rtnmap.put("member", Member);
            //</editor-fold>

            rtnMessage = "DocDesc：" + DocDesc;
            //rtnMessage = NSt;
        } catch (Exception e) {
            throw e;
        }
        return rtnMessage;
    }

}
