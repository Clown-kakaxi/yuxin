package com.yuchengtech.bcrm.oneKeyAccount.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
/**
 * 将一个javaBean转换成Map
 * @author wx
 *
 */
public class ConvertBeanToMap {
	private String format8 = "yyyy-MM-dd";
	private String format16 = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat for8 = new SimpleDateFormat(format8);
	private SimpleDateFormat for16 = new SimpleDateFormat(format16);

	
	/**
	 * 将一个javaBean转换成Map(Date类型转换成yyyy-MM-dd的字符串   Timestamp转换成yyyy-MM-dd HH:mm:ss的字符串)
	 * @param o
	 * @param c
	 * @return
	 */
	public Map<String, Object> convert(Object o, Class<?> c) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			if (o == null || c == null) {
				return null;
			}
//			System.err.println("类名：【"+c.getSuperclass().getName()+"】");
//			// 获取父类，判断是否为实体类
//			if (c.getSuperclass().getName().indexOf("entity") >= 0) {
//				System.err.println("这是一个实体类");
//			}

			// 获取类中的所有定义字段
			Field[] fields = c.getDeclaredFields();

			// 循环遍历字段，获取字段对应的属性值
			for (Field field : fields) {
				// 如果不为空，设置可见性，然后返回
				field.setAccessible(true);
				String type = field.getType().getSimpleName();
				String name = field.getName();
				Object value = field.get(o);
				if(type.equals("Date") && value != null && !"".equals(value.toString())){
					value = this.for8.format(value);
				}else if(type.equals("Timestamp") && value != null && !"".equals(value.toString())){
					value = this.for16.format(value);
				}	
//				System.err.println("type="+type+"  name="+name+"  value="+value);
				retMap.put(name, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retMap;
	}
}
