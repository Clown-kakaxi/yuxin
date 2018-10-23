/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.xml
 * @�ļ�����XMLUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:32:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.xml;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ytec.mdm.base.util.MdmConstants;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�XMLUtils
 * @��������XML��������
 * @��������:XML�������� 
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:32:05   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:32:05
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class XMLUtils {

	
	 /**
	  * ��XML�ĵ�����ת����String
	  * @param doc
	  * @return
	  * @throws IOException
	  */
	   public static String xmlToString(Document doc){
		   if(doc!=null){
			   if(doc.getXMLEncoding()==null){
				   doc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
			   }
			   return doc.asXML();
		   }else{
			   return null;
		   }
	    	
	    }
	   
	   public static String elementToString(Element ele){
	    	return  ele.asXML();
	   }
	    
	   /**
	    * ��Stringת����XML�ĵ�����
	    * @param xmlStr
	    * @return
	 * @throws DocumentException 
	    * @throws JDOMException
	    * @throws IOException
	    */
	    public static Document stringToXml(String xmlStr) throws DocumentException{
	    	
	    	StringReader sr = new StringReader(xmlStr);
	    	SAXReader saxReader = new SAXReader();
	    	//saxReader.setEncoding(MdmConstants.TX_XML_ENCODING);
	    	Document doc=saxReader.read(sr);
	    	return doc;
	    }
	    
	    
	    /**
	     * ����xpath���߽ڵ�ֵ
	     * @param root
	     * @param xpath
	     * @return
	     * @throws JDOMException
	     */
	    public static String getElementTextByXPath(Element root,String xpath){
		   Element element =(Element)root.selectSingleNode(xpath);
		   if(element==null){
			   return null;
		   }
		   return element.getTextTrim();
	    }
	    
	/**
	 * @��������:formatXml
	 * @��������:��ʽ��XML
	 * @�����뷵��˵��:
	 * 		@param doc
	 * 		@return
	 * @�㷨����:
	 */
	public static String formatXml(Document doc) {
		// ���������ʽ
		OutputFormat format = OutputFormat.createPrettyPrint();
		// �ƶ����xml�ı�������
		if(doc.getXMLEncoding()==null){
			format.setEncoding(MdmConstants.TX_XML_ENCODING);
		}
		
		StringWriter writer = new StringWriter();
		// ����һ���ļ������
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		// ����ʽ�����xml��д�뵽�ļ�
		String returnValue = null;
		try {
			xmlwriter.write(doc);
			returnValue = writer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ر������ַ�����ʽ
		return returnValue;

	}
	    
	    
}
