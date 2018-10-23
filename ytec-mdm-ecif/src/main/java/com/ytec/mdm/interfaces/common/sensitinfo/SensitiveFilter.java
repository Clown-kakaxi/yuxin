/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.sensitinfo
 * @文件名：SensitiveFilter.java
 * @版本信息：1.0.0
 * @日期：2014-3-25-上午11:52:58
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.Map;
import java.util.Set;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SensitiveFilter
 * @类描述：敏感信息过滤接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-25 上午11:52:58   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-25 上午11:52:58
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface SensitiveFilter {
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void init(Map arg)throws Exception;
	/**
	 * @函数名称:doInforFilter
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param xmlStr 信息
	 * 		@param sensitInforSet  敏感信息定义
	 * 		@return  过滤后的信息
	 * @算法描述:
	 */
	public String doInforFilter(String str,Set sensitInforSet);
	
	/**
	 * @函数名称:isSensitiveInfo
	 * @函数描述:是否敏感信息
	 * @参数与返回说明:
	 * 		@param info
	 * 		@return
	 * @算法描述:
	 */
	public boolean isSensitiveInfo(String info);
}
