/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����DealDispatchCfg.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:53:25
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.ICaseDispatch;
import com.ytec.mdm.integration.transaction.facade.IExtCaseDispatch;
import com.ytec.mdm.integration.transaction.model.DispatchNode;
import com.ytec.mdm.integration.transaction.model.DispatchRule;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DealDispatchCfg
 * @����������Ͻ�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:53:26   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:53:26
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DealDispatchCfg {
	private static Logger log = LoggerFactory.getLogger(DealDispatchCfg.class);
	private final String defautCfg = "dispatchEngineConfig.xml";
	private static Map<String, DispatchRule> dispatchRuleMap = new HashMap<String, DispatchRule>();

	public void init() throws Exception {
		dispatchRuleMap.clear();
		Document dealDispatchCfg = null;
		SAXReader saxReader = new SAXReader();
		String cfgPath = Thread.currentThread().getContextClassLoader()
				.getResource("").toURI().getPath();
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
				DispatchRule rule = creatDispatchRule(root);
				dispatchRuleMap.put(txCode, rule);
			}
		}
	}

	private  DispatchRule creatDispatchRule(Element root)
			throws Exception {
		DispatchRule rule = new DispatchRule();
		String ruleType = root.attributeValue("ruleType");
		List dispatchNodes = root.elements("dispatchNode");
		if (dispatchNodes == null || dispatchNodes.isEmpty()) {
			log.error("���Ƚ��Ϊ��");
			throw new Exception("���Ƚ��Ϊ��");
		}
		if ("order".equals(ruleType)) {
			rule.setNodeList((List) createDispatchNode(dispatchNodes, ruleType));
		} else if ("case".equals(ruleType)) {
			String beanClass = root.attributeValue("beanClass");
			if (StringUtil.isEmpty(beanClass)) {
				log.error("�ж�����Ϊ��");
				throw new Exception("�ж�����Ϊ��");
			}
			Class clazz = Class.forName(beanClass);
			if(isInterface(clazz,"IExtCaseDispatch")){
				String initArg = root.attributeValue("initArg");
				IExtCaseDispatch cc = (IExtCaseDispatch) clazz.newInstance();
				cc.init(initArg);
				rule.setBeanClass(cc);
			}else{
				ICaseDispatch cc = (ICaseDispatch) clazz.newInstance();
				rule.setBeanClass(cc);
			}
			
			rule.setNodeMap((Map) createDispatchNode(dispatchNodes, ruleType));
		} else {
			log.error("��֧�ֵ��ȹ���{}", ruleType);
			throw new Exception("��֧�ֵ��ȹ���" + ruleType);
		}
		rule.setRuleType(ruleType);
		return rule;
	}

	private  Object createDispatchNode(List<Element> nodes, String type)
			throws Exception {
		List<DispatchNode> nodeList = null;
		Map<String, DispatchNode> nodeMap = null;
		String txCode = null;
		String beanName = null;
		String resultParametPath=null;
		String isReturn=null;
		DispatchNode dnode = null;
		for (Element node : nodes) {
			dnode = new DispatchNode();
			txCode = node.attributeValue("txCode");
			beanName = node.attributeValue("beanName");
			resultParametPath=node.attributeValue("resultParametPath");
			isReturn=node.attributeValue("isReturn");
			if (!StringUtil.isEmpty(txCode)) {
				dnode.setTxCode(txCode);
				dnode.setExeType("0");
			} else if (!StringUtil.isEmpty(beanName)) {
				Class clazz = Class.forName(beanName);
				dnode.setBeanClass(clazz);
				dnode.setExeType("1");
			} else {
				dnode.setExeType("2");
				Element root = node.element("dispatchRule");
				if(root!=null){
					dnode.setRule(creatDispatchRule(root));
				}
			}
			if(!StringUtil.isEmpty(resultParametPath)){
				dnode.setResultParametPath(resultParametPath);
			}
			if("true".equals(isReturn)){
				dnode.setReturn(true);
			}else{
				dnode.setReturn(false);
			}
			if ("case".equals(type)) {
				if (nodeMap == null) {
					nodeMap = new TreeMap<String, DispatchNode>();
				}
				String caseValue = node.attributeValue("caseValue");
				if (StringUtil.isEmpty(caseValue)) {
					log.error("�ж�����ж�ֵΪ��");
					throw new Exception("�ж�����ж�ֵΪ��");
				}
				nodeMap.put(caseValue, dnode);
			} else {
				if (nodeList == null) {
					nodeList = new ArrayList<DispatchNode>();
				}
				String orderStep = node.attributeValue("orderStep");
				if (StringUtil.isEmpty(orderStep)) {
					log.error("�����㲽��ֵΪ��");
					throw new Exception("�����㲽��ֵΪ��");
				}
				dnode.setOrderStep(Integer.valueOf(orderStep));
				nodeList.add(dnode);
			}
		}
		if ("case".equals(type)) {
			return nodeMap;
		} else {
			Comparator<DispatchNode> comparator =new ComparatorNode();
			Collections.sort(nodeList,comparator);
//			for(DispatchNode n:nodeList){
//				log.info(String.valueOf(n.getOrderStep()));
//			}
			return nodeList;
		}
	}
	
	/**
	 * @��������:isInterface
	 * @��������:�ж϶���oʵ�ֵ����нӿ����Ƿ���szInterface  
	 * @�����뷵��˵��:
	 * 		@param c
	 * 		@param szInterface
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isInterface(Class c, String szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getSimpleName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getSimpleName().equals(szInterface)) {
						return true;
					} else if (isInterface(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return isInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}

	public static DispatchRule getDispatchRule(String txCode) {
		return dispatchRuleMap.get(txCode);
	}
	
	/**
	 * @��Ŀ���ƣ�ytec-mdm-ecif 
	 * @�����ƣ�ComparatorNode
	 * @������������Ƚ�
	 * @��������:
	 * @�����ˣ�wangzy1@yuchengtech.com
	 * @����ʱ�䣺2014-5-29 ����6:12:59   
	 * @�޸��ˣ�wangzy1@yuchengtech.com
	 * @�޸�ʱ�䣺2014-5-29 ����6:12:59
	 * @�޸ı�ע��
	 * @�޸�����		�޸���Ա		�޸�ԭ��
	 * --------    	--------	----------------------------------------
	 * @version 1.0.0
	 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
	 * 
	 */
	private class ComparatorNode implements Comparator<DispatchNode> {
		public int compare(DispatchNode arg0, DispatchNode arg1) {
			return arg0.getOrderStep()-arg1.getOrderStep();
		}

	}

}
