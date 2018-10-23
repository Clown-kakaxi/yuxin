/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc
 * @文件名：SynchrTransmiHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:07:38
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.integration.sync.pubsub.SynchrTransmi;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：SynchrTransmiHandler
 * @类描述：数据同步案例
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:07:38   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:07:38
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SynchrTransmiHandler extends SynchrTransmi {
	private static Logger log = LoggerFactory
			.getLogger(SynchrTransmiHandler.class);

	@Override
	public void createSynchrMsg() {
		// TODO Auto-generated method stub
		asyn = false; 		  // 设置处理同步和异步 ，true:异步 ,不需要结果; false:同步,需要结果
		clientName = "httpClient";  // 同步客户端
		destSysNo = "01";     // 发送系统
		
		/*********将原报文转发**********/
		requestMsg=ecifData.getPrimalMsg();
	}

	@Override
	public void reducedSynchrResult() {
		// TODO Auto-generated method stub
		/*********操作响应报文**********/
		log.info("操作响应报文");
		log.info(responseMsg);

	}

}
