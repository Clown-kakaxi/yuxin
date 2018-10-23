package com.yuchengtech.emp.core.keygen.timeformat;

import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * 时间格式Key生成器
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface TimeFormatKeyGenerator {

	/**
	 * 生成当前时间戳+序列号的Key（格式为“年月日时分秒序列号”。形如：201301311311150001）
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract String getTimeFormatKey(Sequence sequence) ; 
	
	/**
	 * 生成指定日期的时间戳+序列号的Key（格式为“年月日时分秒序列号”。形如：201301311311150001）
	 * @param date 生成时间戳的日期
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract String getTimeFormatKey(java.util.Date date, Sequence sequence) ;
	
	/**
	 * 生成当前时间戳+序列号的Key（格式为“年月日时分秒序列号”。形如：201301311311150001）
	 * @param timeFormat 日期格式，参考java.text.SimpleDateFormat
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract String getTimeFormatKey(String timeFormat, Sequence sequence) ;
	
	/**
	 * 生成指定日期的时间戳+序列号的Key（格式为“年月日时分秒序列号”。形如：201301311311150001）
	 * @param timeFormat 日期格式，参考java.text.SimpleDateFormat 
	 * @param date 生成时间戳的日期
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract String getTimeFormatKey(String timeFormat, java.util.Date date, Sequence sequence) ;
	
}
