/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：IoSession.java
 * @版本信息：1.0.0
 * @日期：2014-4-14-上午11:28:33
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IoSession
 * @类描述：nio 接入通信
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-14 上午11:28:33   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-14 上午11:28:33
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class IoSession{
	/**
	 * @属性名称:timeOut
	 * @属性描述:超时时间(ms)
	 * @since 1.0.0
	 */
	private static long timeOut=ServerConfiger.getIntArg("socketTimeOut");
	private SocketChannel client;
	/**
	 * @属性名称:lastAccessTime
	 * @属性描述:最后操作时间
	 * @since 1.0.0
	 */
	private long lastAccessTime;
	/**
	 *@构造函数 
	 * @param client
	 */
	public IoSession(SocketChannel client) {
		this.client = client;
		this.lastAccessTime = System.currentTimeMillis();
	}
	public SocketChannel getClient() {
		return client;
	}
	public void setClient(SocketChannel client) {
		this.client = client;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	/**
	 * @函数名称:isTimeOut
	 * @函数描述:是否超时
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public boolean isTimeOut(){
		long currTime = System.currentTimeMillis();
		return currTime-this.lastAccessTime>timeOut? true:false;
	}
	/**
	 * @函数名称:reSetAccessTime
	 * @函数描述:重置时间
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void reSetAccessTime(){
		this.lastAccessTime = System.currentTimeMillis();
	}

}
