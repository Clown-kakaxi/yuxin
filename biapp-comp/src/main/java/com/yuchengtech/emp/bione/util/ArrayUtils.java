package com.yuchengtech.emp.bione.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述：ArrayUtils
 * @author liuch(liucheng2@yuchengtech.com)
 * @version 1.0 2013-1-9 下午1:11:09
 * @see
 * HISTORY
 * 2013-1-9 下午1:11:09 创建文件
 */
public class ArrayUtils {

    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * Return a collection from the comma delimited String.
     *
     * @param commaDelim the comma delimited String.
     * @return A collection from the comma delimited String. Returns <tt>null</tt> if the string is empty.
     */
    public static Collection<String> asCollection(String commaDelim) {
        if (commaDelim == null || commaDelim.trim().length() == 0) {
            return null;
        }
        return commaDelimitedStringToSet(commaDelim);
    }

    public static <T> Set<T> asSet(T... element) {
        HashSet<T> elements = new HashSet<T>(element.length);
        Collections.addAll(elements, element);
        return elements;
    }
    
    /**
     * Returns a set from comma delimted Strings.
     * @param s The String to parse.
     * @return A set from comma delimted Strings.
     */
    public static Set<String> commaDelimitedStringToSet(String s) {
        Set<String> set = new HashSet<String>();
        String[] split = s.split(",");
        for (String aSplit : split) {
            String trimmed = aSplit.trim();
            if (trimmed.length() > 0)
                set.add(trimmed);
        }
        return set;
    }


}
