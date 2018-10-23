/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：ServerConfiger.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:54:02
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ServerConfiger
 * @类描述：服务配置
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:54:03   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:54:03
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ServerConfiger {
	private static final String defautCfg = "serverConfig.xml";
	private static Logger log = LoggerFactory.getLogger(ServerConfiger.class);
	/**
	 * @属性名称:cfgPath
	 * @属性描述:配置文件路径
	 * @since 1.0.0
	 */
	public static String cfgPath;
	/**
	 * @属性名称:serverName
	 * @属性描述:服务名称
	 * @since 1.0.0
	 */
	public static String serverName;
	/**
	 * @属性名称:serverImpl
	 * @属性描述:接口服务接口实现类
	 * @since 1.0.0
	 */
	public static String serverImpl;
	/**
	 * @属性名称:adapter
	 * @属性描述:接口执行适配器名称
	 * @since 1.0.0
	 */
	public static String adapter;
	/**
	 * @属性名称:serverArg
	 * @属性描述:服务参数
	 * @since 1.0.0
	 */
	public static Map<String, String> serverArg = new HashMap<String, String>();
	/**
	 * @属性名称:serverRun
	 * @属性描述:服务器是否运行
	 * @since 1.0.0
	 */
	public static boolean serverRun = true;
	/**
	 * @属性名称:clientsArg
	 * @属性描述:客户端参数
	 * @since 1.0.0
	 */
	private static Map<String, Map> clientsArg = new HashMap<String, Map>();

	
	public static Node redundances;
	
	/**
	 * 冗余类列表
	 */
	public static List<String> redundanceClassList = new ArrayList<String>();
		
	/**
	 * @属性名称:redundanceClassPropertyMap
	 * @属性描述:冗余参数,类与属性对应值
	 * @since 1.0.0
	 */
	public static Map<String, List> redundanceClassPropertyMap = new HashMap<String, List>();
	
	/**
	 * @属性名称:redundanceClassPropertycondMap
	 * @属性描述:冗余参数,类与属性、条件对应值
	 * @since 1.0.0
	 */
	public static Map<String, Map> redundanceClassPropertycondMap = new HashMap<String, Map>();
	
	public static Node consistences;
	//交易与一致性字段对应的map关系
	public static Map<String, List> transConsistenceMap = new HashMap<String, List>();

	/**
	 * @函数名称:initServer
	 * @函数描述:服务初始化
	 * @参数与返回说明:
	 * 		@param cfgPath_   配置文件路径
	 * 		@param serverName_  服务接口名称
	 * 		@return
	 * @算法描述:
	 */
	public static boolean initServer(String cfgPath_, String serverName_) {
		Document serverCfg = null;
		SAXReader saxReader = new SAXReader();
		String defaultServer=null;
		/**
		 * 如果路劲末尾没有"/"，则加上“/”
		 */
		if (!cfgPath_.endsWith("/")) {
			cfgPath_ = cfgPath_ + "/";
		}
		cfgPath = cfgPath_;
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			/**
			 * 如果文件不存在，就调用类的资源加载器
			 */
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if(is!=null){
				try {
					/**
					 * 解析配置文件
					 */
					serverCfg=saxReader.read(is);
					saxReader=null;
				} catch (DocumentException e) {
					log.error("解析配置文件失败:", e);
					return false;
				}
			}else{
				log.error("配置文件{}/{}不存在", cfgPath, defautCfg);
				return false;
			}
		}else{
			try {
				/**
				 * 解析配置文件
				 */
				serverCfg = saxReader.read(cfgFile);
				saxReader=null;
				cfgFile=null;
			} catch (DocumentException e) {
				log.error("解析配置文件失败:", e);
				return false;
			}
		}
		if(StringUtil.isEmpty(serverName_)){
			/**
			 *  接口
			 */
			Element servers=(Element)serverCfg.selectSingleNode("//server-config/interface/servers");
			log.info("从XML配置中得到服务接口[{}]", servers.attributeValue("default"));
			if(servers!=null){
				/**
				 * 如果接口名称为空，启动默认的接口
				 */
				defaultServer=servers.attributeValue("default");
				if(StringUtil.isEmpty(defaultServer)){
					log.error("启动接口名为空");
					return false;
				}else{
					serverName_=defaultServer;
					log.info("启动默认的接口[{}]",defaultServer);
				}
			}else{
				log.error("服务接口配置错误");
				return false;
			}
		}
		
		List<Element> serverList = serverCfg
				.selectNodes("//server-config/interface/servers/server");
		if (serverList == null || serverList.isEmpty()) {
			log.error("服务接口配置为空");
			return false;
		}
		/**
		 * 服务端的接口配置
		 */
		serverArg.clear();
		for (Element server : serverList) {
			String sname = server.attributeValue("name");
			if(StringUtil.isEmpty(sname)){
				/**
				 * 接口名称为空
				 */
				continue;
			}
			
			if((sname.endsWith("*")&&serverName_.startsWith(sname.substring(0,sname.length()-1)))){
				/**
				 * 服务名称为模糊匹配.
				 */
				if(sname.length()-1<serverName_.length()){
					String subname=serverName_.substring(sname.length()-1); 
					List<Element> subservers = server.elements("server");
					/***
					 * 查找子服务名称
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
			 * 服务名称
			 */
			serverName = serverName_;
			/**
			 * 接口服务接口实现类
			 */
			serverImpl = server.attributeValue("serverImpl");
			if (StringUtil.isEmpty(serverImpl)) {
				log.error("接口实现类配置为空");
				return false;
			}
			/**
			 * 接口执行适配器名称
			 */
			adapter = server.attributeValue("adapter");
			if (StringUtil.isEmpty(adapter)) {
				log.error("执行适配器名称配置为空");
				return false;
			}
			List<Element> serverArgs = server.elements("server_arg");
			for (Element arg : serverArgs) {
				if(serverArg.containsKey(arg.attributeValue("key"))){
					log.warn("接口参数[{}]配置重复",arg.attributeValue("key"));
				}else{
					serverArg.put(arg.attributeValue("key"), arg.getTextTrim());
				}
			}
			break;
		}
		if(StringUtil.isEmpty(serverName)){
			log.error("未匹配到启动接口[{}]",serverName_);
			return false;
		}
		/**
		 * 客户端
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
		 * 常量配置
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
						log.error("常量配置错误", e);
						return false;
					}
					List<Element> propertyArgs = constant.elements("property");
					try {
						log.info("初始化常量", constantClass);
						for (Element arg : propertyArgs) {
							String fnname = arg.attributeValue("name");
							String valu = arg.getTextTrim();
							if (valu == null || valu.isEmpty()) {
								continue;
							}
							if (fnname == null || fnname.isEmpty()) {
								log.error("常量{}属性名配置为空", constantClass);
								return false;
							}
							Field field = clazz.getDeclaredField(fnname);
							if (field == null) {
								log.error("常量{}属性{}配置错误", constantClass, fnname);
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
								log.error("常量{}属性{}不支持该类型", constantClass,
										fnname);
								return false;
							}
							log.debug("常量{}赋值为{}", fnname, valu);

						}
					} catch (Exception e) {
						log.error("常量属性配置错误", e);
						return false;
					}

				}
			}
		}
		
		/***********产品License管理*****************/
		if (!checkLicense()) {
			log.error("产品License失效");
			return false;
		}
		// 业务配置
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
			log.error("初始化Spring对象异常",e);
			return false;
		}
		// 扩展业务配置
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
				log.error("扩展业务配置{}初始方法为空", className);
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
				log.error("扩展业务配置类错误", e);
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
					log.error("扩展业务配置类调用错误", e);
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
					log.error("扩展业务配置类调用错误", e);
					return false;
				}
			}

		}
		// 初始化调用
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
				log.error("初始化调用{}初始方法为空", className);
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
				log.error("初始化调用类错误", e);
				return false;
			}
			try {
				Method mt = clazz.getDeclaredMethod(initFun);
				mt.invoke(cc);
			} catch (Exception e) {
				log.error("初始化{}错误", className);
				log.error("初始化调用类调用错误", e);
				return false;
			}
		}
		
		// 冗余处理
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
				log.error("冗余处理属性{}为空", property);
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
		
		// 冗余处理
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
	 * @函数名称:getClientArg
	 * @函数描述:获取客户端参数
	 * @参数与返回说明:
	 * 		@param clientName
	 * 		@param destSysNo
	 * 		@return
	 * @算法描述:
	 */
	public static Map getClientArg(String clientName,String destSysNo) {
		return clientsArg.get(clientName+"_"+destSysNo);
	}

	/**
	 * @函数名称:getIntArg
	 * @函数描述:获取int参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static int getIntArg(String key) {
		int value = 0;
		try {
			String v = (String) serverArg.get(key);
			if (v != null && !v.isEmpty()) {
				value = Integer.parseInt(v);
			}
		} catch (Exception e) {
			log.error("读取配置文件 int失败", e);
		}

		return value;
	}

	/**
	 * @函数名称:getStringArg
	 * @函数描述:获取字符串参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static String getStringArg(String key) {
		String value = (String) serverArg.get(key);
		return value;
	}

	/**
	 * @函数名称:getBooleanArg
	 * @函数描述:获取boolean参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static boolean getBooleanArg(String key) {
		boolean value = false;
		try {
			String v = (String) serverArg.get(key);
			if (v != null && !v.isEmpty()) {
				value = Boolean.parseBoolean(v);
			}
		} catch (Exception e) {
			log.error("读取配置文件 boolean失败", e);
		}
		return value;
	}

	/**
	 * @函数名称:checkLicense
	 * @函数描述:License 验证
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	private static boolean checkLicense() {
		EcifLisenceManager ecifLisenceManager = EcifLisenceManager.getInstance();
		try {
			ecifLisenceManager.setLisenceModel(MdmConstants.lisenceModel);
			ecifLisenceManager.setLicenseFile(MdmConstants.licenseFile);
			ecifLisenceManager.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("初始License管理器失败",e);
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
