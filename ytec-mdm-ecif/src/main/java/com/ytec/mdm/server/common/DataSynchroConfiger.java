/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����DataSynchroConfiger.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:52:42
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DataSynchroConfiger
 * @������������ͬ�����ô���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:52:43   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:52:43
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DataSynchroConfiger {
	private static Logger log = LoggerFactory.getLogger(DataSynchroConfiger.class);
	private static final String defautCfg = "dataSynchroConfig.xml";
	public static String listenerImpl;
	public static Map listenerArg = new HashMap();
	private static Map<String, Map> clientsArg = new HashMap<String, Map>();
	public static boolean serverRun = true;
	public static boolean initDataSynchroServer(){
		// ��־��ʼ��
		Document synchroCfg = null;
		SAXReader saxReader = new SAXReader();
		String cfgPath="";
		try {
			cfgPath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			log.error("��ȡ·��ʧ��",e1);
			return false;
		};
		if (!cfgPath.endsWith("/")) {
			cfgPath = cfgPath + "/";
		}
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if(is!=null){
				try {
					synchroCfg=saxReader.read(is);
				} catch (DocumentException e) {
					log.error("���������ļ�ʧ��:", e);
					return false;
				}
			}else{
				log.error("�����ļ�{}/{}������", cfgPath, defautCfg);
				return false;
			}
		}else{
			try {
				synchroCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("���������ļ�ʧ��:", e);
				return false;
			}
		}
		
		Element listener =(Element)synchroCfg.selectSingleNode("//server-config/interface/listener");
		if (listener == null) {
			log.error("�����ӿ�����Ϊ��");
			return false;
		}
		// �����ӿ�
		listenerImpl = listener.attributeValue("listenerImpl");
		List<Element> serverArgs = listener.elements("listener_arg");
		for (Element arg : serverArgs) {
			listenerArg.put(arg.attributeValue("key"), arg.getTextTrim());
		}
		// �ͻ���
		List<Element> clientList = synchroCfg.selectNodes("//server-config/interface/clients/client");
		if (clientList != null && !clientList.isEmpty()) {
			for (Element client : clientList) {
				String cname = client.attributeValue("name");
				String destSysNoList = client.attributeValue("destSysNo");
				if(StringUtil.isEmpty(destSysNoList)){
					continue;
				}
				if (cname != null && !cname.isEmpty()) {
					List<Element> clientArgs = client.elements("client_arg");
					Map clientsArgMap = new HashMap();
					for (Element arg : clientArgs) {
						clientsArgMap.put(arg.attributeValue("key"),
								arg.getTextTrim());
					}
					String[] destSysNos=destSysNoList.split("\\,");
					for(String destSysNo:destSysNos){
						clientsArg.put(cname+"_"+destSysNo, clientsArgMap);
					}
				}
			}
		}
		// ��������
		List<Element> constantList = synchroCfg.selectNodes("//server-config/constants/constant");
		if (constantList != null && !constantList.isEmpty()) {
			for (Element constant : constantList) {
				String constantClass = constant.attributeValue("class");
				Object cc = null;
				Class clazz = null;
				if (constantClass != null && !constantClass.isEmpty()) {
					try {
						clazz = Class.forName(constantClass);
						cc = clazz.newInstance();
					} catch (Exception e) {
						log.error("�������ô���", e);
						return false;
					}
					List<Element> propertyArgs = constant.elements("property");
					try {
						log.info("��ʼ������", constantClass);
						for (Element arg : propertyArgs) {
							String fnname = arg.attributeValue("name");
							String valu = arg.getTextTrim();
							if (valu == null || valu.isEmpty()) {
								continue;
							}
							if (fnname == null || fnname.isEmpty()) {
								log.error("����{}����������Ϊ��", constantClass);
								return false;
							}
							Field field = clazz.getDeclaredField(fnname);
							if (field == null) {
								log.error("����{}����{}���ô���", constantClass, fnname);
								return false;
							}
							Class typeClass = field.getType();
							field.setAccessible(true);
							if (java.lang.String.class.equals(typeClass)) {
								field.set(cc, valu);
							} else if (boolean.class.equals(typeClass)) {
								field.set(cc, Boolean.valueOf(valu));
							} else if (Long.class.equals(typeClass)) {
								field.set(cc, Long.valueOf(valu));
							} else if (int.class.equals(typeClass)) {
								field.set(cc, Integer.parseInt(valu));
							} else {
								log.error("����{}����{}��֧�ָ�����", constantClass,
										fnname);
								return false;
							}
//							log.info("����{}��ֵΪ{}", fnname, valu);

						}
					} catch (Exception e) {
						log.error("�����������ô���", e);
						return false;
					}

				}
			}
		}
		// ҵ������
		Element businessCfgE = (Element) synchroCfg
				.selectSingleNode("//server-config/businessCfg");
		BusinessCfg.clearBusinessCfg();
		List<Element> propertyArgs = businessCfgE.elements("property");
		for (Element arg : propertyArgs) {
			BusinessCfg.putBusinessCfg(arg.attributeValue("name"),
					arg.getTextTrim());
		}
		
		try{
			SpringContextUtils.setApplicationContext();
		}catch(Exception e){
			log.error("��ʼ��Spring�����쳣",e);
			return false;
		}
		// ��չҵ������
		List<Element> extBusinessList = synchroCfg
				.selectNodes("//server-config/extBusinessCfg/extBusiness");
		for (Element extBusiness : extBusinessList) {
			String className = extBusiness.attributeValue("class");
			Object cc = null;
			Class clazz = null;
			if (className == null || className.isEmpty()) {
				continue;
			}
			String initFun = extBusiness.attributeValue("initFun");
			if (initFun == null || initFun.isEmpty()) {
				log.error("��չҵ������{}��ʼ����Ϊ��", className);
				return false;
			}
			try {
				clazz = Class.forName(className);
				cc = clazz.newInstance();
				if("true".equals(extBusiness.attributeValue("isSingleton"))){
					Method mt = clazz.getDeclaredMethod("getInstance");
					cc=mt.invoke(cc);
				}
			} catch (Exception e) {
				log.error("��չҵ�����������", e);
				return false;
			}
			String isload = extBusiness.attributeValue("isload");
			List<Element> epropertyArgs = extBusiness.elements("property");
			if ("true".equals(isload)) {
				for (Element arg : epropertyArgs) {
					BusinessCfg.putBusinessCfg(arg.attributeValue("name"),
							arg.getTextTrim());
				}
				try {
					Method mt = clazz.getDeclaredMethod(initFun);
					mt.invoke(cc);
				} catch (Exception e) {
					log.error("��չҵ����������ô���", e);
					return false;
				}
			} else {
				Map attributeMap = new HashMap();
				for (Element arg : epropertyArgs) {
					attributeMap.put(arg.attributeValue("name"),
							arg.getTextTrim());
				}
				try {
					Method mt = clazz.getDeclaredMethod(initFun, Map.class);
					mt.invoke(cc, attributeMap);
				} catch (Exception e) {
					log.error("��չҵ����������ô���", e);
					return false;
				}
			}

		}
		// ��ʼ������
		List<Element> initializations = synchroCfg
				.selectNodes("//server-config/initializations/initialization");
		for (Element initialization : initializations) {
			String className = initialization.attributeValue("class");
			Object cc = null;
			Class clazz = null;
			if (className == null || className.isEmpty()) {
				continue;
			}
			String initFun = initialization.attributeValue("initFun");
			if (initFun == null || initFun.isEmpty()) {
				log.error("��ʼ������{}��ʼ����Ϊ��", className);
				return false;
			}
			try {
				clazz = Class.forName(className);
				cc = clazz.newInstance();
				if("true".equals(initialization.attributeValue("isSingleton"))){
					Method mt = clazz.getDeclaredMethod("getInstance");
					cc=mt.invoke(cc);
				}
			} catch (Exception e) {
				log.error("��ʼ�����������", e);
				return false;
			}
			try {
				Method mt = clazz.getDeclaredMethod(initFun);
				mt.invoke(cc);
			} catch (Exception e) {
				log.error("��ʼ����������ô���", e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @��������:getClientArg
	 * @��������:��ȡ�ͻ���
	 * @�����뷵��˵��:
	 * 		@param clientName
	 * 		@param destSysNo
	 * 		@return
	 * @�㷨����:
	 */
	public static Map getClientArg(String clientName,String destSysNo) {
		return clientsArg.get(clientName+"_"+destSysNo);
	}
	

}
