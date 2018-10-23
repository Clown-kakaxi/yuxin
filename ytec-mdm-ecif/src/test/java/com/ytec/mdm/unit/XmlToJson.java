/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.unit
 * @�ļ�����XmlToJson.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-21-����2:10:55
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�XmlToJson
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-21 ����2:10:55   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-21 ����2:10:55
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class XmlToJson {
	private static Logger log = LoggerFactory.getLogger(XmlToJson.class);
	private final String defautCfg = "dispatchEngineConfig.xml";
	private List<String> ruleCfg=new ArrayList<String>();
	public void init() throws Exception {
		Document dealDispatchCfg = null;
		SAXReader saxReader = new SAXReader();
		String cfgPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if(is!=null){
				try {
					dealDispatchCfg=saxReader.read(is);
				} catch (DocumentException e) {
					log.error("���������ļ�ʧ��:", e);
					return;
				}
			}else{
				log.error("�����ļ�{}/{}������", cfgPath, defautCfg);
				return;
			}
		}else{
			try {
				dealDispatchCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("���������ļ�ʧ��:", e);
				return;
			}
		}
		List<Element> dispatchRuleList = dealDispatchCfg
				.selectNodes("//dispatchs-config/dispatch");
		if (dispatchRuleList != null && !dispatchRuleList.isEmpty()) {
			for (Element dispatchRule : dispatchRuleList) {
				String txCode = dispatchRule.attributeValue("txCode");
				Element root = dispatchRule.element("dispatchRule");
				if (root == null) {
					log.error("{}�ĵ��ȹ���Ϊ��", txCode);
					continue;
				}
				creatDispatchRule(root,0);
				//System.out.println(ruleCfg.toString());
				buildRuleXml(ruleCfg);
				ruleCfg.clear();
			}
		}
	}

	private  void creatDispatchRule(Element root,int pid)
			throws Exception {
		String ruleType = root.attributeValue("ruleType");
		List dispatchNodes = root.elements("dispatchNode");
		StringBuffer buf=null;
		if (dispatchNodes == null || dispatchNodes.isEmpty()) {
			log.error("���Ƚ��Ϊ��");
			throw new Exception("���Ƚ��Ϊ��");
		}
		buf=new StringBuffer("{");
		buf.append("id:").append(ruleCfg.size()+1);
		buf.append(",pid:").append(pid);
		buf.append(",nodeType:\"").append("rule").append("\"");
		if ("order".equals(ruleType)) {
			buf.append(",name:\"").append("˳��ִ�й���").append("\"");
			buf.append(",ruleType:\"").append("order").append("\"");
			ruleCfg.add(buf.append("}").toString());
			createDispatchNode(dispatchNodes, ruleType,ruleCfg.size());
			
		} else if ("case".equals(ruleType)) {
			buf.append(",name:\"").append("��ִ֧�й���").append("\"");
			buf.append(",ruleType:\"").append("case").append("\"");
			String beanClass = root.attributeValue("beanClass");
			if (StringUtil.isEmpty(beanClass)) {
				log.error("�ж�����Ϊ��");
				throw new Exception("�ж�����Ϊ��");
			}
			Class clazz = Class.forName(beanClass);
			buf.append(",beanClass:\"").append(beanClass).append("\"");
			
			String initArg = root.attributeValue("initArg");
			if(initArg!=null||!"".equals(initArg)){
				buf.append(",initArg:\"").append(initArg).append("\"");
			}
			ruleCfg.add(buf.append("}").toString());
			createDispatchNode(dispatchNodes, ruleType,ruleCfg.size());
		} else {
			log.error("��֧�ֵ��ȹ���{}", ruleType);
			throw new Exception("��֧�ֵ��ȹ���" + ruleType);
		}
		return;
	}

	private  void createDispatchNode(List<Element> nodes, String type,int pid)
			throws Exception {
		String txCode = null;
		String beanName = null;
		String resultParametPath=null;
		String isReturn=null;
		StringBuffer buf=null;
		for (Element node : nodes) {
			buf=new StringBuffer("{");
			buf.append("id:").append(ruleCfg.size()+1);
			buf.append(",pid:").append(pid);
			buf.append(",name:\"").append("ִ�н��"+ruleCfg.size()+1).append("\"");
			buf.append(",nodeType:\"").append("node").append("\"");
			txCode = node.attributeValue("txCode");
			beanName = node.attributeValue("beanName");
			resultParametPath=node.attributeValue("resultParametPath");
			isReturn=node.attributeValue("isReturn");
			if(!StringUtil.isEmpty(resultParametPath)){
				//dnode.setResultParametPath(resultParametPath);
			}
			if("true".equals(isReturn)){
				//dnode.setReturn(true);
			}else{
				//dnode.setReturn(false);
			}
			if ("case".equals(type)) {
				
				String caseValue = node.attributeValue("caseValue");
				if (StringUtil.isEmpty(caseValue)) {
					log.error("�ж�����ж�ֵΪ��");
					throw new Exception("�ж�����ж�ֵΪ��");
				}
				buf.append(",caseValue:\"").append(caseValue).append("\"");
			} else {
				
				String orderStep = node.attributeValue("orderStep");
				if (StringUtil.isEmpty(orderStep)) {
					log.error("�����㲽��ֵΪ��");
					throw new Exception("�����㲽��ֵΪ��");
				}
				buf.append(",orderStep:\"").append(orderStep).append("\"");
			}
			if (!StringUtil.isEmpty(txCode)) {
				buf.append(",txCode:\"").append(txCode).append("\"");
				ruleCfg.add(buf.append("}").toString());
			} else if (!StringUtil.isEmpty(beanName)) {
				buf.append(",beanName:\"").append(beanName).append("\"");
				ruleCfg.add(buf.append("}").toString());
			} else {
				ruleCfg.add(buf.append("}").toString());
				Element root = node.element("dispatchRule");
				if(root!=null){
					creatDispatchRule(root,ruleCfg.size());
				}
			}
		}
	}
	
	public void buildRuleXml(List<String> rules){
		if(rules!=null&&rules.size()!=0){
			Document xmlDoc = DocumentHelper.createDocument();
			Element point=null;
			Element parent=null;
			Map<Integer,Element> idMap=new HashMap<Integer,Element>();
			xmlDoc.setXMLEncoding("UTF-8");
			Element rootElement=DocumentHelper.createElement("dispatchs-config");
			xmlDoc.setRootElement(rootElement);
			point=rootElement.addElement("dispatch").addAttribute("txCode", "test");
			idMap.put(0, point);
			int id;
			int pid;
			for(String rule:rules){
				rule=rule.replace("{", "").replace("}", "").replace("\"", "");
				String[] r=rule.split("\\,");
				point=DocumentHelper.createElement("rr");
				for(String r_:r){
					String[] rr=r_.split("\\:");
					if(rr[0].equals("id")){
						id=Integer.valueOf(rr[1]);
						idMap.put(id, point);
					}else if(rr[0].equals("pid")){
						pid=Integer.valueOf(rr[1]);
						parent=idMap.get(pid);
					}else if(rr[0].equals("nodeType")){
						if(rr[1].equals("rule")){
							point.setName("dispatchRule");
						}else{
							point.setName("dispatchNode");
						}
					}else{
						point.addAttribute(rr[0], rr[1]);
					}
				}
				if("dispatchRule".equals(parent.getName())&&"order".equals(parent.attribute("ruleType")) ){
					point.addAttribute("orderStep", String.valueOf((parent.elements().size())));
				}
				parent.add(point);
			}
			
			System.out.println(xmlDoc.asXML());
			idMap.clear();
		}
	}
	
	 public static void main(String[] args) throws Exception {  
		 XmlToJson dd=new XmlToJson();
		 dd.init();
	 }

}
