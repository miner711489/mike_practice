/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 *
 * @author Mike
 */
public class XmlTool {

    /**
     * 將byte[]載入XML DOM類別
     *
     * @param rcvDIByteArray
     * @return dom4j.Document
     * @throws DocumentException
     * @throws IOException
     */
    public static org.dom4j.Document loadDI(byte[] rcvDIByteArray) throws DocumentException, IOException {
        EntityResolver resolver = new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws IOException {
                String empty = "";
                InputSource is;
                try (ByteArrayInputStream bais = new ByteArrayInputStream(empty.getBytes())) {
                    is = new InputSource(bais);
                    is.setEncoding("UTF-8");
                }
                return is;
            }
        };

        org.dom4j.Document d4j;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(rcvDIByteArray)) {
            try {
                SAXReader reader = new SAXReader();
                reader.setEncoding("UTF-8");
                reader.setValidation(false);
                reader.setEntityResolver(resolver);
                d4j = reader.read(bis);
            } catch (Exception e) {
                bis.reset();
                SAXReader reader = new SAXReader();
                reader.setEncoding("BIG5");
                reader.setValidation(false);
                reader.setEntityResolver(resolver);

                d4j = reader.read(bis);
            }
        }
        return d4j;
    }
    /**
     * 取得指定的節點
     *
     * @param xmlDoc
     * @param strNodePath
     * @return
     * @throws Exception
     */
    public static org.dom4j.Node getNode(org.dom4j.Document xmlDoc, String strNodePath) throws Exception {
        if (xmlDoc == null) {
            throw new Exception("Xml文件為空值");
        }

        org.dom4j.Element root = xmlDoc.getRootElement();
        org.dom4j.Node retNode = root.selectSingleNode(strNodePath);

        return retNode;
    }

    /**
     * 取得指定節點的資料內容
     *
     * @param xmlDoc
     * @param strNodePath
     * @return
     * @throws Exception
     */
    public static String getNodeValue(org.dom4j.Document xmlDoc, String strNodePath) throws Exception {
        String retValue;

        if (xmlDoc == null) {
            throw new Exception("Xml文件為空值");
        }

        org.dom4j.Element root = xmlDoc.getRootElement();
        org.dom4j.Node tmpNode = root.selectSingleNode(strNodePath);
        if (tmpNode == null) {
            throw new Exception("找不到指定的Xml節點(" + strNodePath + ")");
        }

        retValue = tmpNode.getText().trim();

        return retValue;
    }

    /**
     * 取得指定的節點屬性
     *
     * @param xmlDoc
     * @param strNodePath
     * @param strAttributeName
     * @return
     * @throws Exception
     */
    public static org.dom4j.Attribute getNodeAttribute(org.dom4j.Document xmlDoc, String strNodePath, String strAttributeName) throws Exception {
        org.dom4j.Attribute retAttribute;

        if (xmlDoc == null) {
            throw new Exception("Xml文件為空值");
        }

        org.dom4j.Element root = xmlDoc.getRootElement();
        org.dom4j.Node tmpNode = root.selectSingleNode(strNodePath);
        if (tmpNode == null) {
            return null;
        }

        org.dom4j.Element tmpElement = (org.dom4j.Element) tmpNode;
        retAttribute = tmpElement.attribute(strAttributeName);

        return retAttribute;
    }

    /**
     * 取得指定節點屬性的資料內容
     *
     * @param xmlDoc
     * @param strNodePath
     * @param strAttributeName
     * @return
     * @throws Exception
     */
    public static String getNodeAttributeValue(org.dom4j.Document xmlDoc, String strNodePath, String strAttributeName) throws Exception {
        String retValue;

        if (xmlDoc == null) {
            throw new Exception("Xml文件為空值");
        }

        org.dom4j.Element root = xmlDoc.getRootElement();
        org.dom4j.Node tmpNode = root.selectSingleNode(strNodePath);
        if (tmpNode == null) {
            throw new Exception("找不到指定的節點(" + strNodePath + ")");
        }

        org.dom4j.Element tmpElement = (org.dom4j.Element) tmpNode;
        retValue = tmpElement.attributeValue(strAttributeName);
        if (retValue == null) {
            throw new Exception("找不到指定的節點屬性(" + strNodePath + "." + strAttributeName + ")");
        }

        return retValue.trim();
    }

}
