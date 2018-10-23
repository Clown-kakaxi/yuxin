/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.http.client
 * @�ļ�����SocketClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:47:53
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.ytec.mdm.interfaces.socket.NioClient;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SocketClient
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
public class SocketClient extends NioClient {
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
	protected String packing(String sendmsg) throws Exception {
		StringBuffer msg = new StringBuffer();
		int length=sendmsg.getBytes(charset).length;
		msg.append(String.format("%08d", length));
		msg.append(sendmsg);
		return msg.toString();
	}

	@Override
	protected boolean decode() throws Exception {
		// TODO Auto-generated method stub
			responseMsg=decoder.decode(receivebuffer).toString();
			return true;
	}

}
