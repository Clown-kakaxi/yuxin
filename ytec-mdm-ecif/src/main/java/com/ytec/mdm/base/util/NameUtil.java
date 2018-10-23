/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����NameUtil.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:30:40
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�NameUtil
 * @��������java���ư�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:30:41   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:30:41
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class NameUtil {
	
	private static final String METHOD_S ="s";
	
	/**
	 * @��������:getNameToJava
	 * @��������:�����Ƹĳ�java�淶
	 * @�����뷵��˵��:
	 * 		@param name
	 * 		@return
	 * @�㷨����:
	 */
	public static String getNameToJava(String name){
		name = name.toUpperCase();
		String[] codeStrs = name.split("_");
		String str = new String();
		// �Ƿ��С�_��
		if (codeStrs.length > 1) {
			for (int i = 1; i < codeStrs.length; i++) {
				// �������е��»����޳��������ӵڶ������ʿ�ʼ������ĸ��д
				str = str + codeStrs[i].substring(0, 1)
						+ codeStrs[i].substring(1).toLowerCase();
			}
		} else {
			str = name.substring(0, 1)
					+ name.substring(1).toLowerCase();
		}
		return str;
	}
	
	/**
	 * @��������:getColumToJava
	 * @��������:���ֶ����Ƹĳ�java�淶
	 * @�����뷵��˵��:
	 * 		@param name
	 * 		@return
	 * @�㷨����:
	 */
	public static String getColumToJava(String name){
		if(name==null){
			return null;
		}
		name = name.toLowerCase();
		String[] codeStrs = name.split("_");
		// �Ƿ��С�_��
		if (codeStrs.length > 1) {
			String str=codeStrs[0];
			for (int i = 1; i < codeStrs.length; i++) {
				// �������е��»����޳��������ӵڶ������ʿ�ʼ������ĸ��д
				str = str + codeStrs[i].substring(0, 1).toUpperCase()
						+ codeStrs[i].substring(1);
			}
			return str;
		} else {
			return name;
		}
	}
	
	
	/**
	 * @��������:getJavaToColum
	 * @��������:��JAVA����ת���ֶδ�д
	 * @�����뷵��˵��:
	 * 		@param name
	 * 		@return
	 * @�㷨����:
	 */
	public static String getJavaToColum(String name){
		
		if(name==null){
			return null;
		}
		StringBuffer str = new StringBuffer();
		char[] chars=name.toCharArray();
		for(char c:chars){
			if(Character.isUpperCase(c)){
				str.append('_');
			}
			str.append(c);
		}
		return str.toString().toUpperCase();
	}
	
	
	public static String toSetMethod(String name){
		return NameUtil.METHOD_S + name.substring(1); 
	}
}
