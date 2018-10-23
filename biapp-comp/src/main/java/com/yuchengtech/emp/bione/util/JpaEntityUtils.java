/**
 * 
 */
package com.yuchengtech.emp.bione.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * <pre>
 * Title:获取jpa实体的基本信息
 * Description: 获取物理表名，获取字段对应物理字段名等
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JpaEntityUtils {

	/**
	 * 获取jpa实体所映射的物理表名
	 */
	public static String getTableName(Class<?> jpaBean) {
		String tableName = "";
		if (jpaBean != null) {
			Table t = jpaBean.getAnnotation(Table.class);
			if (t != null) {
				tableName = t.name();
			}
		}
		return tableName;
	}

	/**
	 * 获取jpa实体指定列(无视private/protected)所映射的物理列名
	 */
	public static String getColumnName(Class<?> jpaBean, String columnName) {
		String name = "";
		if (jpaBean != null && columnName != null && !"".equals(columnName)) {
			Field f = null;
			try {
				f = jpaBean.getDeclaredField(columnName);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			if (f != null) {
				Column c = f.getAnnotation(Column.class);
				if (c != null && c.name() != null && !"".equals(c.name())) {
					name = c.name();
				} else {
					name = f.getName();
				}
			}
		}
		return name;
	}

	/**
	 * 获取jpa实体所有列(无视private/protected)所映射的物理列名
	 * 返回map(key->fieldName,value->columnName)
	 */
	public static Map<String, String> getColumnsByEntity(Class<?> jpaBean) {
		Map<String, String> columns = new HashMap<String, String>();
		if (jpaBean != null) {
			Field[] fs = jpaBean.getDeclaredFields();
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					int modify = fs[i].getModifiers();
					if(modify == 26){
						//if field -> private(2) + static(8) + final(16)
						continue;
					}
					String fieldName = fs[i].getName();
					if (columns.containsKey(fieldName)) {
						continue;
					}
					String columnName = "";
					Column c = fs[i].getAnnotation(Column.class);
					if (c != null && c.name() != null && !"".equals(c.name())) {
						columnName = c.name();
					} else {
						columnName = fieldName;
					}
					columns.put(fieldName, columnName);
				}
			}
		}
		return columns;
	}
}
