/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：SSLNioServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:45:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import com.ytec.mdm.interfaces.socket.ssl.SslContextFactory;
import com.ytec.mdm.interfaces.socket.ssl.SslHandler;
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SSLNioServer
 * @类描述： nio 监听
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:40   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:40
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SSLNioServer extends NioServer {
	private SslContextFactory sslContextFactory=null;
	/**
	 *@构造函数 
	 */
	public SSLNioServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		super.init(arg);
		/***密码加密? 可参照com.ytec.mdm.base.util.PropertyPlaceholderConfigurerExt****/
		sslContextFactory=new SslContextFactory((String)arg.get("keyPassWord"),(String)arg.get("keyStorePath"),(String)arg.get("trustPassWord"),(String)arg.get("trustStorePath"));
		if(arg.get("trustManager")!=null){
			sslContextFactory.setClientTrustManager((String)arg.get("trustManager"));
		}
		sslContextFactory.newInstance();
	}
	
	protected Server_I getServer_I(){
		return new SSLServer_I(this.poolSize);
	}
	protected IoSession buildIoSession(SocketChannel client,ServerSocketChannel server) throws IOException{
		SSLIoSession session= new SSLIoSession(client);
		SslHandler sslHandler=new SslHandler(sslContextFactory.getSslContext());
		sslHandler.init(false);
		session.setSslHandler(sslHandler);
		return session;
	}
}





