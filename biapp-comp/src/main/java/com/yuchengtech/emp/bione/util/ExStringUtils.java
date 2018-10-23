/**
 * 
 */
package com.yuchengtech.emp.bione.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ExStringUtils {

	/**
	 * @param str
	 *            被截取的字符串
	 * 
	 * @param len
	 *            需要显示的长度(以byte为单位)
	 * @param symbol
	 *            用于表示省略的信息的字符
	 * @return 返回处理后的字符串
	 */
	public static String subString(String str, int len, String symbol) {

		long counterOfDoubleByte = 0;
		byte[] bytes;
		String result = str;

		try {
			bytes = str.getBytes("GBK");

			if (bytes.length > len) {

				for (int i = 0; i < len; i++) {
					if (bytes[i] < 0)
						counterOfDoubleByte++;
				}

				if (counterOfDoubleByte % 2 == 0)
					result = new String(bytes, 0, len, "GBK") + symbol;
				else
					result = new String(bytes, 0, len - 1, "GBK") + symbol;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 判断字符串是否为数字，包括小数
	 * @param str
	 *            字符串
	 * 
	 * @return 返回处理后的字符串
	 */
	public static boolean isNumber(String number) {
		int index = number.indexOf(".");
		if (index < 0) {
			return StringUtils.isNumeric(number);
		} else {
			String num1 = number.substring(0, index);
			String num2 = number.substring(index + 1);

			return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
		}
	}

}
