/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：NameUtil.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:30:40
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：NameUtil
 * @类描述：java名称帮助类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:30:41   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:30:41
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class NameUtil {
	
	private static final String METHOD_S ="s";
	
	/**
	 * @函数名称:getNameToJava
	 * @函数描述:将名称改成java规范
	 * @参数与返回说明:
	 * 		@param name
	 * 		@return
	 * @算法描述:
	 */
	public static String getNameToJava(String name){
		name = name.toUpperCase();
		String[] codeStrs = name.split("_");
		String str = new String();
		// 是否有“_”
		if (codeStrs.length > 1) {
			for (int i = 1; i < codeStrs.length; i++) {
				// 将数据中的下划线剔除，并将从第二个单词开始的首字母大写
				str = str + codeStrs[i].substring(0, 1)
						+ codeStrs[i].substring(1).toLowerCase();
			}
		} else {
			str = name.substring(0, 1)
					+ name.substring(1).toLowerCase();
		}
		return str;
	}
	
	/**
	 * @函数名称:getColumToJava
	 * @函数描述:将字段名称改成java规范
	 * @参数与返回说明:
	 * 		@param name
	 * 		@return
	 * @算法描述:
	 */
	public static String getColumToJava(String name){
		if(name==null){
			return null;
		}
		name = name.toLowerCase();
		String[] codeStrs = name.split("_");
		// 是否有“_”
		if (codeStrs.length > 1) {
			String str=codeStrs[0];
			for (int i = 1; i < codeStrs.length; i++) {
				// 将数据中的下划线剔除，并将从第二个单词开始的首字母大写
				str = str + codeStrs[i].substring(0, 1).toUpperCase()
						+ codeStrs[i].substring(1);
			}
			return str;
		} else {
			return name;
		}
	}
	
	
	/**
	 * @函数名称:getJavaToColum
	 * @函数描述:将JAVA属性转成字段大写
	 * @参数与返回说明:
	 * 		@param name
	 * 		@return
	 * @算法描述:
	 */
	public static String getJavaToColum(String name){
		
		if(name==null){
			return null;
		}
		StringBuffer str = new StringBuffer();
		char[] chars=name.toCharArray();
		for(char c:chars){
			if(Character.isUpperCase(c)){
				str.append('_');
			}
			str.append(c);
		}
		return str.toString().toUpperCase();
	}
	
	
	public static String toSetMethod(String name){
		return NameUtil.METHOD_S + name.substring(1); 
	}
}
