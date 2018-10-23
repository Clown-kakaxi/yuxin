/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：BusinessCfg.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:52:21
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server.common;

import java.nio.charset.Charset;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.MdmConstants;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：BusinessCfg
 * @类描述：业务配置存贮
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:52:22   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:52:22
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class BusinessCfg {
	private static Logger log = LoggerFactory.getLogger(BusinessCfg.class);
	/**
	 * @属性名称:businessCfg
	 * @属性描述:业务配置参数对象
	 * @since 1.0.0
	 */
	private static HashMap<String, String> businessCfg=new HashMap<String, String>();
	/**
	 * @属性名称:chooseSaveHistoryObjects
	 * @属性描述:日志保存对象
	 * @since 1.0.0
	 */
	private static HashMap<String, Boolean> chooseSaveHistoryObjects=new HashMap<String, Boolean>();
	
	/**
	 * @函数名称:putBusinessCfg
	 * @函数描述:装载配置
	 * @参数与返回说明:
	 * 		@param k 键值
	 * 		@param v 数据
	 * @算法描述:
	 */
	public static void putBusinessCfg(String k,String v){
		businessCfg.put(k, v);
	}
	/**
	 * @函数名称:clearBusinessCfg
	 * @函数描述:清除配置
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public static void clearBusinessCfg(){
		businessCfg.clear();
	}
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@throws Exception
	 * @算法描述:
	 */
	public static void init() throws Exception {
			/**日志对象加载***/
			if(getBoolean("chooseSaveHistory")){
				String[] chooseSaveHistoryObj=getStringArray("chooseSaveHistoryObject");
				chooseSaveHistoryObjects.clear();
				if(chooseSaveHistoryObj!=null){
					for(String v:chooseSaveHistoryObj){
						if(v!=null&&!v.isEmpty()){
							chooseSaveHistoryObjects.put(v, true);
						}
					}
				}
			}
			/**默认字符集设置***/
			if(MdmConstants.TX_XML_ENCODING==null){
				log.info("运行时应用字符集为空,设置为平台字符集{}",Charset.defaultCharset().name());
				MdmConstants.TX_XML_ENCODING=Charset.defaultCharset().name();
			}
	}

	/**
	 * @函数名称:getString
	 * @函数描述:获取字符参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static String getString(String key) {
		String value =(String)businessCfg.get(key);
		return value;
	}
	/**
	 * @函数名称:getStringArray
	 * @函数描述:获取字符数组参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static String[] getStringArray(String key) {
		String value =(String)businessCfg.get(key);
		String[] valueArray=null;
		if(value!=null){
			valueArray=value.split("\\,");
		}
		return valueArray;
	}
	/**
	 * @函数名称:getInt
	 * @函数描述:获取int参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static int getInt(String key) {
		int value = 0;
		try{
			String v =(String)businessCfg.get(key);
			if(v!=null&&!v.isEmpty()){
				value=Integer.parseInt(v);
			}
		}catch(Exception e){
			log.error("读取配置文件 int失败", e);
		}

		return value;
	}
	
	/**
	 * @函数名称:getBoolean
	 * @函数描述:获取boolean参数
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public static boolean getBoolean(String key) {
		boolean value =false;
		try{
			String v =(String)businessCfg.get(key);
			if(v!=null&&!v.isEmpty()){
				value=Boolean.parseBoolean(v);
			}
		}catch(Exception e){
			log.error("读取配置文件 boolean失败", e);
		}
		return value;
	}
	
	/***
	 * 是否保留历史
	 * @param m 模型名称
	 * @return
	 */
	public static boolean isSaveHisObj(String m){
		if(chooseSaveHistoryObjects.get(m)!=null){
			return true;
		}else{
			return false;
		}
	}
}
