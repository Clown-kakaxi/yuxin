/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：SSLServer_I.java
 * @版本信息：1.0.0
 * @日期：2014-4-24-下午3:03:53
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;
import java.nio.channels.SocketChannel;
import com.ytec.mdm.interfaces.socket.ssl.SslHandler;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SSLServer_I
 * @类描述： nio SSL服务
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:47   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:47
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
class SSLServer_I extends Server_I{

	/**
	 * 构造函数
	 * 
	 */
	public SSLServer_I() {
		super();
	}
	/**
	 * 构造函数
	 * 
	 * @param poolsize 线程池大小
	 */
	public SSLServer_I(int poolsize) {
		super(poolsize);
	}
	
	public void removeOpenSocket(SocketChannel client){
		try{
			int port=client.socket().getPort();
			SSLIoSession session=null;
			if((session=(SSLIoSession)socketOpenMap.get(port))!=null){
				session.getSslHandler().destroy(client);
				socketOpenMap.remove(port);
			}
		}catch(Exception e){
			log.error("关闭socket错误",e);
		}
	}
	
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=(NioExecutor)clazz.newInstance();
		executor.init(session, this);
		return executor;
	}
	
	protected IoSession buildIoSession(SocketChannel client) throws Exception{
		log.error("获取SSL对象失败");
		throw new Exception("获取SSL对象失败");
	}
	
	protected void opSocketChannel(SocketChannel client)throws Exception{
		/**SSL握手***/
		SslHandler sslHandler=((SSLIoSession)socketOpenMap.get(client.socket().getPort())).getSslHandler();
		if(!sslHandler.isHandshakeComplete()){
			sslHandler.doHandShake(client);
		}
	}

}
