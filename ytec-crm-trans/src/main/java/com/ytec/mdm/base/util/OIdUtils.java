/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����OIdUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:25:11
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�OIdUtils
 * @����������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:25:16   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:25:16
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class OIdUtils {
	private static final AtomicInteger LAST_VALUE=new AtomicInteger(0);
	private static final int MAX = 999999;
	/**
	 * Gets the idof string.
	 * 
	 * @return the idof string
	 */
	public static String getIdofString(){
		long time = System.currentTimeMillis();
		String str = String.valueOf(time);
		String a = str.substring(str.length() - 11);
		LAST_VALUE.compareAndSet(MAX, 0);
		return String.format("%d%s%06d", MdmConstants.SYSTEMJVMID,a,LAST_VALUE.incrementAndGet());
	}

	/**
	 * Gets the id of long.
	 * 
	 * @return the id of long
	 * @throws NumberFormatException
	 *             the number format exception
	 */
	public static Long getIdOfLong() throws NumberFormatException{
		return Long.parseLong(getIdofString());
	}
	

	/**
	 * @��������:setCustIdValue
	 * @��������:����CUST_ID
	 * @�����뷵��˵��:
	 * 		@param obj
	 * 		@param value
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static void setCustIdValue(final Object obj,final String value) throws Exception{
		Field field = ReflectionUtils.getAccessibleField(obj, MdmConstants.CUSTID);
		if (field != null) {
			Class typeClass = field.getType();
			if(typeClass.equals(Long.class)||typeClass.equals(long.class)){
				//LONG ����
				field.set(obj, Long.parseLong(value));
			}else if(typeClass.equals(String.class)){
				//�ַ�����
				field.set(obj, value);
			}else if(typeClass.equals(BigInteger.class)){
				//BigInteger ����
				field.set(obj, BigInteger.valueOf(Long.parseLong(value)));
			}else if(typeClass.equals(BigDecimal.class)){
				//BigDecimal ����
				field.set(obj, BigDecimal.valueOf(Long.parseLong(value)));
			}else{
				//��������
				field.set(obj, value);
			}
		}
	}
	
	/**
	 * @��������:setIdValueByName
	 * @��������:ͨ���������ƣ���������ֵ
	 * @�����뷵��˵��:
	 * 		@param obj
	 * 		@param fieldName
	 * 		@param value
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static void setIdValueByName(final Object obj,final String fieldName ,final String value) throws Exception{
		Field field = ReflectionUtils.getAccessibleField(obj, fieldName);
		if (field != null) {
			Class typeClass = field.getType();
			if(typeClass.equals(Long.class)||typeClass.equals(long.class)){
				//LONG ����
				field.set(obj, Long.parseLong(value));
			}else if(typeClass.equals(String.class)){
				//�ַ�����
				field.set(obj, value);
			}else if(typeClass.equals(BigInteger.class)){
				//BigInteger ����
				field.set(obj, BigInteger.valueOf(Long.parseLong(value)));
			}else if(typeClass.equals(BigDecimal.class)){
				//BigDecimal ����
				field.set(obj, BigDecimal.valueOf(Long.parseLong(value)));
			}else{
				//��������
				field.set(obj, value);
			}
		}
	}
	
}
