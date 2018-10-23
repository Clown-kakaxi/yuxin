/**
 * @Title: StringUtil.java
 * @Package com.ytec.mdm.base.util
 * @Description: TODO
 * @author ���������׳ϿƼ����޹�˾
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
 * Title: �ַ�������
 * Description: �ַ�����������
 * </pre>
 * 
 * @author zl zhanglei5@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:1.00.00    �޸���:		�޸�����:	�޸�����:
 * </pre>
 */
public class StringUtil {

	public static final int CLOBBUFFERSIZE = 2048;

	/**
	 * �ж϶����Ƿ�ΪNULL��մ�
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {

		return (null == obj) || "".equals(obj);

	}

	/**
	 * ȥ���ַ�����λ�հ��ַ�
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
	 * �Ѷ���ת��Ϊ�ַ�������ȥ��β�ո�
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {

		return ((null != obj) ? obj.toString().trim() : "");

	}

	/**
	 * �����ֶ����ַ���ת��ΪBigDecimal
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {

		return ((null != obj && !"".endsWith(obj.toString().trim())) ? new BigDecimal(
				obj.toString().trim()) : null);

	}

	/**
	 * �����ֶ����ַ���ת��ΪDouble
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj) {

		return ((null != obj && !"".endsWith(obj.toString().trim())) ? Double
				.valueOf((obj.toString().trim())) : null);

	}

	/**
	 * �������ַ���ת��Ϊlongֵ
	 * 
	 * @param obj
	 *            �����ַ��� ��obj������NULL��մ�
	 * @return
	 */
	public static long toLong(Object obj) {
		return Long.parseLong(toString(obj));

	}

	/**
	 * �����һ������Ϊ�ջ�NULL���򷵻صڶ������������򷵻ص�һ������
	 * 
	 * @param aStr1
	 * @param aStr2
	 * @return
	 */
	public static String nvl(String aStr1, String aStr2) {
		return (!"".equals(aStr1) && null != aStr1) ? aStr1 : aStr2;
	}

	/**
	 * ��ʱ��ת��Ϊ�ִ�
	 * 
	 * @param date
	 *            ��ת����ʱ��
	 * @param format
	 *            ת����ʽ
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
	 * @��������:readClob
	 * @��������:��ȡClob�ֶ�
	 * @�����뷵��˵��:
	 * 		@param c
	 * 		@return
	 * @�㷨����:
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
	 * @��������:split
	 * @��������:�ַ����ָ�
	 * @�����뷵��˵��:
	 * 		@param value  �����ַ���
	 * 		@param delim  �ָ���
	 * 		@return
	 * @�㷨����:
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
	 * @��������:reverse2Date
	 * @��������: �����ַ�����ʽȥת����Ӧ��ʽ�����ں�ʱ��
	 * @�����뷵��˵��:
	 * @param date
	 * @param dateFormat
	 * @return
	 * @�㷨����:
	 */
	public static Date reverse2Date(String date, SimpleDateFormat dateFormat) {
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			SimpleDateFormat simple = null;
			switch (date.length()) {
			case 19:// ����+ʱ��yyyy/MM/dd HH:mm:ss ����yyyy-MM-dd HH:mm:ss
				if ('-' == date.charAt(4)) {
					simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else {
					simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				}
				break;
			case 10:// ����yyyy/MM/dd ����yyyy-MM-dd
				if ('-' == date.charAt(4)) {
					simple = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					simple = new SimpleDateFormat("yyyy/MM/dd");
				}
				break;
			case 8:// ʱ��HH:mm:ss ������ yyyyMMdd
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
	 * �õ�����IP.
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
	 * * ������հ��滻��һ��.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 * @��������:String trimWhile(String str)
	 * @��������:
	 * @�����뷵��˵��: String trimWhile(String str)
	 * @�㷨����:
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
	 * * �Ƚ�date1<=date2.
	 * 
	 * @param date1
	 *            the date1
	 * @param date2
	 *            the date2
	 * @param equal
	 *            the equal
	 * @return true, if compares date
	 * @��������:boolean comparesDate(Date date1, Date date2, boolean equal)
	 * @��������:
	 * @�����뷵��˵��: boolean comparesDate(Date date1, Date date2, boolean equal)
	 * @�㷨����:
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
	 * @��������:BCD2ASCII
	 * @��������:BCD����תASCII����
	 * @�����뷵��˵��:
	 * 		@param b
	 * 		@return
	 * @�㷨����:
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
	 * @��������:BCD2ASCII
	 * @��������:BCD��������תASCII���봮
	 * @�����뷵��˵��:
	 * 		@param bs
	 * 		@return
	 * @�㷨����:
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
	 * @��������:isChinese
	 * @��������:��֤�ַ������Ƿ�������
	 * @�����뷵��˵��:
	 * @param msg
	 * @return
	 * @�㷨����:
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
