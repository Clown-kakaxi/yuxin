/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.http.client
 * @�ļ�����HttpsNioClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:47:53
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.https.client;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.ytec.mdm.interfaces.common.xmlcheck.RequestCheckSum;
import com.ytec.mdm.interfaces.socket.SSLNioClient;
import com.ytec.mdm.interfaces.socket.http.client.ResponseDecoder;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�HttpsNioClient
 * @��������HTTP�ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:47:53   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:47:53
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
@Scope("prototype")
public class HttpsNioClient extends SSLNioClient {
	private static Logger log = LoggerFactory.getLogger(HttpsNioClient.class);
	private ResponseDecoder decoder = new ResponseDecoder();
	private String url;

	/**
	 */
	public HttpsNioClient() {
	}

	public void init(Map arg) throws Exception{
		// TODO Auto-generated method stub
		super.init(arg);
		this.url = (String) arg.get("url");
	}

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
			log.info("���յ�:{}�ֽ�,http status {}",decoder.contentLength,decoder.getResStatus().getReasonPhrase());
			String receiveText = decoder.getBody(charset);
			if (receiveText != null) {
				responseMsg=new String(receiveText.getBytes(),charset);
			}
			return true;
		}else{
			return false;
		}
	}

}
