/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����OtherChannelExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-7-����5:44:06
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�OtherChannelExecutor
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-7 ����5:44:06   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-7 ����5:44:06
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class OtherChannelExecutor implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	private IWithChannel work;
	
	public OtherChannelExecutor(IWithChannel work) {
		this.work = work;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		work.otherChannelExe();
	}
	
}
