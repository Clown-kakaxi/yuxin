/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����BusinessCfg.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:52:21
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import java.nio.charset.Charset;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.MdmConstants;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�BusinessCfg
 * @��������ҵ�����ô���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:52:22   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:52:22
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class BusinessCfg {
	private static Logger log = LoggerFactory.getLogger(BusinessCfg.class);
	/**
	 * @��������:businessCfg
	 * @��������:ҵ�����ò�������
	 * @since 1.0.0
	 */
	private static HashMap<String, String> businessCfg=new HashMap<String, String>();
	/**
	 * @��������:chooseSaveHistoryObjects
	 * @��������:��־�������
	 * @since 1.0.0
	 */
	private static HashMap<String, Boolean> chooseSaveHistoryObjects=new HashMap<String, Boolean>();
	
	/**
	 * @��������:putBusinessCfg
	 * @��������:װ������
	 * @�����뷵��˵��:
	 * 		@param k ��ֵ
	 * 		@param v ����
	 * @�㷨����:
	 */
	public static void putBusinessCfg(String k,String v){
		businessCfg.put(k, v);
	}
	/**
	 * @��������:clearBusinessCfg
	 * @��������:�������
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public static void clearBusinessCfg(){
		businessCfg.clear();
	}
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static void init() throws Exception {
			/**��־�������***/
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
			/**Ĭ���ַ�������***/
			if(MdmConstants.TX_XML_ENCODING==null){
				log.info("����ʱӦ���ַ���Ϊ��,����Ϊƽ̨�ַ���{}",Charset.defaultCharset().name());
				MdmConstants.TX_XML_ENCODING=Charset.defaultCharset().name();
			}
	}

	/**
	 * @��������:getString
	 * @��������:��ȡ�ַ�����
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static String getString(String key) {
		String value =(String)businessCfg.get(key);
		return value;
	}
	/**
	 * @��������:getStringArray
	 * @��������:��ȡ�ַ��������
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
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
	 * @��������:getInt
	 * @��������:��ȡint����
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static int getInt(String key) {
		int value = 0;
		try{
			String v =(String)businessCfg.get(key);
			if(v!=null&&!v.isEmpty()){
				value=Integer.parseInt(v);
			}
		}catch(Exception e){
			log.error("��ȡ�����ļ� intʧ��", e);
		}

		return value;
	}
	
	/**
	 * @��������:getBoolean
	 * @��������:��ȡboolean����
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
	 */
	public static boolean getBoolean(String key) {
		boolean value =false;
		try{
			String v =(String)businessCfg.get(key);
			if(v!=null&&!v.isEmpty()){
				value=Boolean.parseBoolean(v);
			}
		}catch(Exception e){
			log.error("��ȡ�����ļ� booleanʧ��", e);
		}
		return value;
	}
	
	/***
	 * �Ƿ�����ʷ
	 * @param m ģ������
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
