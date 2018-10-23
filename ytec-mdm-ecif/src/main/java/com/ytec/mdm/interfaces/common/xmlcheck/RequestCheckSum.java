/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.xmlcheck
 * @�ļ�����RequestCheckSum.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:34:01
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.xmlcheck;

import com.ytec.mdm.base.util.crypt.EncryptUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�RequestCheckSum
 * @��������������һ����У��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:34:02   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:34:02
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class RequestCheckSum {
	/****
	 * �����ĵ�һ���Լ��
	 * rCheckSum:У����Ϣ
	 * xml:������
	 * @return boolean 
	 */
	public static boolean CheckRequestSum(String rCheckSum,String xml){
		if(rCheckSum==null|| rCheckSum.isEmpty() ||xml==null ||xml.isEmpty()){
			return true;
		}else{
			if(rCheckSum.equals(EncryptUtils.encrypt(xml,"MD5")))
				return true;
			else
				return false;
		}
	}
	
	/****
	 * ��Ӧ���ĵ�һ����У��ֵ
	 * xml:��Ӧ��
	 * @return String У����Ϣ
	 */
	public static String  CheckReponseSum(String xml){
		if(xml==null|| xml.isEmpty()){
			return "";
		}else{
			/***
			 * ��ȡMD5�ļ��ܹ���
			 * ����MD5����
			 */
			return EncryptUtils.encrypt(xml,"MD5");
		}
	}

}
