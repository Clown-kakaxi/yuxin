/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.bs
 * @文件名：ServiceEntityMgr.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:03:49
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.bs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.ClassesScanner;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ServiceEntityMgr
 * @类描述：实体管理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:03:49   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:03:49
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ServiceEntityMgr {
	private static Logger log = LoggerFactory
			.getLogger(ServiceEntityMgr.class);
	/**
	 * @属性名称:txEntityMap
	 * @属性描述:实体名称与实体对象MAP
	 * @since 1.0.0
	 */
	private static Map<String,String> txEntityMap=new HashMap<String,String>();
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@throws IOException
	 * @算法描述:
	 */
	public static void init() throws IOException{
		txEntityMap.clear();
		String[] packages=BusinessCfg.getStringArray("packageName");
		txEntityMap.putAll(ClassesScanner.scanEntityByPackages(packages));
		log.info("加载{}个业务实体",txEntityMap.size());
	}
	/**
	 * @函数名称:getEntityByName
	 * @函数描述:获取实体类
	 * @参数与返回说明:
	 * 		@param className
	 * 		@return
	 * @算法描述:
	 */
	public static Class getEntityByName(String className){
		Class clazz=null;
		String fullClassName=null;
		if((fullClassName=txEntityMap.get(className))!=null){
			try{
				clazz=Class.forName(fullClassName);
			}catch(Exception e){
				log.error("获取业务实体错误",e);
			}
		}
		return clazz;
	}

}
