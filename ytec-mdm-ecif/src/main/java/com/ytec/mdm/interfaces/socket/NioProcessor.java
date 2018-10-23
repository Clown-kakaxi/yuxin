/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����NioProcessor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:46:30
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�NioProcessor
 * @��������NIO ����ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:46:30   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:46:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface NioProcessor {
	/**
	 * @��������:stop
	 * @��������:ֹͣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void stop() ;
	/**
	 * @��������:start
	 * @��������:����
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void start();
	/**
	 * @��������:registerChannel
	 * @��������:ע��ͻ���
	 * @�����뷵��˵��:
	 * 		@param session
	 * @�㷨����:
	 */
	public void registerChannel(IoSession session);
	/**
	 * @��������:removeOpenSocket
	 * @��������:�Ƴ��ͻ���
	 * @�����뷵��˵��:
	 * 		@param client
	 * @�㷨����:
	 */
	public void removeOpenSocket(SocketChannel client);
	/**
	 * @��������:isStop
	 * @��������:�������Ƿ�ֹͣ
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isStop();
}
