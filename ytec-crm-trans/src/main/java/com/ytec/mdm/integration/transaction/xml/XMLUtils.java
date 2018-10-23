/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.xml
 * @文件名：XMLUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:32:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：XMLUtils
 * @类描述：XML处理工具类
 * @功能描述:XML处理工具类 
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:32:05   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:32:05
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class XMLUtils {

	
	 /**
	  * 将XML文档对象转换成String
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
	    * 将String转换成XML文档对象
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
	     * 根据xpath或者节点值
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
	 * @函数名称:formatXml
	 * @函数描述:格式化XML
	 * @参数与返回说明:
	 * 		@param doc
	 * 		@return
	 * @算法描述:
	 */
	public static String formatXml(Document doc) {
		// 创建输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 制定输出xml的编码类型
		if(doc.getXMLEncoding()==null){
			format.setEncoding(MdmConstants.TX_XML_ENCODING);
		}
		
		StringWriter writer = new StringWriter();
		// 创建一个文件输出流
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		// 将格式化后的xml串写入到文件
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
		// 返回编译后的字符串格式
		return returnValue;

	}
	    
	    
}
