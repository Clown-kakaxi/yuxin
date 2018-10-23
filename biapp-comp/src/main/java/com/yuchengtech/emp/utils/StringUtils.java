package com.yuchengtech.emp.utils;

import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 正则表达式匹配
	 * 
	 * @param strtext
	 * 
	 * @param strPattern
	 * 
	 * @return
	 */
	public static boolean like(String strtext, String strPattern) {
		String inputstrp = strPattern.replaceAll("[.]", "\\\\x2e");
		inputstrp = inputstrp.replaceAll("[*]", ".*");
		inputstrp = inputstrp.replaceAll("%", ".*");
		String oldinputstrp;
		do {
			oldinputstrp = inputstrp;
			inputstrp = oldinputstrp.replace("?", "\\w");
		} while (!oldinputstrp.equals(inputstrp));
		return Pattern.matches(inputstrp, strtext);
	}

	/**
	 * 格式化信息，例如 String source = "用户{0}操作了{1}功能，用户{0}没权限"; String dest =
	 * StringUtil.format(source, new String[] {"张三","新增机构"} )
	 * 
	 * 执行结果：dest成了 用户张三操作了新增机构功能，用户张三没权限
	 */
	public static String formate(String source, String[] params) {
		if (source == null || params == null)
			return source;
		String dest = "";
		int paramLength = params.length;
		String regStr = "";
		for (int i = 0; i < paramLength; i++) {
			regStr = "\\{" + String.valueOf(i) + "\\}";
			// 替换异常信息中的变量
			source = source.replaceAll(regStr, params[i]);
		}
		dest = source;
		return dest;
	}

	/** trim */
	public static String trimNull(String source) {
		return trimNull(source, "");
	}

	/** trim */
	public static String trimNull(String source, String deft) {
		if (source == null || source.trim().length() <= 0) {
			return deft;
		}
		return source.trim();
	}

	public static String formate(String source, int len) {
		return formate(source, len, ' ');
	}

	public static String formate(String source, int len, char c) {
		if (source == null || len <= 0)
			return source;
		String dest = source;
		while (dest.length() < len) {
			dest = c + dest;
		}
		return dest;
	}
	
	/**
     * Check if two strings are equal. Here, null is equal to null.
     *
     * @param a the first value
     * @param b the second value
     * @return true if both are null or both are equal
     */
    public static boolean equalsWithNull(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    /**
     * Convert a string to uppercase using the English locale.
     *
     * @param s the test to convert
     * @return the uppercase text
     */
    public static String toUpperEnglish(String s) {
        return s.toUpperCase(Locale.ENGLISH);
    }

    /**
     * Convert a string to lowercase using the English locale.
     *
     * @param s the text to convert
     * @return the lowercase text
     */
    public static String toLowerEnglish(String s) {
        return s.toLowerCase(Locale.ENGLISH);
    }


}