/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����NioServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:45:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import com.ytec.mdm.interfaces.socket.NioServer;
import com.ytec.mdm.interfaces.socket.Server_I;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�NioServer
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
public class NioFlowControlServer extends NioServer{
	/***
	 * ���캯��
	 */
	public NioFlowControlServer(){
		super();
	}
	
	protected Server_I getServer_I(){
		return new FlowServer_I();
	}
}





