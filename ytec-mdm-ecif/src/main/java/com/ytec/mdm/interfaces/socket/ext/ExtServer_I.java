/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����ExtServer_I.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-24-����3:43:06
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.nio.channels.SocketChannel;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.Server_I;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ExtServer_I
 * @�������� nio ����
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
public class ExtServer_I extends Server_I{

	/**
	 * ���캯��
	 * 
	 */
	public ExtServer_I() {
		super();
	}
	/**
	 * ���캯��
	 * 
	 * @param poolsize �̳߳ش�С
	 */
	public ExtServer_I(int poolsize) {
		super(poolsize);
	}
	
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=null;
		Class clazz=((ExtIoSession)session).getClazz();
		if(clazz!=null){
			executor=(NioExecutor)clazz.newInstance();
		}else{
			executor=(NioExecutor)this.clazz.newInstance();
		}
		executor.init(session, this);
		return executor;
	}
	
	protected IoSession buildIoSession(SocketChannel client) throws Exception{
		return new ExtIoSession(client);
	}
}