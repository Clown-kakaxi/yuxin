/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:21:35
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-crm
 * @类名称：IEcifDealEngine
 * @类描述：交易引擎接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:21:35
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:21:35
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public interface IEcifDealEngine {
	/**
	 * @函数名称:execute
	 * @函数描述:交易引擎处理
	 * @参数与返回说明:
	 * 		@param data
	 * @算法描述:
	 */
	public void execute(EcifData data);

}
