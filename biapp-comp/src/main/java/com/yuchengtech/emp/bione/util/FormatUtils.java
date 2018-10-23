package com.yuchengtech.emp.bione.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期、数字格式化与转换工具类
 */
public class FormatUtils {

    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        if (pattern == null || "".equals(pattern.trim()))
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    public static String formatShortDate(Date date) {
        return (formatDate(date, "yyyyMMdd"));
    }
    
    public static String formatDate(Date date) {
        return (formatDate(date, "yyyy-MM-dd"));
    }

    public static String formatTime(Date date) {
        return (formatDate(date, "HH:mm:ss"));
    }

    public static String formatDateTime(Date date) {
        return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    public static String formatTimestamp(Date date) {
        return (formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public static Date parseDate(String date, String pattern) {
    	if ((date == null) || (date.trim().equals(""))) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Date parseShortDate(String date) {
        return parseDate(date, "yyyyMMdd");
    }
    
    public static Date parseDate(String date) {
        return parseDate(date, "yyyy-MM-dd");
    }

    public static Date parseDateTime(String datetime) {
        return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static Date parseTimestamp(String timestamp) {
        return parseDate(timestamp, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    /**
     * 默认的转换时间戳值数值为日期字符串的方法：格式yyyy-MM-dd HH:mm:ss
     * @param times Long值时间，时间戳
     * @return Long值为空时返回空字符串
     */
    public static String format(Long times) {
    	Date date = (times == null) ? null : new Date(times.longValue());
    	return formatDateTime(date);
    }
    
    /**
     * 转换时间戳值数值为日期字符串的方法，转换格式为指定格式
     * @param times Long值时间，时间戳
     * @param pattern 格式
     * @return Long值为空时返回空字符串
     */
    public static String format(Long times, String pattern) {
    	Date date = (times == null) ? null : new Date(times.longValue());
    	return formatDate(date, pattern);
    }
    
    /**
     * 返回yyyy-MM-dd格式日期值
     * @param times Long值时间，时间戳
     * @return Long值为空时返回空字符串
     */
    public static String formatDate(Long times) {
    	return format(times, "yyyy-MM-dd");
    }
    
    /**
     * 返回HH:mm:ss格式时间值
     * @param times Long值时间，时间戳
     * @return Long值为空时返回空字符串
     */
    public static String formatTime(Long times) {
    	return format(times, "HH:mm:ss");
    }
    
    /**
     * 返回yyyy-MM-dd HH:mm:ss格式日期值
     * @param times Long值时间，时间戳
     * @return Long值为空时返回空字符串
     */
    public static String formatDateTime(Long times) {
    	return format(times, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 返回yyyy-MM-dd HH:mm格式日期值
     * @param times Long值时间，时间戳
     * @return Long值为空时返回空字符串
     */
    public static String formatDateTimeMin(Long times) {
    	return format(times, "yyyy-MM-dd HH:mm");
    }
    
    
    /**
     * 默认的转换时间戳值时间为Date的方法：返回java.util.Date
     * @param times Long值时间
     * @return java.util.Date
     */
    public static Date parse(Long times) {
    	return (times == null) ? null : new Date(times.longValue());
    }
    
    
    // 格式金额
    public static String formatMoney(double menoy) {
		return formatNumber(menoy, 2);
	}

    //小数位为fraction位
    public static String formatNumber(double decimal, int fraction) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(fraction);
        nf.setMinimumFractionDigits(fraction);
        return (nf.format(decimal));
    }

    //小数位为0位（结果是整数字符串）
    public static String formatNumber(double decimal) {
        return formatNumber(decimal, 0);
    }


	/**
	 *  将数字格式为以","分割的字符串，若为空则返回"0"，若有截断为四舍五入
	 * @param number
	 *            要格式的数字类型实例，可以是Integer,Long,Float,Double,BigDecimal,BigInteger
	 * @param maximumFractionDigits
	 *            最多小数位数
	 * @param minimumFractionDigits
	 *            最少小数位数
	 * @return 格式化后的数字字符串
	 */
	public static String formatNumber(Number number,
			int maximumFractionDigits, int minimumFractionDigits) {
		if (number == null)
			return "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(maximumFractionDigits);
		nf.setMinimumFractionDigits(minimumFractionDigits);
        return nf.format(number);
	}
	
	private static Pattern ColumnPattern = Pattern.compile("_\\w");
    
    /**
     * 将字段名转换成Java规范的属性名，如"MODEL_CODE"--"modelCode"
     * @param column 字段名称，如"MODEL_CODE"
     * @return 符合Java规范的属性名(对于连续带多个_的不能成功转换)
     */
    public static String toProperty(String column) {
      String s = column.toLowerCase();
      Matcher m = ColumnPattern.matcher(column);
      if (!m.find()) return s;

      StringBuffer sb = new StringBuffer(100);
      int index = 0;
      while (m.find(index)) {
        sb.append(s.substring(index, m.start()));
        sb.append(m.group().substring(1).toUpperCase());
        index = m.end();
      }
      sb.append(s.substring(index));
      return sb.toString();
    }
    
    /**
     * 将字符串转义，如在Oracle查询中要转义'%','_',以及转义符符本身'/'
     * @param str 被转义字符串
     * @return 若被转义字符串为空，则不转义，否则返回转义后的字符串
     */
    public static String escapeSQLChar(String str) {
    	if (str == null || "".equals(str)) {
    		return str;
    	}
    	/*return str.replaceAll("\\\\","\\\\\\\\").replaceAll("_","\\\\_")
				.replaceAll("%","\\\\%");*/
    	return str.replaceAll("/","//").replaceAll("_","/_")
				.replaceAll("%","/%");
    }
}

