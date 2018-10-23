/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.client
 * @文件名：SocketClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:53
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.yuchengtech.emp.ecif.base.nio;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SocketClient
 * @类描述：HTTP客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:47:53   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:47:53
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
@Scope("prototype")
public class SocketClient extends NIOClient {
	private static Logger log = LoggerFactory.getLogger(SocketClient.class);
	private CharsetDecoder decoder;  

	/**
	 */
	public SocketClient() {
	}

	public void init(Map arg) throws Exception{
		// TODO Auto-generated method stub
		super.init(arg);
		 decoder = Charset.forName(charset).newDecoder();
	}

	@Override
	public String packing(String sendmsg) throws Exception {
		StringBuffer msg = new StringBuffer();
		int length=sendmsg.getBytes(charset).length;
		msg.append(String.format("%08d", length));
		msg.append(sendmsg);
		return msg.toString();
	}

	@Override
	public boolean decode() throws Exception {
		// TODO Auto-generated method stub
			responseMsg=decoder.decode(receivebuffer).toString();
			responseMsg = responseMsg.substring(8);
			return true;
			
	}
	
	public String receiveMsg() throws Exception {
		// TODO Auto-generated method stub
			responseMsg=decoder.decode(receivebuffer).toString().substring(8);
			return responseMsg;
			
	}

}
