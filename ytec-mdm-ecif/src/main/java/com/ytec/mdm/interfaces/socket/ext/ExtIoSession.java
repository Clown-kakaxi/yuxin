/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：ExtIoSession.java
 * @版本信息：1.0.0
 * @日期：2014-4-24-下午1:24:53
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.interfaces.socket.IoSession;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ExtIoSession
 * @类描述：socket 扩展Session
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-24 下午1:24:53   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-24 下午1:24:53
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ExtIoSession extends IoSession {
	/**
	 * @属性名称:serverAcceptPort
	 * @属性描述:服务端接入端口
	 * @since 1.0.0
	 */
	private int serverAcceptPort;
	/**
	 * @属性名称:clazz
	 * @属性描述:协议解析适配器
	 * @since 1.0.0
	 */
	private Class clazz;

	/**
	 *@构造函数 
	 * @param client
	 */
	public ExtIoSession(SocketChannel client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/**
	 *@构造函数 
	 * @param client
	 * @param serverAcceptPort
	 */
	public ExtIoSession(SocketChannel client, int serverAcceptPort) {
		super(client);
		this.serverAcceptPort=serverAcceptPort;
		// TODO Auto-generated constructor stub
	}

	public int getServerAcceptPort() {
		return serverAcceptPort;
	}

	public void setServerAcceptPort(int serverAcceptPort) {
		this.serverAcceptPort = serverAcceptPort;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	

}
