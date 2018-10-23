/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����GetKeyNameUtil.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:13:53
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util;
import java.util.HashMap;
import java.util.Map;
import com.ytec.mdm.base.dao.JPAAnnotationMetadataUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetKeyNameUtil
 * @�������� ����������ƹ���
 * @��������:���POJO���󣬱������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:14:02
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:14:02
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class GetKeyNameUtil {

	/**
	 * @��������:entityKeyMap
	 * @��������:ʵ���������Ӧ��ϵ��֧�ֵ�����������������֧�֣�ӦΪJPA�ĸ���������Ҫ�½�XXXid�Ķ��󣬲������ӣ���Ҫ�ͻ���
	 * @since 1.0.0
	 */
	private Map<String, String> entityKeyMap = new HashMap<String, String>();
	/**
	 * @��������:metadataUtil
	 * @��������:���ݿ�ʵ�������
	 * @since 1.0.0
	 */
	private JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
	
	private static GetKeyNameUtil getKeyNameUtil=new GetKeyNameUtil();
	
	public static GetKeyNameUtil getInstance(){
		return getKeyNameUtil;
	}

	/**
	 * @��������:getKeyName
	 * @��������:���POJO���󣬱������
	 * @�����뷵��˵��:
	 * @param obj
	 * @return
	 * @�㷨����:
	 */
	public String getKeyName(Object obj) {
		String keyName = null;
		String objName = obj.getClass().getSimpleName();
		if ((keyName = entityKeyMap.get(objName)) != null) {
			return keyName;
		} else {
			keyName=metadataUtil.getIdPropertyName(obj);
			if(keyName!=null){
				entityKeyMap.put(objName, keyName);
			}
			return keyName;
		}
	}

}
