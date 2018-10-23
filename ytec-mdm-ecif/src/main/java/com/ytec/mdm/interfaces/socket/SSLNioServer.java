/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����SSLNioServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:45:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SSLNioServer
 * @�������� nio ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:45:40   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:45:40
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SSLNioServer extends NioServer {
	private SslContextFactory sslContextFactory=null;
	/**
	 *@���캯�� 
	 */
	public SSLNioServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		super.init(arg);
		/***�������? �ɲ���com.ytec.mdm.base.util.PropertyPlaceholderConfigurerExt****/
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





