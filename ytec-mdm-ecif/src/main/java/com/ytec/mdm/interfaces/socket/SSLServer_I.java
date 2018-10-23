/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����SSLServer_I.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-24-����3:03:53
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;
import java.nio.channels.SocketChannel;
import com.ytec.mdm.interfaces.socket.ssl.SslHandler;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SSLServer_I
 * @�������� nio SSL����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:45:47   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:45:47
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
class SSLServer_I extends Server_I{

	/**
	 * ���캯��
	 * 
	 */
	public SSLServer_I() {
		super();
	}
	/**
	 * ���캯��
	 * 
	 * @param poolsize �̳߳ش�С
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
			log.error("�ر�socket����",e);
		}
	}
	
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=(NioExecutor)clazz.newInstance();
		executor.init(session, this);
		return executor;
	}
	
	protected IoSession buildIoSession(SocketChannel client) throws Exception{
		log.error("��ȡSSL����ʧ��");
		throw new Exception("��ȡSSL����ʧ��");
	}
	
	protected void opSocketChannel(SocketChannel client)throws Exception{
		/**SSL����***/
		SslHandler sslHandler=((SSLIoSession)socketOpenMap.get(client.socket().getPort())).getSslHandler();
		if(!sslHandler.isHandshakeComplete()){
			sslHandler.doHandShake(client);
		}
	}

}
