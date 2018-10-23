/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IModelFilter.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:22:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：IModelFilter
 * @类描述：写交易模型过滤接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:22:14
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:22:14
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public interface IModelFilter {
	/**
	 * @函数名称:execute
	 * @函数描述:实体过滤
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param entity
	 * 		@return
	 * @算法描述:
	 */
	public boolean execute(EcifData ecifData,Object entity);
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void init(String ruleExpr);

}
