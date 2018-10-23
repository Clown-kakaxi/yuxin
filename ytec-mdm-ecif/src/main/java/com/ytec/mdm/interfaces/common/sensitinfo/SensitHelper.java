/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.sensitinfo
 * @文件名：SensitHelper.java
 * @版本信息：1.0.0
 * @日期：2014-3-25-下午1:44:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ytec.mdm.base.util.StringUtil;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SensitHelper
 * @类描述：敏感信息帮助类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-25 下午1:44:23   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-25 下午1:44:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SensitHelper extends AbsSensitiveFilter{
	/**
	 * @属性名称:filter
	 * @属性描述:报文敏感信息过滤
	 * @since 1.0.0
	 */
	private SensitiveFilter filter;
	private static SensitHelper sensitHelper=new SensitHelper();
	/**
	 * @函数名称:getInstance
	 * @函数描述:单例获取
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public static SensitHelper getInstance(){
		return sensitHelper;
	}
	public void init(Map arg) throws Exception{
		String sensitFilterClass=(String)arg.get("sensitFilterClass");
		if(!StringUtil.isEmpty(sensitFilterClass)){
			filter=(SensitiveFilter)Class.forName(sensitFilterClass).newInstance();
			filter.init(arg);
		}
		super.initDbSensitive(arg);
		
	}
	/**
	 * @函数名称:doInforFilter
	 * @函数描述:过滤XML报文
	 * @参数与返回说明:
	 * 		@param str
	 * 		@param sensitInforSet
	 * 		@return
	 * @算法描述:
	 */
	public String doInforFilter(String str,Set sensitInforSet){
		if(filter==null){
			return str;
		}else{
			return filter.doInforFilter(str, sensitInforSet);
		}
	}
	
	/**
	 * @函数名称:isDbSensitiveInfor
	 * @函数描述:是否是数据库字段敏感信息
	 * @参数与返回说明:
	 * 		@param colum
	 * 		@return
	 * @算法描述:
	 */
	public boolean isDbSensitiveInfor(String colum){
		return this.sensitInforMap.contains(colum);
	}
	public boolean isXmlSensitiveInfor(String colum){
		if(filter==null){
			return false;
		}else{
			return filter.isSensitiveInfo(colum);
		}
	}

}
