package com.yuchengtech.trans.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlhelperUtil {
	public List getParseXmlList(String xml) {
		List dataList = new ArrayList();
		// 内围
		List listinner = new ArrayList();
		try {
			// 外围
			Map pakagemap = outXml(xml);
			listinner = InnerxmlList(xml);
			int listsize = 0;
			listsize = InnerxmlList(xml).size();
			if (listsize > 0) {
				System.out.println("bug" + listinner);
				for (int i = 0; i < listsize; i++) {
					Map outMap = (Map) listinner.get(i);
					outMap.putAll(pakagemap);
					dataList.add(outMap);
				}
			} else {
				dataList.add(pakagemap);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * @param xml
	 * @return 转换List
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static List InnerxmlList(String xml) throws UnsupportedEncodingException {
		List list = new ArrayList();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = (Document) sb.build(is);
			Element root = doc.getRootElement();
			list.add(iterateElement(root));
			Map myMap = new HashMap();
			myMap = iterateElement(root);
			List datalist = new ArrayList();
			Map<String, List<?>> tempMap = myMap;
			for (Iterator<String> iterator = tempMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				List<?> templist = tempMap.get(key);
				if (templist.size() > 1) {

					for (int i = 0; i < templist.size(); i++) {
						if (templist.size() > 1) {
							datalist = templist;
						}
					}
				} else {
					for (int i = 0; i < templist.size(); i++) {
						Object ob = templist.get(i);
						if (ob instanceof Map) {
							datalist = templist;
						}
					}
				}
			}
			list = datalist;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	public static Map outXml(String xml) throws UnsupportedEncodingException {
		Map myMap = new HashMap();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = (Document) sb.build(is);
			Element root = doc.getRootElement();
			myMap = iterateElementFirst(root);
			return myMap;
		} catch (Exception e) {
			e.printStackTrace();
			return myMap;
		}
	}

	/**
	 * 一个迭代方法
	 * 
	 * @param element
	 *        : org.jdom.Element
	 * @return java.util.Map 实例
	 */
	@SuppressWarnings("unchecked")
	private static Map iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				// 删除两行
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}

	// 得到
	@SuppressWarnings("unchecked")
	private static Map iterateElementFirst(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}
}
