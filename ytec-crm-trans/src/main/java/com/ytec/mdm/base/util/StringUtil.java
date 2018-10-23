/**
 * @Title: StringUtil.java
 * @Package com.ytec.mdm.base.util
 * @Description: TODO
 * @author 北京宇信易诚科技有限公司
 * @date Apr 15, 2013
 * @version V1.0 
 */
package com.ytec.mdm.base.util;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Title: 字符串处理
 * Description: 字符串处理工具类
 * </pre>
 * 
 * @author zl zhanglei5@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.00.00    修改人:		修改日期:	修改内容:
 * </pre>
 */
public class StringUtil {

	public static final int CLOBBUFFERSIZE = 2048;

	/**
	 * 判断对象是否为NULL或空串
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {

		return (null == obj) || "".equals(obj);

	}

	/**
	 * 去掉字符串首位空白字符
	 * 
	 * @param str
	 * @return str
	 */
	public static String StringTrim(String str) {
		if (str == null) {
			return null;
		}
		int count = str.length();
		int len = count;
		int st = 0;
		int off = 0;
		char[] val = str.toCharArray();

		while ((st < len) && Character.isWhitespace(val[off + st])) {
			st++;
		}
		while ((st < len) && Character.isWhitespace(val[off + len - 1])) {
			len--;
		}
		return ((st > 0) || (len < count)) ? str.substring(st, len) : str;
	}

	/**
	 * 把对象转换为字符串，并去行尾空格
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {

		return ((null != obj) ? obj.toString().trim() : "");

	}

	/**
	 * 把数字对象字符串转换为BigDecimal
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {

		return ((null != obj && !"".endsWith(obj.toString().trim())) ? new BigDecimal(
				obj.toString().trim()) : null);

	}

	/**
	 * 把数字对象字符串转换为Double
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj) {

		return ((null != obj && !"".endsWith(obj.toString().trim())) ? Double
				.valueOf((obj.toString().trim())) : null);

	}

	/**
	 * 把数字字符串转换为long值
	 * 
	 * @param obj
	 *            数字字符串 ，obj不能是NULL或空串
	 * @return
	 */
	public static long toLong(Object obj) {
		return Long.parseLong(toString(obj));

	}

	/**
	 * 如果第一个参数为空或NULL，则返回第二个参数，否则返回第一个参数
	 * 
	 * @param aStr1
	 * @param aStr2
	 * @return
	 */
	public static String nvl(String aStr1, String aStr2) {
		return (!"".equals(aStr1) && null != aStr1) ? aStr1 : aStr2;
	}

