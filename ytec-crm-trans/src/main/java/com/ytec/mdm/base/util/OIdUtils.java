/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：OIdUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:25:11
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：OIdUtils
 * @类描述：主键生成
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:25:16   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:25:16
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
	 * @函数名称:setCustIdValue
	 * @函数描述:设置CUST_ID
	 * @参数与返回说明:
	 * 		@param obj
	 * 		@param value
	 * 		@throws Exception
	 * @算法描述:
	 */
	public static void setCustIdValue(final Object obj,final String value) throws Exception{
		Field field = ReflectionUtils.getAccessibleField(obj, MdmConstants.CUSTID);
		if (field != null) {
			Class typeClass = field.getType();
			if(typeClass.equals(Long.class)||typeClass.equals(long.class)){
				//LONG 类型
				field.set(obj, Long.parseLong(value));
			}else if(typeClass.equals(String.class)){
				//字符类型
				field.set(obj, value);
			}else if(typeClass.equals(BigInteger.class)){
				//BigInteger 类型
				field.set(obj, BigInteger.valueOf(Long.parseLong(value)));
			}else if(typeClass.equals(BigDecimal.class)){
				//BigDecimal 类型
				field.set(obj, BigDecimal.valueOf(Long.parseLong(value)));
			}else{
				//其他类型
				field.set(obj, value);
			}
		}
	}
	
	/**
	 * @函数名称:setIdValueByName
	 * @函数描述:通过主键名称，设置主键值
	 * @参数与返回说明:
	 * 		@param obj
	 * 		@param fieldName
	 * 		@param value
	 * 		@throws Exception
	 * @算法描述:
	 */
	public static void setIdValueByName(final Object obj,final String fieldName ,final String value) throws Exception{
		Field field = ReflectionUtils.getAccessibleField(obj, fieldName);
		if (field != null) {
			Class typeClass = field.getType();
			if(typeClass.equals(Long.class)||typeClass.equals(long.class)){
				//LONG 类型
				field.set(obj, Long.parseLong(value));
			}else if(typeClass.equals(String.class)){
				//字符类型
				field.set(obj, value);
			}else if(typeClass.equals(BigInteger.class)){
				//BigInteger 类型
				field.set(obj, BigInteger.valueOf(Long.parseLong(value)));
			}else if(typeClass.equals(BigDecimal.class)){
				//BigDecimal 类型
				field.set(obj, BigDecimal.valueOf(Long.parseLong(value)));
			}else{
				//其他类型
				field.set(obj, value);
			}
		}
	}
	
}
