/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioProcessor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:46:30
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：NioProcessor
 * @类描述：NIO 服务接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:46:30   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:46:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface NioProcessor {
	/**
	 * @函数名称:stop
	 * @函数描述:停止
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void stop() ;
	/**
	 * @函数名称:start
	 * @函数描述:启动
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void start();
	/**
	 * @函数名称:registerChannel
	 * @函数描述:注册客户端
	 * @参数与返回说明:
	 * 		@param session
	 * @算法描述:
	 */
	public void registerChannel(IoSession session);
	/**
	 * @函数名称:removeOpenSocket
	 * @函数描述:移除客户端
	 * @参数与返回说明:
	 * 		@param client
	 * @算法描述:
	 */
	public void removeOpenSocket(SocketChannel client);
	/**
	 * @函数名称:isStop
	 * @函数描述:服务器是否停止
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public boolean isStop();
}
