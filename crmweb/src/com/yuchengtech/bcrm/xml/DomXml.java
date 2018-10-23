/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2010 CITIC Holdings All rights reserved.
 * 
 * Created on 2013-6-19
 *******************************************************************************/


package com.yuchengtech.bcrm.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yuchengtech.crm.exception.BizException;

public class DomXml {

    //存储xml元素信息的容器    
	public static ArrayList<Map<String,String>> elemList = new ArrayList<Map<String,String>>();
    public static final String FaultCode = "FaultCode";//esb返回码值
    public static final String TxnStat = "TxnStat";//esb成功标识
    public static final String FaultString = "FaultString";
    public static final String CustNo = "custNo";
    public static String faultCode = "";
    public static String txnStat = "";
    public static String faultString = "";
    public static Map<String,String> mapBean;//存储查询列表数据
    public static Map<String,String> map;
    
	/**
	 * 解析xml方法
	 * @param xml xml字符串
	 * @param txnCd 上送交易码
	 */
	public static Map<String, String> readString2Xml(String xml) {
		Document doc = null;
		try {
			//map.clear();
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();//获取根节点
			Map<String, String> map = getElementMap(rootElt, null);
//			Map esbReturnMap = new HashMap();
//			esbReturnMap.put("faultCode", faultCode);
//			esbReturnMap.put("txnStat", txnStat);
//			esbReturnMap.put("faultString", faultString);
			return map;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BizException(1,0,"001","解析xml错误");
		} catch (BizException e) {
			throw new BizException(1,0,e.getCode(),e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"1003","解析xml异常");
		}
	}
	
	/**
	 * 解析XML信息，将多条信息及返回码封装到Map中
	 * @param xml xml字符串
	 * @param txnCd 上送交易码
	 */
	public static Map readRowListToMap(String xml, String listRowName) {
		Document doc = null;
		try {
			DomXml.elemList.clear();
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();//获取根节点
			getElementList(rootElt, listRowName);
			Map esbReturnMap = new HashMap();
			esbReturnMap.put("rowList", elemList);
			esbReturnMap.put("faultCode", faultCode);
			esbReturnMap.put("txnStat", txnStat);
			esbReturnMap.put("faultString", faultString);
			return esbReturnMap;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BizException(1,0,"001","解析xml错误");
		} catch (BizException e) {
			throw new BizException(1,0,e.getCode(),e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"1003","解析xml异常");
		}
	}
	
	
	/**
	 * 解析获取列表信息
	 * @param element
	 * @param listRowName
	 */
	public static Map<String, String> getElementMap(Element element, HashMap<String, String> map) {
		try {
			List elements = element.elements();
			if (map == null) {
				map = new HashMap<String, String>();
			}
	        //没有子元素
	    	if (elements.isEmpty()) {
		        String value = element.getTextTrim();
		        String qName = element.getQName().getName();
		        map.put(qName, value);
	        } else {   
	        	// 有子元素   
	        	Iterator it = elements.iterator();   
		        while (it.hasNext()) {
		        	Element elem = (Element)it.next();   
		            // 递归遍历    
		        	getElementMap(elem, map);
		        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"002","解析xml节点错误");
		}
		return map;
	}
	
	/**
	 * 解析获取列表信息
	 * @param element
	 * @param listRowName
	 */
	public static void getElementList(Element element, String listRowName) {
		try {
			List elements = element.elements();
			Element parNode = element.getParent();
			if (parNode != null && listRowName.equals(parNode.getQName().getName())) {
				mapBean = new HashMap<String,String>();
				elemList.add(mapBean);
			}
	        //没有子元素
	    	if (elements.isEmpty()) {
		        String value = element.getTextTrim();
		        String qName = element.getQName().getName();
	        	if (mapBean != null) {
	        		mapBean.put(qName, value);
	        	}
	        } else {   
	        	// 有子元素   
	        	Iterator it = elements.iterator();   
		        while (it.hasNext()) {
		        	Element elem = (Element)it.next();   
		            // 递归遍历    
		            getElementList(elem, listRowName);
		        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"002","解析xml节点错误");
		}
	}
	
    /**   
     * 获取节点所有属性值  
     * @param element  
     * @return 返回节点名称
     */  
    public static String getQname(Element element) {
    	return element.getQName().getName();
//        String xattribute = "";
//        DefaultAttribute e = null;
//        System.out.println(element.getQName().getName()+".................");
//        List list = element.attributes();   
//        for (int i = 0; i < list.size(); i++) {
//            e = (DefaultAttribute)list.get(i);  
//            //System.out.println("name = " + e.getName() + ", value = " + e.getText());   
//            xattribute += " [name = " + e.getName() + ", value = " + e.getText() + "]";
//        }
//        return xattribute;
    }
    
    public static void main(String[] args) {
    	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ResponseBody><addressList><address><addrType>A2131231231231</addrType><addr>A2131231231231</addr><zipcode/></address><address><addrType>2</addrType><addr>北京市海淀区维护-1中关村-维护-1科贸大厦1031室维护</addr><zipcode/></address></addressList></ResponseBody>";
    	DomXml.readRowListToMap(xml, "addressList");
    	System.out.println(DomXml.elemList.size());
    	System.out.println(DomXml.mapBean.size()+"map");
//    	DomXml.readString2Xml(xml);
//    	System.out.println(DomXml.elemList.size());
//    	System.out.println(DomXml.mapBean.size()+"map");
	}

}
