/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
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

    public static org.dom4j.Document changeDIDom(org.dom4j.Document didom, String diRootName) throws Exception {
        //<editor-fold defaultstate="collapsed" desc="範本令的部份增加判斷">
        if (diRootName.contains("獎懲令") || diRootName.contains("派免令") || diRootName.contains("派免兼令")) {
            //尋找段落/文字無值Tag 並移除
            List<org.dom4j.Node> mNodes = didom.getRootElement().selectNodes("//令/段落");
            for (org.dom4j.Node s : mNodes) {
                if (((org.dom4j.Element) s).attributeCount() == 0 && s.getText().equals("") && s.selectSingleNode("//段落/條列") == null) {
                    s.detach();
                }
            }

            //產生新的段落
            org.dom4j.Document nDocument = org.dom4j.DocumentHelper.createDocument();
            org.dom4j.Element nRoot = nDocument.addElement("段落");
            nRoot.addAttribute("段名", "主旨：");
            org.dom4j.Element aElement1 = nRoot.addElement("文字"); //放主旨

            //確認是否有主旨/文字
            if (didom.getRootElement().selectSingleNode("//主旨/文字") != null) {
                aElement1.setText(didom.getRootElement().selectSingleNode("//主旨/文字").getText());
            }

            //確認是否有條列 序號=""
            if (didom.getRootElement().selectSingleNode("//條列[@序號='']") == null) {
                org.dom4j.Element aElement2 = nRoot.addElement("條列"); // 條列序號 放段落/文字
                aElement2.addAttribute("序號", "");
                org.dom4j.Element aElement3 = aElement2.addElement("文字");
                if (didom.getRootElement().selectSingleNode("//段落/文字") != null) {
                    //aElement3.setText(didom.getRootElement().selectSingleNode("//段落/文字").getText());
                    String tagAttrNam = null;
                    if (didom.getRootElement().selectSingleNode("//段落/文字").getParent().attributeCount() > 0) {
                        tagAttrNam = didom.getRootElement().selectSingleNode("//段落/文字").getParent().attribute("段名").getText();
                    }
                    if (tagAttrNam == null || tagAttrNam.equals("")) {
                        aElement3.setText(didom.getRootElement().selectSingleNode("//段落/文字").getText());
                    }
                }
            }

            //搬
            List<org.dom4j.Node> sElement = didom.getRootElement().selectNodes("//段落/條列");
            for (org.dom4j.Node s : sElement) {
                if (s.getParent().attributeValue("段名") == null || s.getParent().attributeValue("段名") == "" || s.getParent().attributeValue("段名").equals("主旨：")) {
                    nRoot.add(((org.dom4j.Element) s).createCopy());
                }
            }
            org.dom4j.Element oElement = (org.dom4j.Element) didom.getRootElement().selectSingleNode("//令/段落");
            if (oElement != null) {
                oElement.detach();
            }
            didom.getRootElement().add(nRoot); //取代原有的段落(主旨)
        }

        //</editor-fold>
        return didom;
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
