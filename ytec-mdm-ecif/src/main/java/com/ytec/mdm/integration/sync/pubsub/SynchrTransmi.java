/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.pubsub
 * @文件名：SynchrTransmi.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:01:16
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.sync.pubsub;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IDispatchFun;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SynchrTransmi
 * @类描述：交易同步和转发
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:01:16   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:01:16
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public  abstract class SynchrTransmi implements IDispatchFun {
	private static Logger log = LoggerFactory.getLogger(SynchrTransmi.class);
	protected EcifData ecifData;
	/**
	 * @属性名称:asyn
	 * @属性描述:true:异步 ,不需要结果; false:同步,需要结果
	 * @since 1.0.0
	 */
	protected boolean asyn=false;
	/**
	 * @属性名称:clientName
	 * @属性描述:同步客户端
	 * @since 1.0.0
	 */
	protected String clientName;
	/**
	 * @属性名称:destSysNo
	 * @属性描述:发送系统
	 * @since 1.0.0
	 */
	protected String destSysNo;
	/**
	 * @属性名称:requestMsg
	 * @属性描述:同步报文
	 * @since 1.0.0
	 */
	protected String requestMsg;
	/**
	 * @属性名称:responseMsg
	 * @属性描述:同步报文
	 * @since 1.0.0
	 */
	protected String responseMsg;
	
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IDispatchFun#execute(com.ytec.mdm.base.bo.EcifData)
	 */
	public void execute(EcifData ecifData){
		this.ecifData=ecifData;
		log.info("生成同步报文");
		createSynchrMsg();
		if(!ecifData.isSuccess()){
			return ;
		}
		log.info("获取同步发送客户端");
		IClient client =  getSyncClient();
		if(client!=null){
			ClientResponse clientResponse = client.sendMsg(requestMsg);
			if(clientResponse.isSuccess()){
				responseMsg=clientResponse.getResponseMsg();
				if(responseMsg==null){
					ecifData.setStatus(ErrorCode.ERR_COMM_UNKNOWN_ERROR.getCode(),"同步发送交易失败");
					return;
				}
				if(!asyn){
					log.info("处理同步报文结果");
					reducedSynchrResult();
				}
			}else{
				log.error("客户端发送失败");
				ecifData.setStatus(ErrorCode.ERR_SYNCHRO_SEND_ERROR.getCode(),"客户端发送失败");
				return;
			}
		}else{
			ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),"获取同步发送客户端失败");
		}
	}
	/**
	 * @函数名称:createSynchrMsg
	 * @函数描述:生成同步报文 设置 同步客户端，发送系统
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public abstract void createSynchrMsg();
	/**
	 * @函数名称:reducedSynchrResult
	 * @函数描述:处理同步报文结果
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public abstract void reducedSynchrResult();
	
	
	/**
	 * @函数名称:getSyncClient
	 * @函数描述:获取同步发送客户端
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	private IClient getSyncClient(){
		IClient client = null;
		try {
			client = (IClient) SpringContextUtils.getBean(clientName);
		} catch (Exception e) {
			log.error("获取同步发送客户端失败", e);
		}
		if (client == null) {
			log.error("获取同步发送客户端{}失败", clientName);
			return null;
		} else {
			Map clientArg = ServerConfiger.getClientArg(
					clientName, destSysNo);
			if (clientArg == null) {
				log.error("获取同步发送客户端{}参数失败", clientName);
				return null;
			} else {
				try {
					client.init(clientArg);
				} catch (Exception e) {
					log.error("初始化同步发送客户端{}参数失败", clientName);
					return null;
				}
				return client;
			}
		}
	}

}
