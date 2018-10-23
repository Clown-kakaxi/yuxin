/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.bs
 * @�ļ�����ServiceEntityMgr.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:03:49
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ServiceEntityMgr
 * @��������ʵ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:03:49   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:03:49
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ServiceEntityMgr {
	private static Logger log = LoggerFactory
			.getLogger(ServiceEntityMgr.class);
	/**
	 * @��������:txEntityMap
	 * @��������:ʵ��������ʵ�����MAP
	 * @since 1.0.0
	 */
	private static Map<String,String> txEntityMap=new HashMap<String,String>();
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@throws IOException
	 * @�㷨����:
	 */
	public static void init() throws IOException{
		txEntityMap.clear();
		String[] packages=BusinessCfg.getStringArray("packageName");
		txEntityMap.putAll(ClassesScanner.scanEntityByPackages(packages));
		log.info("����{}��ҵ��ʵ��",txEntityMap.size());
	}
	/**
	 * @��������:getEntityByName
	 * @��������:��ȡʵ����
	 * @�����뷵��˵��:
	 * 		@param className
	 * 		@return
	 * @�㷨����:
	 */
	public static Class getEntityByName(String className){
		Class clazz=null;
		String fullClassName=null;
		if((fullClassName=txEntityMap.get(className))!=null){
			try{
				clazz=Class.forName(fullClassName);
			}catch(Exception e){
				log.error("��ȡҵ��ʵ�����",e);
			}
		}
		return clazz;
	}

}
