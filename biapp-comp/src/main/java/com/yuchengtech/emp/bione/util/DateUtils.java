/*
 * 
 * 
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ytec Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ytec.
 */

package com.yuchengtech.emp.bione.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 日期计算工具类
 *
 * @author fbchen
 * @version 1.0  Date: 2009-7-7 下午06:59:40
 */
public class DateUtils {

	// 时间格式：2010-12-30 14:33:48 296
	public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * 获得传入日期当月最小的日期
	 * @param dt
	 * @return
	 */
	public static Date getStartDateOfMonth(Date dt) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(dt);
		gcal.set(Calendar.DATE, 1);
		return gcal.getTime();
	}

	/**
	 * 获得传入日期当月最大的日期
	 * @param dt
	 * @return
	 */
	public static Date getEndDateOfMonth(Date dt) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(dt);
		gcal.set(Calendar.DATE, gcal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return gcal.getTime();
	}

	/**
	 * 获取下个月的第一天，如：输入2009-01-31，返回2009-02-01
	 * @param dt
	 * @return
	 */
	public static Date getStartDateOfNextMonth(Date dt) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(dt);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 获取下个月的最后一天，如：输入2009-01-31，返回2009-02-28
	 * @param dt
	 * @return
	 */
	public static Date getEndDateOfNextMonth(Date dt) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(dt);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 获得传入日期当季最大的日期
	 * @param dt
	 * @return
	 */
	public static Date getEndDateOfSeason(Date dt) {
		java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
		gcal.setTime(dt);
		int mon = gcal.get(java.util.Calendar.MONTH);
		//计算需要加多少月才为季度的末月
		int add = ((mon + 1) % 3) == 0 ? 0 : 3 - ((mon + 1) % 3);
		gcal.add(java.util.Calendar.MONTH, add);
		gcal.set(java.util.Calendar.DATE, gcal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
		return gcal.getTime();
	}

	/**
	 * 获得传入日期当季最小的日期
	 * @param dt
	 * @return
	 */
	public static Date getStartDateOfSeason(Date dt) {
		java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
		gcal.setTime(dt);
		int mon = gcal.get(java.util.Calendar.MONTH);
		//计算需要加多少月才为季度的首月
		int add = -(mon % 3);
		gcal.add(java.util.Calendar.MONTH, add);
		gcal.set(java.util.Calendar.DATE, 1);
		return gcal.getTime();
	}

	/**
	 * 获得传入日期当年最大的日期
	 * @param dt
	 * @return
	 */
	public static Date getEndDateOfYear(Date dt) {
		java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
		gcal.setTime(dt);
		gcal.set(java.util.Calendar.MONTH, 11);
		gcal.set(java.util.Calendar.DATE, 31);
		return gcal.getTime();
	}

	/**
	 * 获得传入日期当年最小的日期
	 * @param dt
	 * @return
	 */
	public static Date getStartDateOfYear(Date dt) {
		java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
		gcal.setTime(dt);
		gcal.set(java.util.Calendar.MONTH, 0);
		gcal.set(java.util.Calendar.DATE, 1);
		return gcal.getTime();
	}

	/**
	 * 获取过去的第N天的日期
	 * @param pass 过去的天数
	 * @return
	 */
	public static Date getPastDate(int pass) {
		return getPastDate(pass, false);
	}

	/**
	 * 获取过去的第N天的日期，若clearTime为TRUE则都将时分秒置为0
	 * @param pass
	 * @param clearTime
	 * @return
	 */
	public static Date getPastDate(int pass, boolean clearTime) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(new Date());
		gcal.add(Calendar.DATE, pass * (-1));
		if (clearTime) {
			gcal.set(Calendar.HOUR_OF_DAY, 0);
			gcal.set(Calendar.MINUTE, 0);
			gcal.set(Calendar.SECOND, 0);
		}
		return gcal.getTime();
	}

	/**
	 * 指定日期、时间，获取组合后的时间
	 * @param date 日期
	 * @param time 时间，格式如HH:mm:ss或HH:mm
	 * @return
	 */
	public static Date getDateTime(Date date, String time) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		String[] t = time.split(":");
		gcal.set(Calendar.HOUR_OF_DAY, t.length > 0 ? NumberUtils.toInt(t[0], 0) : 0);
		gcal.set(Calendar.MINUTE, t.length > 1 ? NumberUtils.toInt(t[1], 0) : 0);
		gcal.set(Calendar.SECOND, t.length > 2 ? NumberUtils.toInt(t[2], 0) : 0);
		return gcal.getTime();
	}

	/**
	 * 计算日期值当前日期值的差值，计算方式：today-date。
	 * 因此，值等于0为今天，大于0为过去，小于0为未来。
	 * @param date
	 * @return
	 */
	public static int compareDay(Date date) {
		int today = Integer.parseInt(FormatUtils.formatDate(new Date(), "yyyyMMdd"));
		int idate = Integer.parseInt(FormatUtils.formatDate(date, "yyyyMMdd"));
		return (today - idate);
	}

	/**
	 * 将字符串日期转成当天时间等于00:00:00的java.util.Date对象
	 * @param dateStr yyyy-MM-dd格式的日期字符串
	 * @return Date
	 */
	public static Date getDateStart(String dateStr) {
		Date date = FormatUtils.parseDate(dateStr);
		if (date != null) {
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(date);
			gcal.set(Calendar.HOUR_OF_DAY, 0);
			gcal.set(Calendar.MINUTE, 0);
			gcal.set(Calendar.SECOND, 0);
			gcal.set(Calendar.MILLISECOND, 0);
			date = gcal.getTime();
		}
		return date;
	}

	/**
	 * 将字符串日期转成当天时间等于00:00:00的java.util.Date对象的Long值（毫秒数）
	 * @param dateStr yyyy-MM-dd格式的日期字符串
	 * @return Long
	 */
	public static Long getDateStartLong(String dateStr) {
		Date date = getDateStart(dateStr);
		return date == null ? null : new Long(date.getTime());
	}

	/**
	 * 将字符串日期转成当天时间等于23:59:59的java.util.Date对象
	 * @param dateStr yyyy-MM-dd格式的日期字符串
	 * @return Date
	 */
	public static Date getDateEnd(String dateStr) {
		Date date = FormatUtils.parseDate(dateStr);
		if (date != null) {
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(date);
			gcal.set(Calendar.HOUR_OF_DAY, 23);
			gcal.set(Calendar.MINUTE, 59);
			gcal.set(Calendar.SECOND, 59);
			gcal.set(Calendar.MILLISECOND, 999);
			date = gcal.getTime();
		}
		return date;
	}

	/**
	 * 将字符串日期转成当天时间等于23:59:59的java.util.Date对象的Long值（毫秒数）
	 * @param dateStr yyyy-MM-dd格式的日期字符串
	 * @return Long
	 */
	public static Long getDateEndLong(String dateStr) {
		Date date = getDateEnd(dateStr);
		return date == null ? null : new Long(date.getTime());
	}

	// test
	public static void main(String[] args) {
		//Date d = getPastDate(7);
		//Systems.out.println(FormatUtils.formatDateTime(d));
	}

	/**
	 * @param time
	 *            时间
	 * @param format
	 *            日期返回的格式 如yyyy-MM-dd hh:mm:ss SSS
	 * @return 字符器类型的日期，日期的格式如参数format
	 */
	public static String getFormatTime(long time, String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.format(new Date(time));
	}

	/**
	 * 根据指定的格式对日期进行格式化。（可尽量使用FormatUtils类的方法。）
	 * @param date 待格式化日期
	 * @param pattern 格式
	 * @return 日期文本
	 */
	public static String formatDate(Date date, String pattern) {
		return FormatUtils.formatDate(date, pattern);
	}

}
