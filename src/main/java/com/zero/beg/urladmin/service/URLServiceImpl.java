package com.zero.beg.urladmin.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.zero.beg.urladmin.common.util.PathUtil;
import com.zero.beg.urladmin.model.URLAdminPO;
import com.zero.beg.urladmin.model.URLCategroyPO;
import com.zero.beg.urladmin.model.URLPO;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class URLServiceImpl implements URLService {

    public static final String CONFIG_XML_FILENAME = "config.xml";
    public static final String ELEMENT_CATEGROY = "./categroy";
    public static final String ELEMENT_ROOT_ATT_URL = "url";
    public static final String ELEMENT_CATEGROY_ATT_TITLE = "title";
    public static final String ELEMENT_URL = "url";
    public static final String ELEMENT_URL_ATT_TEXT = "text";
    public static final String ELEMENT_URL_ATT_HREF = "href";
    public static final String ELEMENT_URL_ATT_DESC = "desc";


    @Autowired
    PathUtil pathUtil;

    @Override
    public URLAdminPO getURLAdmin() {
        String homePath = pathUtil.getHomePath();
        String configFilePath = homePath + File.separator + CONFIG_XML_FILENAME;
        File urlAdminFile = new File(configFilePath);
        if (!urlAdminFile.exists()) {
            throw new RuntimeException("Can't find config file:" + configFilePath);
        }
        URLAdminPO urlAdminPO = loadURLAdmin(configFilePath);
        return urlAdminPO;
    }

    private URLAdminPO loadURLAdmin(String configFilePath) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(configFilePath);
        } catch (DocumentException e) {
            throw new RuntimeException("Load xml error ", e);
        }

        Element root = document.getRootElement();
        List<Node> categroyNodes = root.selectNodes(ELEMENT_CATEGROY);

        List<URLCategroyPO> urlCategroies = new ArrayList<>();
        for (Node categroyNode : categroyNodes) {
            Element categroyEle = (Element) categroyNode;
            URLCategroyPO urlCategroyPO = loadURLCategroy(categroyEle);
            urlCategroies.add(urlCategroyPO);
        }
        String url = root.attributeValue(ELEMENT_ROOT_ATT_URL);
        URLAdminPO urlAdminPO = new URLAdminPO(url, urlCategroies);
        String base64Image = generateQRCodeBase64Image(url);
        urlAdminPO.setImg(base64Image);
        return urlAdminPO;
    }

    private String generateQRCodeBase64Image(String url){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix =  qrCodeWriter.encode(url, BarcodeFormat.QR_CODE,360,360);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            return "data:image/png;base64,"+encoder.encodeToString(pngOutputStream.toByteArray());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private URLCategroyPO loadURLCategroy(Element categroyEle) {
        List<Node> urlNodes = categroyEle.selectNodes(ELEMENT_URL);
        List<URLPO> urlPOs = new ArrayList<>();
        for (Node urlNode : urlNodes) {
            Element urlEle = (Element)urlNode;
            String text = urlEle.attributeValue(ELEMENT_URL_ATT_TEXT);
            String href = urlEle.attributeValue(ELEMENT_URL_ATT_HREF);
            String desc = urlEle.attributeValue(ELEMENT_URL_ATT_DESC);
            URLPO urlPO = new URLPO(text,href,desc);
            urlPOs.add(urlPO);
        }
        String title = categroyEle.attributeValue(ELEMENT_CATEGROY_ATT_TITLE);
        URLCategroyPO urlCategroyPO = new URLCategroyPO(title,urlPOs);
        return urlCategroyPO;
    }
}
