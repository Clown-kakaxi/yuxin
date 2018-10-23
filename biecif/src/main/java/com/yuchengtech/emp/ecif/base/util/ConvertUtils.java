/**
 * 
 */
package com.yuchengtech.emp.ecif.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class ConvertUtils {

	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 字符串 转换为 BigDecimal
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getDateStrToBigDecimal(String date) throws ParseException {
		format.setLenient(false);
		return Long.valueOf(format.parse(date).getTime());
	}
	
	/**
	 * 时间字符串转换为长整型
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getDateStrToLong(String date) throws ParseException {
		format.setLenient(false);
		return format.parse(date).getTime();
	}
	
	/**
	 * 时间字符串转换为date
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateStrToData(String date) throws ParseException {
		format.setLenient(false);
		return format.parse(date);
	}
	
	public static Date getDateStrToData2(String date) throws ParseException {
		format2.setLenient(false);
		return format2.parse(date);
	}
	
	/**
	 * 数字对象  转 时间字符串
	 * @param date
	 * @return
	 */
	public static String getNumberObjToDataStr(Object date){
		Long dateLong = Long.valueOf(String.valueOf(date));
		return getDateToString(new Date(dateLong));
	}
	
	/**
	 * Timestamp 转换成 时间字符串
	 * @param time
	 * @return
	 */
	public static String getTimestampToStr(Timestamp time){
		return getNumberObjToDataStr(time.getTime());
	}
	
	/**
	 * 转换为Double类型
	 * @param obj
	 * @return
	 */
	public static Double getDouble(Object obj){
		if("".equals(obj)||obj==null){
			obj = 0;
		}
		return Double.valueOf(String.valueOf(obj));
	}
	/**
	 * 数字字符串 转化为日期
	 * @param date
	 * @return
	 */
	public static Date getNumberToDate(Object date){
		return new Date(Long.valueOf(String.valueOf(date)));
	}
	
	/**
	 * 日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String getDateToString(Date date){
		return format.format(date);
	}
	
	public static String getDateToString2(Date date){
		format2.setLenient(false);
		return format2.format(date);
	}
	
	/**
	 * 数字字符串转换为Timestamp
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getStrNumToTimestamp(String date) throws ParseException {
		return new Timestamp(getDateStrToLong(date));
	}
	/**
	 * 数字字符串转换为Timestamp
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getStrToTimestamp(String date) throws ParseException {
		format.setLenient(false);
		return new Timestamp(format.parse(date).getTime());
	}
	
	public static String getDoubleToStr(Double doubleNum){
		if(doubleNum!=null){
			return String.valueOf((int)(doubleNum/1));
		}else{
			return "0";
		}
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss字符串转换为Timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp getStrToTimestamp2(String date) throws ParseException {
		format2.setLenient(false);
		return new Timestamp(format2.parse(date).getTime());
	}
}
