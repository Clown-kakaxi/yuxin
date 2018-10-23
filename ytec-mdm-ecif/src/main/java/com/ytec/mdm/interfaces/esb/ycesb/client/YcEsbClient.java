/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.esb.ycesb2.client
 * @文件名：YcEsbClient2.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:41:55
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.esb.ycesb.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;

import spc.webos.endpoint.ESB2;
import spc.webos.endpoint.Executable;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：YcEsbClient2
 * @类描述： YC ESB2 客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:41:56   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:41:56
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
public class YcEsbClient implements IClient {
	private static Logger log = LoggerFactory.getLogger(YcEsbClient.class);
	private ESB2 esb2 = null;
	private int timeout;
	private String charset ;

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IClient#sendMsg(java.lang.String)
	 */
	public ClientResponse sendMsg(String msg) {
		ClientResponse clientResponse=new ClientResponse();
		try{
			Executable exe = new Executable();
			//String dt = SystemUtil.getInstance().getCurrentDate(SystemUtil.DF_APP8);
			exe.request =msg.getBytes(charset);
			exe.timeout = timeout;
			//exe.correlationID = (dt + "-" + seqNb).getBytes();
			esb2.execute(exe);
			clientResponse.setResponseMsg(new String(exe.response,charset));
			return clientResponse;
		}catch(Exception e){
			log.error("ESB2发送失败",e);
			clientResponse.setSuccess(false);
		}
		return clientResponse;
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IClient#init(java.util.Map)
	 */
	public void init(Map arg) throws Exception {
		esb2 = ESB2.getInstance();
		timeout=Integer.valueOf((String)arg.get("timeout"));
		charset=(String)arg.get("charset");
	}

}
