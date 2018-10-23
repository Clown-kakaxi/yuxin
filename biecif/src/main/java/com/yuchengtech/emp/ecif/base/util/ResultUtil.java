package com.yuchengtech.emp.ecif.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.bione.util.JpaEntityUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;

/**
 * <p>
 * Description: ��ݿ��ѯ�������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Create Date: 2009-2-19
 * </p>
 * <p>
 * Company: CITIC BANK
 * </p>
 * 
 * @author pengsenlin
 * @version $Id: ResultUtil.java,v 1.1 2009/07/14 03:23:22 pengsenlin Exp $
 */
@Service
@Transactional(readOnly = true)
public class ResultUtil {

	@Autowired
	private CodeUtil codeUtil;
	
	/**
	 * 将List<Object[]> 对象通过查询sql 映射成实体组
	 * @param list List<Object[]>对象 
	 * @param type 实体类型
	 * @param selectsql 查询SQL
	 * @return 实体数组
	 * @throws Exception
	 */
	public <T> T[] listObjectsToEntityBeans(List<Object[]> list , Class type, String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        // 获得实体bean的类型
        Class entityBeanClass = type;
        // 获取实体bean的方法
        Method[] entityBeanMethods = entityBeanClass.getDeclaredMethods();
        // 创建实体bean数组
        T[] entityBeans =
                (T[]) java.lang.reflect.Array.newInstance(entityBeanClass, list.size());
        
        String[] colss = cols.split(",");
        if(colss.length > 1){
	        Object[] objjust = list.get(0);
	        if(objjust.length != colss.length){
	        	throw new Exception("查询语句列与查询结果列数量不符!");
	        }
	        int entityBeansIndex = 0;
	        for(Object[] obj : list){
				Map<String, String> map = new HashMap<String, String>();
	        	for(int i = 0; i < colss.length; i ++){
	        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
	        	}
		
		        T entityBean = (T) type.newInstance(); // 实例化一个实体bean，对应一条记录
		        
	            entityBeans[entityBeansIndex] = entityBean;
		        
		        for (String keyName : colss) { // 遍历一条记录的所有字段
		
		            // 遍历实体bean的所有声明方法
		            for (int methodInedx = 0; methodInedx < entityBeanMethods.length; methodInedx++) {
		
		                // 当xmlRow字段名称和实体bean的方法名称匹配时，将对应字段的值注入bean中
		                if (("set" + keyName).equalsIgnoreCase(entityBeanMethods[methodInedx].getName())) {
		                    // 获取方法参数类型
		                    Class methodParameterType =
		                    	entityBeanMethods[methodInedx].getParameterTypes()[0];
		                    // 日期类型特殊处理
		                    if ("java.sql.Date".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName)!=null&&!map.get(keyName).equals("")){
			                        Date dateValue = strToTime(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    	}
		                        // 时间类型特殊处理
		                    }else if ("java.util.Date".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName)!=null&&!map.get(keyName).equals("")){
			                        Date dateValue = strToDate(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    	}
		                    } else if ("java.sql.Time".equals(methodParameterType.getName())) {
		                        Date dateValue = strToTime(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    } else if ("java.sql.Timestamp".equals(methodParameterType.getName())) {
		                        Date dateValue = strToTimestamp(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    } else if ("java.math.BigDecimal".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        BigDecimal bigDecimalValue =
			                                new BigDecimal(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, bigDecimalValue);
		                    	}
		                    } else if ("java.lang.Integer".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        Integer integerValue = new Integer(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else if ("int".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        Integer integerValue = new Integer(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else if ("long".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        long integerValue = new Long(map.get(keyName)).longValue();
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else if ("java.lang.Long".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        long integerValue = new Long(map.get(keyName)).longValue();
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else {
		                    	entityBeanMethods[methodInedx].invoke(entityBean, map.get(keyName));
		                    }
		                }
		                continue; // 匹配后立即进入一个字段的匹配
		            }
		        }
	            entityBeansIndex++;
	        }
	        return entityBeans;
        }else{	
	        int entityBeansIndex = 0;
	        for(Object obj : list){
				Map<String, String> map = new HashMap<String, String>();
        		map.put(colss[0], obj !=null ? obj.toString() :"");
		
		        T entityBean = (T) type.newInstance(); // 实例化一个实体bean，对应一条记录
		        
	            entityBeans[entityBeansIndex] = entityBean;
		        
		        for (String keyName : colss) { // 遍历一条记录的所有字段
		        	
		            // 遍历实体bean的所有声明方法
		            for (int methodInedx = 0; methodInedx < entityBeanMethods.length; methodInedx++) {
		
		                // 当xmlRow字段名称和实体bean的方法名称匹配时，将对应字段的值注入bean中
		                if (("set" + keyName).equalsIgnoreCase(entityBeanMethods[methodInedx].getName())) {
		                    // 获取方法参数类型
		                    Class methodParameterType =
		                    	entityBeanMethods[methodInedx].getParameterTypes()[0];
		                    // 日期类型特殊处理
		                    if ("java.sql.Date".equals(methodParameterType.getName())) {
		                        Date dateValue = strToTime(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                        // 时间类型特殊处理
		                    }else if ("java.util.Date".equals(methodParameterType.getName())) {
		                        Date dateValue = strToTime(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    } else if ("java.sql.Time".equals(methodParameterType.getName())) {
		                        Date dateValue = strToTime(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
		                    } else if ("java.math.BigDecimal".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        BigDecimal bigDecimalValue =
			                                new BigDecimal(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, bigDecimalValue);
		                    	}
		                    } else if ("java.lang.Integer".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        Integer integerValue = new Integer(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else if ("int".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        Integer integerValue = new Integer(map.get(keyName));
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else if ("long".equals(methodParameterType.getName())) {
		                    	if(map.get(keyName).trim().length() != 0){
			                        long integerValue = new Long(map.get(keyName)).longValue();
			                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
		                    	}
		                    } else {
		                    	entityBeanMethods[methodInedx].invoke(entityBean, map.get(keyName));
		                    }
		                }
		                continue; // 匹配后立即进入一个字段的匹配
		            }
		        }
	            entityBeansIndex++;
	        }
	        return entityBeans;
        }
    }
	
	/**
	 * 将List<Object> 对象通过查询SQL映射成实体数组
	 * @param list List<Object>对象
	 * @param type 实体类型
	 * @param selectsql 查询SQL
	 * @return 实体数组
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] listObjectToEntityBeans(List<Object> list , Class type, String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        String[] colss = cols.split(",");
        // 获得实体bean的类型
        Class entityBeanClass = type;
        // 获取实体bean的方法
        Method[] entityBeanMethods = entityBeanClass.getDeclaredMethods();
        // 创建实体bean数组
        T[] entityBeans =
                (T[]) java.lang.reflect.Array.newInstance(entityBeanClass, list.size());

        int entityBeansIndex = 0;
        for(int j = 0; j < list.size(); j ++){
        	
        	Object[] obj = (Object[]) list.get(j);
			Map<String, String> map = new HashMap<String, String>();
        	for(int i = 0; i < colss.length; i ++){
        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
        	}
	
	        T entityBean = (T) type.newInstance(); // 实例化一个实体bean，对应一条记录
	        
            entityBeans[entityBeansIndex] = entityBean;
	        
	        for (String keyName : colss) { // 遍历一条记录的所有字段
	
	            // 遍历实体bean的所有声明方法
	            for (int methodInedx = 0; methodInedx < entityBeanMethods.length; methodInedx++) {
	
	                // 当xmlRow字段名称和实体bean的方法名称匹配时，将对应字段的值注入bean中
	                if (("set" + keyName).equalsIgnoreCase(entityBeanMethods[methodInedx].getName())) {
	                    // 获取方法参数类型
	                    Class methodParameterType =
	                    	entityBeanMethods[methodInedx].getParameterTypes()[0];
	                    // 日期类型特殊处理
	                    if ("java.sql.Date".equals(methodParameterType.getName())) {
	                        Date dateValue = strToTime(map.get(keyName));
	                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
	                        // 时间类型特殊处理
	                    }else if ("java.util.Date".equals(methodParameterType.getName())) {
	                        Date dateValue = strToTime(map.get(keyName));
	                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
	                    } else if ("java.sql.Time".equals(methodParameterType.getName())) {
	                        Date dateValue = strToTime(map.get(keyName));
	                        entityBeanMethods[methodInedx].invoke(entityBean, dateValue);
	                    } else if ("java.math.BigDecimal".equals(methodParameterType.getName())) {
	                    	if(map.get(keyName).trim().length() != 0){
		                        BigDecimal bigDecimalValue =
		                                new BigDecimal(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, bigDecimalValue);
	                    	}
	                    } else if ("java.lang.Integer".equals(methodParameterType.getName())) {
	                    	if(map.get(keyName).trim().length() != 0){
		                        Integer integerValue = new Integer(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
	                    	}
	                    } else if ("int".equals(methodParameterType.getName())) {
	                    	if(map.get(keyName).trim().length() != 0){
		                        Integer integerValue = new Integer(map.get(keyName));
		                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
	                    	}
	                    } else if ("long".equals(methodParameterType.getName())) {
	                    	if(map.get(keyName).trim().length() != 0){
		                        long integerValue = new Long(map.get(keyName)).longValue();
		                        entityBeanMethods[methodInedx].invoke(entityBean, integerValue);
	                    	}
	                    } else {
	                    	entityBeanMethods[methodInedx].invoke(entityBean, map.get(keyName));
	                    }
	                }
	                continue; // 匹配后立即进入一个字段的匹配
	            }
	        }
            entityBeansIndex++;
        }
        return entityBeans;
    }
	
	/**
	 * 将List<Object[]> 对象通过查询sql 映射成List<Map<String, String>>
	 * @param list List<Object[]>对象 
	 * @param selectsql 查询SQL
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> listObjectsToListMaps(List<Object[]> list , String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        cols = cols.toLowerCase();
        List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
        String[] colss = cols.split(",");
        if(colss.length > 1){
	        Object[] objjust = list.get(0);
	        if(objjust.length != colss.length){
	        	throw new Exception("查询语句列与查询结果列数量不符!");
	        }	
	        for(Object[] obj : list){
				Map<String, String> map = new HashMap<String, String>();
	        	for(int i = 0; i < colss.length; i ++){
	        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
	        	}
        		listMaps.add(map);
	        }
	        return listMaps;
        }else{	
	        for(Object obj : list){
				Map<String, String> map = new HashMap<String, String>();
        		map.put(colss[0], obj !=null ? obj.toString() :"");
        		listMaps.add(map);
	        }
	        return listMaps;
        }
    }
	
	/**
	 * 将List<Object> 对象通过查询SQL映射成List<Map<String, String>>
	 * @param list List<Object>对象
	 * @param selectsql 查询SQL
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> listObjectToListMaps(List<Object> list , String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        cols = cols.toLowerCase();
        List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
        String[] colss = cols.split(",");
        if(colss.length > 1){
	        Object[] objjust = (Object[]) list.get(0);
	        if(objjust.length != colss.length){
	        	throw new Exception("查询语句列与查询结果列数量不符!");
	        }	
	        for(int j = 0; j < list.size(); j ++){
	        	Object[] obj = (Object[]) list.get(j);
				Map<String, String> map = new HashMap<String, String>();
	        	for(int i = 0; i < colss.length; i ++){
	        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
	        	}
	        	listMaps.add(map);
	        }
	        return listMaps;
        }else{	
	        for(Object obj : list){
				Map<String, String> map = new HashMap<String, String>();
        		map.put(colss[0], obj !=null ? obj.toString() :"");
        		listMaps.add(map);
	        }
	        return listMaps;
        }
    }
	
    /**
     * 将8位的字符串转换成时间
     * @param str 6位或8位时间字符串 152000,15:20:00
     * @return 日期 str为空返回null
     */
    public static java.sql.Date strToDate(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
       
        return java.sql.Date.valueOf(str);
    }
    	
    /**
     * 将8位的字符串转换成时间
     * @param str 6位或8位时间字符串 152000,15:20:00
     * @return 日期 str为空返回null
     */
    public static java.sql.Time strToTime(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        if (str.length() == 6) {
            str = formatTime6ToTime8(str);
        }
        if (str.length() == 10) {
            str = formatTime10ToTime8(str);
        }
        if (str.length() >= 23) {
            str = formatTime23ToTime8(str);
        }

        if (Integer.parseInt(str.substring(0, 2)) > 23) {
            return null; //大于23点
        }
        if (Integer.parseInt(str.substring(3, 5)) > 59) {
            return null; //大于59分
        }
        if (Integer.parseInt(str.substring(6)) > 59) {
            return null; //大于59秒
        }
        return java.sql.Time.valueOf(str);
    }
    
    /**
     * 将8位的字符串转换成时间
     * @param str 6位或8位时间字符串 152000,15:20:00
     * @return 日期 str为空返回null
     */
    public static java.sql.Timestamp strToTimestamp(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }

        return java.sql.Timestamp.valueOf(str);
    }
   
    
    /**
     * @see 将6位时间格式化成8位时间 如:150000--->15:00:00
     * @param valueStr  要处理的时间
     * @return 处理后的时间
     */
    public static String formatTime6ToTime8(String valueStr) {

        if (valueStr == null || (valueStr.trim()).equals("")) {
            return valueStr;
        }

        if (valueStr.matches("[0-9]{6}")) {
            String newValue = valueStr.substring(0, 2) + ":" + valueStr.substring(2, 4) + ":"
                    + valueStr.substring(4);
            return newValue;
        } else {
            return valueStr;
        }
    }

    /**
     * @see 将6位时间格式化成8位时间 如:150000--->15:00:00
     * @param valueStr  要处理的时间
     * @return 处理后的时间
     */
    public static String formatTime10ToTime8(String valueStr) {

        if (valueStr == null || (valueStr.trim()).equals("")) {
            return valueStr;
        }
        valueStr = valueStr.replaceAll("/", "");
        valueStr = valueStr.replaceAll("-", "");      
        return valueStr;

    }
   
    /**
     * 对于timestamp(6)的格式进行格式化
     * @param valueStr
     * @return
     */
    public static String formatTime23ToTime8(String valueStr) {

        if (valueStr == null || (valueStr.trim()).equals("")) {
            return valueStr;
        }

        String newValue = valueStr.substring(11, 19) ;
        return newValue;
    }
    
    /**
     * 判断数据是否为空,""及null都认为是空
     * @param str 字符串
     * @return true-为空,false-非空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0 || str.equals("null")) {
            return true;
        }
        return false;
    }
    
    /**
     * 将List<T>里数据字典项转换为数据字典名输出
     * @param list 需要转换的 List<T>
     * @param type 转换实体类型
     * @return 返回的 List<T>
     * @throws Exception
     */
	public <T> List listObjectsDictTran(List list , Class type) throws Exception {
		if(list != null && list.size() > 0){
	        // 获得实体bean的类型
	        Class entityBeanClass = type;
	        String className = type.getSimpleName().toString();
	        String tableName = "";
	        if(className != null && className.length() > 0){
	        	for(int i = 0; i < className.length(); i ++){
	        		char c = className.charAt(i);
	        		if(Character.isUpperCase(c)  && i != 0){
	        			tableName = tableName + "_" + c;
	        		}else{
	        			tableName = tableName + c;
	        		}
	        	}
	        }
	        if(tableName.length() > 0){
	        	tableName = tableName.toUpperCase();
	        }
	        Field[] fields = entityBeanClass.getDeclaredFields();
	        String columns = "";
	        for(int i = 0; i < fields.length; i ++){
	        	String name = fields[i].getName();
	        	String column = "";
	        	for(int slen = 0; slen < name.length(); slen ++){
	        		char c = name.charAt(slen);
	        		if(Character.isUpperCase(c) && slen != 0){
	        			column = column + "_" + c;
	        		}else{
	        			column = column + c;
	        		}
	        	}
	        	if(i == fields.length-1){
	        		columns = columns + column;
	        	}else{
	        		columns = columns + column + ",";
	        	}
	        }
	        if(columns.length() > 0){
	        	columns = columns.toUpperCase();
	        }
	        Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMapByColumns(tableName, columns);
	        for(int i = 0; i < list.size(); i ++){
		        T entityBean = (T) type.newInstance(); // 实例化一个实体bean，对应一条记录
		        entityBean = (T) list.get(i);
	        	Field[] beanfields = entityBean.getClass().getFields();
	        	for(Field beanfield : beanfields){
		    		if (beanfield.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
		    			if(dictMap != null && dictMap.get(beanfield.getName().toUpperCase()) != null){
			    			// 拿到该属性的gettet方法
						    Method getM = (Method) entityBean.getClass().getMethod("get" + getMethodName(beanfield.getName()));
					    	String val = (String) getM.invoke(entityBean);// 调用getter方法获取属性值
					    	if(dictMap.get(beanfield.getName().toUpperCase()).get(val) != null){// 拿到该属性的gettet方法
							    Method setM = (Method) entityBean.getClass().getMethod("set" + getMethodName(beanfield.getName()));
							    setM.invoke(entityBean, dictMap.get(beanfield.getName().toUpperCase()).get(val));
					    	}
		    			}
				    }
	        	}
	        }
	        return list;
		} else {
			return null;
		}
    }
	
    /**
     * 将List<T>里数据字典项转换为数据字典名输出
     * @param list 需要转换的 List<T> 此集合中的T为带数据库表字段的映射实体
     * @param type 转换实体类型
     * @return 返回的 List<T>
     * @throws Exception
     */
	public <T> List jpaListObjectsDictTran(List list , Class type) throws Exception {
		List<T> temp = new ArrayList<T>();
		if(list != null && list.size() > 0) {
			String tableName = JpaEntityUtils.getTableName(type);
			Map<String, String> columnMap = JpaEntityUtils.getColumnsByEntity(type);
	        String columns = "";
			if(columnMap != null){
				Set<Entry<String, String>> columnMapEntries = columnMap.entrySet();
				Iterator<Entry<String, String>> columnMapIterator = columnMapEntries.iterator();
				while(columnMapIterator.hasNext()){
					Map.Entry<String, String> columnMapEntry = (Entry<String, String>) columnMapIterator.next();
					String columnValue = columnMapEntry.getValue();
					if(columnMapIterator.hasNext()){
						columns = columns + columnValue + ",";
					} else {
						columns = columns + columnValue;
					}
				}	
			}
			
	        Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMapByColumns(tableName, columns);
	        if(dictMap.entrySet().size() != 0){
		        for(int i = 0; i < list.size(); i ++){
			        T entityBean = (T) type.newInstance(); // 实例化一个实体bean，对应一条记录
			        //BeanUtils.copy((T) list.get(i), entityBean);
			        entityBean = (T) list.get(i);
		        	Field[] beanfields = entityBean.getClass().getDeclaredFields();
		        	for(Field beanfield : beanfields){
			    		if (beanfield.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
			    			String columnName = columnMap.get(beanfield.getName());
			    			columnName = columnName.replaceAll("_", "").toUpperCase();
			    			if(dictMap != null && dictMap.get(columnName) != null){
				    			// 拿到该属性的gettet方法
							    Method getM = (Method) entityBean.getClass().getMethod("get" + getMethodName(beanfield.getName()));
						    	String val = (String) getM.invoke(entityBean);// 调用getter方法获取属性值
						    	if(dictMap.get(columnName).get(val) != null){// 拿到该属性的gettet方法
						    		Class parameterType = beanfield.getType();
								    Method setM = (Method) entityBean.getClass().getMethod("set" + getMethodName(beanfield.getName()), parameterType);
								    setM.invoke(entityBean, dictMap.get(columnName).get(val));
						    	}
			    			}
					    }
		        	}
		        	temp.add(entityBean);
		        }
	        }
	        return list;
		} else {
			return null;
		}
    }
	
    /**
     * 将List<T>里数据字典项转换为数据字典名输出
     * @param list 需要转换的 List<T> 此集合中的T为带数据库表字段的映射实体
     * @param type 转换实体类型
     * @return 返回的 List<T>
     * @throws Exception
     */
	public <T> T jpaBeanDictTran(T entityBean) throws Exception {
		if(entityBean != null){
			String tableName = JpaEntityUtils.getTableName(entityBean.getClass());
			Map<String, String> columnMap = JpaEntityUtils.getColumnsByEntity(entityBean.getClass());
	        String columns = "";
			if(columnMap != null){
				Set<Entry<String, String>> columnMapEntries = columnMap.entrySet();
				Iterator<Entry<String, String>> columnMapIterator = columnMapEntries.iterator();
				while(columnMapIterator.hasNext()){
					Map.Entry<String, String> columnMapEntry = (Entry<String, String>) columnMapIterator.next();
					String columnValue = columnMapEntry.getValue();
					if(columnMapIterator.hasNext()){
						columns = columns + columnValue + ",";
					} else {
						columns = columns + columnValue;
					}
				}	
			}
			
	        Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMapByColumns(tableName, columns);
	    	Field[] beanfields = entityBean.getClass().getDeclaredFields();
	    	for(Field beanfield : beanfields){
	    		if (beanfield.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
	    			String columnName = columnMap.get(beanfield.getName());
	    			columnName = columnName.replaceAll("_", "").toUpperCase();
	    			if(dictMap != null && dictMap.get(columnName) != null){
		    			// 拿到该属性的gettet方法
					    Method getM = (Method) entityBean.getClass().getMethod("get" + getMethodName(beanfield.getName()));
				    	String val = (String) getM.invoke(entityBean);// 调用getter方法获取属性值
				    	if(dictMap.get(columnName).get(val) != null){// 拿到该属性的gettet方法
				    		Class parameterType = beanfield.getType();
						    Method setM = (Method) entityBean.getClass().getMethod("set" + getMethodName(beanfield.getName()), parameterType);
						    setM.invoke(entityBean, dictMap.get(columnName).get(val));
				    	}
	    			}
			    }
	    	}
	        return entityBean;
		} else {
			return null;
		}
    }
	
	/**
	 * 根据机构编号获取机构名称
	 * @param orgid
	 * @return
	 */
	public String getOrgName(String orgid){
		Map<String, String> orgMap= this.codeUtil.getOrgMap();
		if(orgMap != null){
			return orgMap.get(orgid);
		}
		return null;
	}
	
	/**
	 * 根据员工编号获取员工名称
	 * @param empid
	 * @return
	 */
	public String getEmpName(String empid){
		Map<String, String> empMap= this.codeUtil.getEmpMap();
		if(empMap != null){
			return empMap.get(empid);
		}
		return null;
	}
	
	/**
	 * 根据渠道标识获取渠道名称
	 * @param empid
	 * @return
	 */
	public String getChannelName(String channelid){
		Map<String, String> channelMap= this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_CHANNELTYPE);;
		if(channelMap != null){
			if(channelMap.get(channelid) != null){
				return channelMap.get(channelid);
			}
		}
		return channelid;
	}
	
	/**
	 * 根据产品代码获取产品名称
	 * @param empid
	 * @return
	 */
	public String getProduceName(String procode){
		Map<String, String> productMap= this.codeUtil.getProductMap();
		if(productMap != null){
			return productMap.get(procode);
		}
		return procode;
	}
	
	// 把一个字符串的第一个字母大写、效率是最高的、
	private static String getMethodName(String fildeName) throws Exception{
		byte[] items = fildeName.getBytes(); 
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	/**
	 * 将List<Object> 对象通过查询SQL映射成List<Map<String, String>>
	 * @param list List<Object>对象
	 * @param selectsql 查询SQL
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> listObjectToListMaps1(List<Object[]> list , String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        cols = cols.toLowerCase();
        List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
        String[] colss = cols.split(",");
        if(colss.length > 1){
	        Object[] objjust = (Object[]) list.get(0);
	        if(objjust.length != colss.length){
	        	throw new Exception("查询语句列与查询结果列数量不符!");
	        }	
	        for(int j = 0; j < list.size(); j ++){
	        	Object[] obj = (Object[]) list.get(j);
				Map<String, String> map = new HashMap<String, String>();
	        	for(int i = 0; i < colss.length; i ++){
	        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
	        	}
	        	listMaps.add(map);
	        }
	        return listMaps;
        }else{	
	        for(Object obj : list){
				Map<String, String> map = new HashMap<String, String>();
        		map.put(colss[0], obj !=null ? obj.toString() :"");
        		listMaps.add(map);
	        }
	        return listMaps;
        }
    }
	
	public List<Map<String, String>> listObjectsToListMaps2(List<Object[]> list , String selectsql) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        //String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "createbranchno,brcname,cstaumtypedeclare,sumdaysumcust,cstscale1,cstscale2,cstscale3,sumdaysumaum,aumscale1,aumscale2,aumscale3";
        cols = cols.toLowerCase();
        List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
        String[] colss = cols.split(",");
        if(colss.length > 1){
	        Object[] objjust = list.get(0);
	        if(objjust.length != colss.length){
	        	throw new Exception("查询语句列与查询结果列数量不符!");
	        }	
	        for(Object[] obj : list){
				Map<String, String> map = new HashMap<String, String>();
	        	for(int i = 0; i < colss.length; i ++){
	        		map.put(colss[i], obj[i] !=null ? obj[i].toString() :"");
	        	}
        		listMaps.add(map);
	        }
	        return listMaps;
        }else{	
	        for(Object obj : list){
				Map<String, String> map = new HashMap<String, String>();
        		map.put(colss[0], obj !=null ? obj.toString() :"");
        		listMaps.add(map);
	        }
	        return listMaps;
        }
    }
	
	public static void main (String args[]){
		System.out.print("2013-12-20 10:12:50.944".substring(11,19));
	}
}
