/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import org.w3c.dom.Document;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 *
 * @author Mike
 */
public class path {

    private String Path = "";

    public String getFilePath() {
        Path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        return Path;
    }

    public void getDocument() {

        try {
            File file = new File("D:\\Project\\test\\build\\classes\\doccument\\test.xml");
            DocumentBuilder builder = null;
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();

            Document document;
            document = builder.parse(file);

            Element root = document.getDocumentElement();

            // 獲得根元素下的子節點
            NodeList childNodes = root.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                System.out.println(node.getNodeName() + ":" + node.getTextContent());
            }

        } catch (Exception e) {

        }

    }

}
