/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.client
 * @文件名：HttpClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:53
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.http.client;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.ytec.mdm.interfaces.common.xmlcheck.RequestCheckSum;
import com.ytec.mdm.interfaces.socket.NioClient;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：HttpClient
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
public class HttpClient extends NioClient {
	private static Logger log = LoggerFactory.getLogger(HttpClient.class);
	/**
	 * @属性名称:decoder
	 * @属性描述:http解析器
	 * @since 1.0.0
	 */
	private ResponseDecoder decoder = new ResponseDecoder();
	/**
	 * @属性名称:url
	 * @属性描述:URL
	 * @since 1.0.0
	 */
	private String url;

	/**
	 */
	public HttpClient() {
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioClient#init(java.util.Map)
	 */
	public void init(Map arg) throws Exception{
		// TODO Auto-generated method stub
		super.init(arg);
		this.url = (String) arg.get("url");
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioClient#packing(java.lang.String)
	 */
	@Override
	protected String packing(String sendmsg) throws Exception {
		StringBuffer msg = new StringBuffer();
		msg.append("POST /" + url + " HTTP/1.1\r\n");
		msg.append("Accept: */*\r\n");
		msg.append("Accept-Language: zh-cn\r\n");
		msg.append("User-Agent: ecif\r\n");
		msg.append("Host: localhost\r\n");
		String checkSum = RequestCheckSum.CheckReponseSum(sendmsg);
		msg.append("Content-MD5: " + checkSum + "\r\n");
		msg.append("Connection: Keep-Alive\r\n");
		msg.append("Content-type: application/xml; charset="
				+ charset + "\r\n");
		msg.append("Content-Length: "+ sendmsg.getBytes(charset).length + "\r\n");
		msg.append("\r\n");
		msg.append(sendmsg);
		return msg.toString();
	}

	@Override
	protected boolean decode() throws Exception {
		// TODO Auto-generated method stub
		if(decoder.decode(receivebuffer)){
			log.info("接收到:{}字节,http status {}",decoder.contentLength,decoder.getResStatus().getReasonPhrase());
			responseMsg = decoder.getBody(charset);
			return true;
		}else{
			return false;
		}
	}

}
