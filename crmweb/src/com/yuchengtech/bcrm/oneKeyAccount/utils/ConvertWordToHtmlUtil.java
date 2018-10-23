package com.yuchengtech.bcrm.oneKeyAccount.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import com.yuchengtech.bob.upload.FileTypeConstance;
  
/**
 * 将word转换成html
 * @author wx
 *
 */
public class ConvertWordToHtmlUtil {  
  
    /*public static void main(String argv[]) {  
        try {  
            convert2Html("E://111//bb.doc","E://111//1.html");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }*/  
  
    public static void writeFile(String content, String path) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
            }  
		}
	}  
  
    public static String convert2Html(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException {  
        //读取word文件
    	HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));  
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(  
                DocumentBuilderFactory.newInstance().newDocumentBuilder()  
                        .newDocument());  
         wordToHtmlConverter.setPicturesManager( new PicturesManager()  
         {  
             public String savePicture( byte[] content,  
                     PictureType pictureType, String suggestedName,  
                     float widthInches, float heightInches )  
             {  
                 return "test/"+suggestedName;  
             }  
         } );  
        wordToHtmlConverter.processDocument(wordDocument); 
        String localPicPath = FileTypeConstance.getBipProperty("crm.localPicPath");
        //如果有图片，现将图片保存到本地，页面上读取本地图片来显示
        List<Picture> pics=wordDocument.getPicturesTable().getAllPictures();  
        if(pics!=null){  
            for(int i=0;i<pics.size();i++){  
                Picture pic = (Picture)pics.get(i);  
                System.out.println();  
                try {  
                    pic.writeImageContent(new FileOutputStream(localPicPath  
                            + pic.suggestFullFileName()));  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                }    
            }  
        }  
        Document htmlDocument = wordToHtmlConverter.getDocument();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        DOMSource domSource = new DOMSource(htmlDocument);  
        StreamResult streamResult = new StreamResult(out);  
  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer serializer = tf.newTransformer();  
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");  
        serializer.setOutputProperty(OutputKeys.METHOD, "html");  
        serializer.transform(domSource, streamResult);  
        out.close();
        System.out.println("htmlContent:["+new String(out.toByteArray())+"]");
        return new String(out.toByteArray());
        //writeFile(new String(out.toByteArray()), outPutFile);  
    }  
    
    
    public static String getWordContentHtml(String fileName) throws TransformerException, IOException, ParserConfigurationException {  
        //读取word文件
//    	System.err.println("开户手册文件地址："+fileName);
    	HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));  
//        System.err.println(wordDocument);
    	WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());  
         wordToHtmlConverter.setPicturesManager(new PicturesManager(){  
             public String savePicture( byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches ){  
                 String localPicPath = FileTypeConstance.getBipProperty("crm.localPicPath");  
            	 return localPicPath+suggestedName;  
             }  
         });  
        wordToHtmlConverter.processDocument(wordDocument);  
        //如果有图片，将图片保存到本地，页面上读取本地图片来显示
        List<Picture> pics=wordDocument.getPicturesTable().getAllPictures();
        String localPicPath = FileTypeConstance.getBipProperty("crm.localPicPath");  
        if(pics!=null){  
            for(int i=0;i<pics.size();i++){  
                Picture pic = (Picture)pics.get(i);  
                System.out.println();  
                try {  
                    pic.writeImageContent(new FileOutputStream(localPicPath  
                            + pic.suggestFullFileName()));  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                }    
            }  
        }  
        Document htmlDocument = wordToHtmlConverter.getDocument();//Document
        ByteArrayOutputStream out = new ByteArrayOutputStream();//ByteArrayOutputStream
        DOMSource domSource = new DOMSource(htmlDocument);//DOMSource
        StreamResult streamResult = new StreamResult(out);//StreamResult
  
        TransformerFactory tf = TransformerFactory.newInstance();//TransformerFactory 
        Transformer serializer = tf.newTransformer();//Transformer
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//ENCODING
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");//INDENT 
        serializer.setOutputProperty(OutputKeys.METHOD, "html");//METHOD
        serializer.transform(domSource, streamResult);//transform 
        out.close();
        //writeFile(new String(out.toByteArray()), "E://111//1.html");  
        return new String(out.toByteArray());//"111";//
	}
}  