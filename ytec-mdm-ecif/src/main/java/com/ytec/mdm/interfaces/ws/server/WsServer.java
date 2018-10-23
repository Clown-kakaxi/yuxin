/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.ws.server
 * @文件名：WsServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:49:08
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：WsServer
 * @类描述：WEB SERVICE 服务端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:49:09   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:49:09
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class WsServer implements IServer {
	private static Logger log = LoggerFactory.getLogger(WsServer.class);
	private final String DEFAULT_PTL = "http://";   //默认协议
	private final String DEFAULT_PORT = "9000";     //默认端口

	/**
	 * @属性名称:protocol
	 * @属性描述:协议
	 * @since 1.0.0
	 */
	private String protocol;
	// server IP address
	/**
	 * @属性名称:ip
	 * @属性描述:发布IP地址
	 * @since 1.0.0
	 */
	private String ip;
	// service port
	/**
	 * @属性名称:port
	 * @属性描述:发布端口
	 * @since 1.0.0
	 */
	private String port;
	// url
	/**
	 * @属性名称:url
	 * @属性描述:URL地址
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
