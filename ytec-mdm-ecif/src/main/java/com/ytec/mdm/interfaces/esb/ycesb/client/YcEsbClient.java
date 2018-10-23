/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.esb.ycesb2.client
 * @�ļ�����YcEsbClient2.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:41:55
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�YcEsbClient2
 * @�������� YC ESB2 �ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:41:56   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:41:56
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
			log.error("ESB2����ʧ��",e);
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
