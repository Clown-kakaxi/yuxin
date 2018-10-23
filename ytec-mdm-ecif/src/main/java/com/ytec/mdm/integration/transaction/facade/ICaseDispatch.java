/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：ICaseDispatch.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:19:32
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ICaseDispatch
 * @类描述：分支判别接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:19:32   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:19:32
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface ICaseDispatch {
	/**
	 * @函数名称:decide
	 * @函数描述:分支判别
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@return
	 * @算法描述:
	 */
	public String decide(EcifData ecifData);
}
