/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.ws.server
 * @�ļ�����WsServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:49:08
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.ws.server;

import java.util.Map;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.IServer;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�WsServer
 * @��������WEB SERVICE �����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:49:09   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:49:09
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class WsServer implements IServer {
	private static Logger log = LoggerFactory.getLogger(WsServer.class);
	private final String DEFAULT_PTL = "http://";   //Ĭ��Э��
	private final String DEFAULT_PORT = "9000";     //Ĭ�϶˿�

	/**
	 * @��������:protocol
	 * @��������:Э��
	 * @since 1.0.0
	 */
	private String protocol;
	// server IP address
	/**
	 * @��������:ip
	 * @��������:����IP��ַ
	 * @since 1.0.0
	 */
	private String ip;
	// service port
	/**
	 * @��������:port
	 * @��������:�����˿�
	 * @since 1.0.0
	 */
	private String port;
	// url
	/**
	 * @��������:url
	 * @��������:URL��ַ
	 * @since 1.0.0
	 */
	private String url;

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IServer#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
		log.info("!>web service: {} [STOP] ",url);
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IServer#start()
	 */
	public void start() {
		// TODO Auto-generated method stub
		TxDealWebService implementor = new TxDealWebServiceImpl();
		Endpoint.publish(url, implementor);
		log.info("!>web service: {} [START] ",url);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IServer#init(java.util.Map)
	 */
	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		String tProtocol;
		String tIp;
		String tPort;
		tProtocol = (String) arg.get("webServiceProtocol");
		if (tProtocol != null && !tProtocol.equals(""))
			this.protocol = tProtocol;
		else
			this.protocol = this.DEFAULT_PTL;

		tIp = (String) arg.get("webServiceIp");
		if (tIp != null && !tIp.equals("")) {
			this.ip = tIp;
		} else {
			this.ip = StringUtil.getLocalIp();
		}

		tPort = (String) arg.get("webServicePort");
		if (tPort != null && !tPort.equals("")) {
			this.port = tPort;
		} else {
			this.port = this.DEFAULT_PORT;
		}
		url = protocol + ip + ":" + port + "/" + "txDealWebService";
	}

}
