/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.client
 * @文件名：SocketClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:53
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.client;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.socket.NioClient;

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
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Component
@Scope("prototype")
public class SocketClient extends NioClient {
	private static Logger log = LoggerFactory.getLogger(SocketClient.class);
	private CharsetDecoder decoder;
	private String lenFormat;

	/**
	 */
	public SocketClient() {
	}

	public void init(Map arg) throws Exception {
		super.init(arg);
		lenFormat = arg.get("lenFormat")==null?"":arg.get("lenFormat").toString();
		decoder = Charset.forName(charset).newDecoder();
	}

	@Override
	protected String packing(String sendmsg) throws Exception {
		StringBuffer msg = new StringBuffer();
		if (sendmsg == null || !(sendmsg.length() >= 0)) {
			Exception e = new Exception("数据同步错误，报文为空");
			log.error(e.getLocalizedMessage());
			throw e;
		}
		if (charset == null || "".equals(charset)) {
			charset = MdmConstants.TX_XML_ENCODING;
		}
		int length = sendmsg.getBytes(charset).length;
		msg.append(String.format(lenFormat, length));
		msg.append(sendmsg);
		return msg.toString();
	}

	@Override
	protected boolean decode() throws Exception {
		responseMsg = decoder.decode(receivebuffer).toString();
		return true;
	}

	@Override
	public ClientResponse sendMsg(String sendmsg) {
		log.info("[SocketClient]开始发送同步报文(继承父类方法)");
		return super.sendMsg(sendmsg);
	}
}
