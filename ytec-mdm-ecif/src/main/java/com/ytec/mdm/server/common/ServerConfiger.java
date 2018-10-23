/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����ServerConfiger.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:54:02
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ServerConfiger
 * @����������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:54:03   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:54:03
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ServerConfiger {
	private static final String defautCfg = "serverConfig.xml";
	private static Logger log = LoggerFactory.getLogger(ServerConfiger.class);
	/**
	 * @��������:cfgPath
	 * @��������:�����ļ�·��
	 * @since 1.0.0
	 */
	public static String cfgPath;
	/**
	 * @��������:serverName
	 * @��������:��������
	 * @since 1.0.0
	 */
	public static String serverName;
	/**
	 * @��������:serverImpl
	 * @��������:�ӿڷ���ӿ�ʵ����
	 * @since 1.0.0
	 */
	public static String serverImpl;
	/**
	 * @��������:adapter
	 * @��������:�ӿ�ִ������������
	 * @since 1.0.0
	 */
	public static String adapter;
	/**
	 * @��������:serverArg
	 * @��������:�������
	 * @since 1.0.0
	 */
	public static Map<String, String> serverArg = new HashMap<String, String>();
	/**
	 * @��������:serverRun
	 * @��������:�������Ƿ�����
	 * @since 1.0.0
	 */
	public static boolean serverRun = true;
	/**
	 * @��������:clientsArg
	 * @��������:�ͻ��˲���
	 * @since 1.0.0
	 */
	private static Map<String, Map> clientsArg = new HashMap<String, Map>();

	
	public static Node redundances;
	
	/**
	 * �������б�
	 */
	public static List<String> redundanceClassList = new ArrayList<String>();
		
	/**
	 * @��������:redundanceClassPropertyMap
	 * @��������:�������,�������Զ�Ӧֵ
	 * @since 1.0.0
	 */
	public static Map<String, List> redundanceClassPropertyMap = new HashMap<String, List>();
	
	/**
	 * @��������:redundanceClassPropertycondMap
	 * @��������:�������,�������ԡ�������Ӧֵ
	 * @since 1.0.0
	 */
	public static Map<String, Map> redundanceClassPropertycondMap = new HashMap<String, Map>();
	
	public static Node consistences;
	//������һ�����ֶζ�Ӧ��map��ϵ
	public static Map<String, List> transConsistenceMap = new HashMap<String, List>();

	/**
	 * @��������:initServer
	 * @��������:�����ʼ��
	 * @�����뷵��˵��:
	 * 		@param cfgPath_   �����ļ�·��
	 * 		@param serverName_  ����ӿ�����
	 * 		@return
	 * @�㷨����:
	 */
	public static boolean initServer(String cfgPath_, String serverName_) {
		Document serverCfg = null;
		SAXReader saxReader = new SAXReader();
		String defaultServer=null;
		/**
		 * ���·��ĩβû��"/"������ϡ�/��
		 */
		if (!cfgPath_.endsWith("/")) {
			cfgPath_ = cfgPath_ + "/";
		}
		cfgPath = cfgPath_;
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			/**
			 * ����ļ������ڣ��͵��������Դ������
			 */
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if(is!=null){
				try {
					/**
					 * ���������ļ�
					 */
					serverCfg=saxReader.read(is);
					saxReader=null;
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
				/**
				 * ���������ļ�
				 */
				serverCfg = saxReader.read(cfgFile);
				saxReader=null;
				cfgFile=null;
			} catch (DocumentException e) {
				log.error("���������ļ�ʧ��:", e);
				return false;
			}
		}
		if(StringUtil.isEmpty(serverName_)){
			/**
			 *  �ӿ�
			 */
			Element servers=(Element)serverCfg.selectSingleNode("//server-config/interface/servers");
			log.info("��XML�����еõ�����ӿ�[{}]", servers.attributeValue("default"));
			if(servers!=null){
				/**
				 * ����ӿ�����Ϊ�գ�����Ĭ�ϵĽӿ�
				 */
				defaultServer=servers.attributeValue("default");
				if(StringUtil.isEmpty(defaultServer)){
					log.error("�����ӿ���Ϊ��");
					return false;
				}else{
					serverName_=defaultServer;
					log.info("����Ĭ�ϵĽӿ�[{}]",defaultServer);
				}
			}else{
				log.error("����ӿ����ô���");
				return false;
			}
		}
		
		List<Element> serverList = serverCfg
				.selectNodes("//server-config/interface/servers/server");
		if (serverList == null || serverList.isEmpty()) {
			log.error("����ӿ�����Ϊ��");
			return false;
		}
		/**
		 * ����˵Ľӿ�����
		 */
		serverArg.clear();
		for (Element server : serverList) {
			String sname = server.attributeValue("name");
			if(StringUtil.isEmpty(sname)){
				/**
				 * �ӿ�����Ϊ��
				 */
				continue;
			}
			
			if((sname.endsWith("*")&&serverName_.startsWith(sname.substring(0,sname.length()-1)))){
				/**
				 * ��������Ϊģ��ƥ��.
				 */
				if(sname.length()-1<serverName_.length()){
					String subname=serverName_.substring(sname.length()-1); 
					List<Element> subservers = server.elements("server");
					/***
					 * �����ӷ�������
					 */
					boolean hasSub=false;
					for(Element subserver:subservers){
						if(subname.equals(subserver.attributeValue("subname"))){
							List<Element> serverArgs = subserver.elements("server_arg");
							for (Element arg : serverArgs) {
								serverArg.put(arg.attributeValue("key"), arg.getTextTrim());
							}
							hasSub=true;
							break;
						}
					}
					if(!hasSub){
						continue;
					}
				}else{
					continue;
				}
				
			}else if(serverName_.equals(sname)){
				
			}else{
				continue;
			}
			/**
			 * ��������
			 */
			serverName = serverName_;
			/**
			 * �ӿڷ���ӿ�ʵ����
			 */
			serverImpl = server.attributeValue("serverImpl");
			if (StringUtil.isEmpty(serverImpl)) {
				log.error("�ӿ�ʵ��������Ϊ��");
				return false;
			}
			/**
			 * �ӿ�ִ������������
			 */
			adapter = server.attributeValue("adapter");
			if (StringUtil.isEmpty(adapter)) {
				log.error("ִ����������������Ϊ��");
				return false;
			}
			List<Element> serverArgs = server.elements("server_arg");
			for (Element arg : serverArgs) {
				if(serverArg.containsKey(arg.attributeValue("key"))){
					log.warn("�ӿڲ���[{}]�����ظ�",arg.attributeValue("key"));
				}else{
					serverArg.put(arg.attributeValue("key"), arg.getTextTrim());
				}
			}
			break;
		}
		if(StringUtil.isEmpty(serverName)){
			log.error("δƥ�䵽�����ӿ�[{}]",serverName_);
			return false;
		}
		/**
		 * �ͻ���
		 */
		clientsArg.clear();
		List<Element> clientList = serverCfg
				.selectNodes("//server-config/interface/clients/client");
		if (clientList != null && !clientList.isEmpty()) {
			for (Element client : clientList) {
				String cname = client.attributeValue("name");
				String destSysNo = client.attributeValue("destSysNo");
				if (cname != null && !cname.isEmpty()) {
					List<Element> clientArgs = client.elements("client_arg");
					Map clientsArgMap = new HashMap();
					for (Element arg : clientArgs) {
						clientsArgMap.put(arg.attributeValue("key"),
								arg.getTextTrim());
					}
					clientsArg.put(cname+"_"+destSysNo, clientsArgMap);
				}
			}
		}
		/**
		 * ��������
		 */
		List<Element> constantList = serverCfg
				.selectNodes("//server-config/constants/constant");
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
							log.debug("����{}��ֵΪ{}", fnname, valu);

						}
					} catch (Exception e) {
						log.error("�����������ô���", e);
						return false;
					}

				}
			}
		}
		
		/***********��ƷLicense����*****************/
		if (!checkLicense()) {
			log.error("��ƷLicenseʧЧ");
			return false;
		}
		// ҵ������
		Element businessCfgE = (Element) serverCfg
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
		List<Element> extBusinessList = serverCfg
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
				Map attributeMap = new TreeMap();
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
		List<Element> initializations = serverCfg
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
				log.error("��ʼ��{}����", className);
				log.error("��ʼ����������ô���", e);
				return false;
			}
		}
		
		// ���ദ��
		redundances = serverCfg.selectSingleNode("//server-config/redundances");
		
		List<Element> redundanceAllClassList = serverCfg
				.selectNodes("//server-config/redundances/redundance/redundance-class");
		for (Element redundance : redundanceAllClassList) {
			String className = redundance.attributeValue("class");
			if (className == null || className.isEmpty()) {
				continue;
			}
			String property = redundance.attributeValue("property");
			if (property == null || property.isEmpty()) {
				log.error("���ദ������{}Ϊ��", property);
				return false;
			}

			String condition = redundance.attributeValue("condition");

			redundanceClassList.add(className);
			
			List classPropertyList = redundanceClassPropertyMap.get(className);
			if(classPropertyList==null){
				classPropertyList = new ArrayList();		
			}
			classPropertyList.add(property);
			redundanceClassPropertyMap.put(className, classPropertyList);
		}
		
		// ���ദ��
		consistences = serverCfg.selectSingleNode("//server-config/consistences");
		
		List<Element> consistenceList = consistences.selectNodes("consistence");
		for(Element e:consistenceList){
			String name = e.attributeValue("name");
			String transtions = e.attributeValue("transtions");
			if(transtions!=null){
				String[] transtionArray = transtions.split(",");
				for(String trans: transtionArray){
					List list = transConsistenceMap.get(trans);
					if(list==null){
						list = new ArrayList();
					}
					if(!list.contains(name)){
						list.add(name);
					}
					transConsistenceMap.put(trans, list);
				}
			}
		}
		
		
		serverCfg=null;
		return true;
	}

	/**
	 * @��������:getClientArg
	 * @��������:��ȡ�ͻ��˲���
	 * @�����뷵��˵��:
	 * 		@param clientName
	 * 		@param destSysNo
	 * 		@return
	 * @�㷨����:
	 */
	public static Map getClientArg(String clientName,String destSysNo) {
		return clientsArg.get(clientName+"_"+destSysNo);
	}

	/**
	 * @��������:getIntArg
	 * @��������:��ȡint����
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static int getIntArg(String key) {
		int value = 0;
		try {
			String v = (String) serverArg.get(key);
			if (v != null && !v.isEmpty()) {
				value = Integer.parseInt(v);
			}
		} catch (Exception e) {
			log.error("��ȡ�����ļ� intʧ��", e);
		}

		return value;
	}

	/**
	 * @��������:getStringArg
	 * @��������:��ȡ�ַ�������
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static String getStringArg(String key) {
		String value = (String) serverArg.get(key);
		return value;
	}

	/**
	 * @��������:getBooleanArg
	 * @��������:��ȡboolean����
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static boolean getBooleanArg(String key) {
		boolean value = false;
		try {
			String v = (String) serverArg.get(key);
			if (v != null && !v.isEmpty()) {
				value = Boolean.parseBoolean(v);
			}
		} catch (Exception e) {
			log.error("��ȡ�����ļ� booleanʧ��", e);
		}
		return value;
	}

	/**
	 * @��������:checkLicense
	 * @��������:License ��֤
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	private static boolean checkLicense() {
		EcifLisenceManager ecifLisenceManager = EcifLisenceManager.getInstance();
		try {
			ecifLisenceManager.setLisenceModel(MdmConstants.lisenceModel);
			ecifLisenceManager.setLicenseFile(MdmConstants.licenseFile);
			ecifLisenceManager.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("��ʼLicense������ʧ��",e);
			return false;
		}
		if(ecifLisenceManager.verified){
			return true;
	    }else{
	    	log.error(ecifLisenceManager.LISENCE_ERROR);
	    	return false;
		}
	}
}
