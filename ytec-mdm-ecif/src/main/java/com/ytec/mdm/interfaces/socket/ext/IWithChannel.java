/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����IWithChannel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-7-����5:32:59
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IWithChannel
 * @����������ͨ����ִ�нӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-7 ����5:32:59   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-7 ����5:32:59
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IWithChannel {
	/**
	 * @��������:asserOtherChannel
	 * @��������:�϶��ý����Ƿ��ύ����һ��ͨ��
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public boolean asserOtherChannel();
	/**
	 * @��������:otherChannelExe
	 * @��������:��һ��ͨ��ִ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void otherChannelExe();
}
