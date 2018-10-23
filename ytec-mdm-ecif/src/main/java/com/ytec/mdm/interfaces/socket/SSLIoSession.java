/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：SSLIoSession.java
 * @版本信息：1.0.0
 * @日期：2014-4-3-下午2:13:57
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.interfaces.socket.ssl.SslHandler;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SSLIoSession
 * @类描述：SSL/TLS操作Session
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-3 下午2:13:57   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-3 下午2:13:57
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SSLIoSession extends IoSession{
	/**
	 * @属性名称:sslHandler
	 * @属性描述:SSL/TLS引擎
	 * @since 1.0.0
	 */
	private SslHandler sslHandler;
	public SSLIoSession(SocketChannel client) {
		super(client);
	}
	public SslHandler getSslHandler() {
		return sslHandler;
	}
	public void setSslHandler(SslHandler sslHandler) {
		this.sslHandler = sslHandler;
	}
}