	/**
	 * 把时间转换为字串
	 * 
	 * @param date
	 *            待转换的时间
	 * @param format
	 *            转换格式
	 * @return String
	 */
	public static String date2Str(Date date, String format) {
		if (!StringUtil.isEmpty(date)) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		} else {
			return null;
		}

	}

	/**
	 * @函数名称:readClob
	 * @函数描述:读取Clob字段
	 * @参数与返回说明:
	 * 		@param c
	 * 		@return
	 * @算法描述:
	 */
	public static String readClob(Clob c) {
		if (c == null) {
			return "";
		}
		Reader r = null;
		StringBuffer sb = null;
		try {
			sb = new StringBuffer((int) c.length());
			r = c.getCharacterStream();
			char[] cbuf = new char[CLOBBUFFERSIZE];
			int n;
			while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
				sb.append(cbuf, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sb = null;
		} finally {
			try {
				if (r != null) {
					r.close();
				}
			} catch (Exception e) {
			}
		}
		return sb == null ? "" : sb.toString();
	}

	/**
	 * @函数名称:split
	 * @函数描述:字符串分隔
	 * @参数与返回说明:
	 * 		@param value  被分字符串
	 * 		@param delim  分隔符
	 * 		@return
	 * @算法描述:
	 */
	public static String[] split(String value, char delim) {
		int end = value.length();
		List<String> res = new ArrayList<String>();

		int start = 0;
		for (int i = 0; i < end; i++) {
			if (value.charAt(i) == delim) {
				if (start == i)
					res.add("");
				else {
					res.add(value.substring(start, i));
				}
				start = i + 1;
			}
		}

		if (start == 0) {
			res.add(value);
		} else if (start != end) {
			res.add(value.substring(start, end));
		} else {
			for (int i = res.size() - 1; (i >= 0)
					&& (((String) res.get(i)).length() == 0); i--) {
				res.remove(i);
			}

		}

		return (String[]) res.toArray(new String[res.size()]);
	}

	/**
	 * @函数名称:reverse2Date
	 * @函数描述: 根据字符串格式去转换相应格式的日期和时间
	 * @参数与返回说明:
	 * @param date
	 * @param dateFormat
	 * @return
	 * @算法描述:
	 */
	public static Date reverse2Date(String date, SimpleDateFormat dateFormat) {
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			SimpleDateFormat simple = null;
			switch (date.length()) {
			case 19:// 日期+时间yyyy/MM/dd HH:mm:ss 或者yyyy-MM-dd HH:mm:ss
				if ('-' == date.charAt(4)) {
					simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else {
					simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				}
				break;
			case 10:// 日期yyyy/MM/dd 或者yyyy-MM-dd
				if ('-' == date.charAt(4)) {
					simple = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					simple = new SimpleDateFormat("yyyy/MM/dd");
				}
				break;
			case 8:// 时间HH:mm:ss 或日期 yyyyMMdd
				if (':' == date.charAt(2)) {
					simple = new SimpleDateFormat("HH:mm:ss");
				} else {
					simple = new SimpleDateFormat("yyyyMMdd");
				}
				break;
			default:
				break;
			}
			if (simple == null)
				return null;
			try {
				simple.setLenient(false);
				return simple.parse(date);
			} catch (Exception et) {
				et.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 得到本机IP.
	 * 
	 * @return the local ip
	 */
	public static String getLocalIp() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return "0.0.0.0";
		}
	}

	/**
	 * * 将多个空白替换成一个.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 * @函数名称:String trimWhile(String str)
	 * @函数描述:
	 * @参数与返回说明: String trimWhile(String str)
	 * @算法描述:
	 */
	public static String trimWhile(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("[\\s*|\t|\r|\n]{1,}");
			Matcher m = p.matcher(str);
			str = m.replaceAll(" ");
			return str;
		}
		return str;
	}

	/**
	 * * 比较date1<=date2.
	 * 
	 * @param date1
	 *            the date1
	 * @param date2
	 *            the date2
	 * @param equal
	 *            the equal
	 * @return true, if compares date
	 * @函数名称:boolean comparesDate(Date date1, Date date2, boolean equal)
	 * @函数描述:
	 * @参数与返回说明: boolean comparesDate(Date date1, Date date2, boolean equal)
	 * @算法描述:
	 */
	public static boolean comparesDate(Date date1, Date date2, boolean equal) {
		if (date1 == null || date2 == null) {
			return true;
		} else {
			if (equal) {
				return date1.getTime() <= date2.getTime();
			} else {
				return date1.getTime() < date2.getTime();
			}
		}
	}

	/**
	 * @函数名称:BCD2ASCII
	 * @函数描述:BCD编码转ASCII编码
	 * @参数与返回说明:
	 * 		@param b
	 * 		@return
	 * @算法描述:
	 */
	public static String BCD2ASCII(byte b) {
		int h = (b & 0xF0) >> 4;
		int l = b & 0xF;
		byte[] bRet = new byte[2];
		bRet[0] = (byte) (h + (h > 9 ? 55 : 48));
		bRet[1] = (byte) (l + (l > 9 ? 55 : 48));
		return new String(bRet);
	}

	/**
	 * @函数名称:BCD2ASCII
	 * @函数描述:BCD编码数组转ASCII编码串
	 * @参数与返回说明:
	 * 		@param bs
	 * 		@return
	 * @算法描述:
	 */
	public static String BCD2ASCII(byte[] bs) {
		int l = bs.length;
		StringBuffer sRet=new StringBuffer("");
		for (int n = 0; n < l; n++) {
			sRet.append(BCD2ASCII(bs[n]));
		}
		return sRet.toString();
	}

	public static String BString2ASCII(byte b) {
		int h = (b & 0xF0) >> 4;
		int l = b & 0xF;
		byte[] bRet = new byte[2];
		bRet[0] = (byte) (h + (h > 9 ? 55 : 48));
		bRet[1] = (byte) (l + (l > 9 ? 55 : 48));
		return new String(bRet);
	}

	public static String BString2ASCII(byte[] bs) {
		int l = bs.length;
		StringBuffer sRet=new StringBuffer("");
		for (int n = 0; n < l; n++) {
			sRet.append(BString2ASCII(bs[n]));
		}
		return sRet.toString();
	}

	public static byte[] ASCII2BCD(String sValue) {
		if ((sValue == null) || (sValue.length() < 1))
			return null;
		String sClone = sValue;
		if (sClone.length() % 2 != 0)
			sClone = "0" + sClone;
		byte[] bRet = new byte[sClone.length() / 2];
		for (int n = 0; n < sClone.length(); n += 2) {
			byte hi = 0;
			byte lo = 0;
			if ((sClone.charAt(n) >= '0') && (sClone.charAt(n) <= '9'))
				hi = (byte) ((sClone.charAt(n) - '0') * 16);
			else if ((sClone.charAt(n) >= 'a') && (sClone.charAt(n) <= 'f'))
				hi = (byte) ((sClone.charAt(n) - 'a' + 10) * 16);
			else if ((sClone.charAt(n) >= 'A') && (sClone.charAt(n) <= 'F')) {
				hi = (byte) ((sClone.charAt(n) - 'A' + 10) * 16);
			}

			if ((sClone.charAt(n + 1) >= '0') && (sClone.charAt(n + 1) <= '9'))
				lo = (byte) (sClone.charAt(n + 1) - '0');
			else if ((sClone.charAt(n + 1) >= 'a')
					&& (sClone.charAt(n + 1) <= 'f'))
				lo = (byte) (sClone.charAt(n + 1) - 'a' + 10);
			else if ((sClone.charAt(n + 1) >= 'A')
					&& (sClone.charAt(n + 1) <= 'F')) {
				lo = (byte) (sClone.charAt(n + 1) - 'A' + 10);
			}
			bRet[(n / 2)] = (byte) (hi + lo);
		}

		return bRet;
	}

	/**
	 * @函数名称:isChinese
	 * @函数描述:验证字符串中是否有中文
	 * @参数与返回说明:
	 * @param msg
	 * @return
	 * @算法描述:
	 */
	public static boolean isChinese(String msg) {
		if (msg != null) {
			char[] msgC = msg.toCharArray();
			for (char c : msgC) {
				Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
				if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
						|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
						|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
						|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
						|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
						|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
					return true;
				}
			}

		}
		return false;
	}
}
