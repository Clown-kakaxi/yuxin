/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IMsgNodeFilter.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:22:54
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IMsgNodeFilter
 * @类描述：规则过滤接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:22:54   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:22:54
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IMsgNodeFilter {
	/**
	 * @函数名称:execute
	 * @函数描述:校验转换
	 * @参数与返回说明:
	 * 		@param expression
	 * 		@param value
	 * 		@param params
	 * 		@return
	 * @算法描述:
	 */
	public String execute(Object expression, String value,String... params);
}
