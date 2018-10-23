/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.ptsync
 * @文件名：ISynchroExecute.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:59:06
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ISynchroExecute
 * @类描述：同步处理接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:59:06   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:59:06
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface ISynchroExecute {
	/**
	 * @函数名称:execute
	 * @函数描述:同步请求报文处理
	 * @参数与返回说明:
	 * 		@param txSyncConf
	 * 		@param txEvtNotice
	 * 		@return
	 * @算法描述:
	 */
	public boolean execute(TxSyncConf txSyncConf,TxEvtNotice txEvtNotice);
	/**
	 * @函数名称:executeResult
	 * @函数描述:同步响应报文处理
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public boolean executeResult();
}
