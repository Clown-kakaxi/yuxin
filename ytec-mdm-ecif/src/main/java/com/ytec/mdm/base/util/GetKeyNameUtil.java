/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：GetKeyNameUtil.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:13:53
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;
import java.util.HashMap;
import java.util.Map;
import com.ytec.mdm.base.dao.JPAAnnotationMetadataUtil;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：GetKeyNameUtil
 * @类描述： 获得主键名称工具
 * @功能描述:获得POJO对象，表的主键
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:14:02
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:14:02
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class GetKeyNameUtil {

	/**
	 * @属性名称:entityKeyMap
	 * @属性描述:实体的主键对应关系，支持单主键，联合主键不支持，应为JPA的复合主键需要新建XXXid的对象，操作复杂，需要客户化
	 * @since 1.0.0
	 */
	private Map<String, String> entityKeyMap = new HashMap<String, String>();
	/**
	 * @属性名称:metadataUtil
	 * @属性描述:数据库实体帮助类
	 * @since 1.0.0
	 */
	private JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
	
	private static GetKeyNameUtil getKeyNameUtil=new GetKeyNameUtil();
	
	public static GetKeyNameUtil getInstance(){
		return getKeyNameUtil;
	}

	/**
	 * @函数名称:getKeyName
	 * @函数描述:获得POJO对象，表的主键
	 * @参数与返回说明:
	 * @param obj
	 * @return
	 * @算法描述:
	 */
	public String getKeyName(Object obj) {
		String keyName = null;
		String objName = obj.getClass().getSimpleName();
		if ((keyName = entityKeyMap.get(objName)) != null) {
			return keyName;
		} else {
			keyName=metadataUtil.getIdPropertyName(obj);
			if(keyName!=null){
				entityKeyMap.put(objName, keyName);
			}
			return keyName;
		}
	}

}
