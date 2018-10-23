/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @�ļ�����IFixedRequestCoder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-17-����2:31:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IFixedRequestCoder
 * @������������������XML����ת���ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-17 ����2:31:25   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-17 ����2:31:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IFixedRequestCoder {
	/**
	 * @��������:requestFixedStringToXml
	 * @��������:��������ת����ƽ��XML
	 * @�����뷵��˵��:
	 * 		@param data
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void requestFixedStringToXml(EcifData data) throws Exception;
	/**
	 * @��������:responseXmlToFixedByte
	 * @��������:ƽ��XMLת���ɶ����ֽ���
	 * @�����뷵��˵��:
	 * 		@param data
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public byte[] responseXmlToFixedByte(EcifData data) throws Exception;
	
	
	/**
	 * @��������:responseXmlToFixedString
	 * @��������:ƽ��XMLת���ɶ�������
	 * @�����뷵��˵��:
	 * 		@param data
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public String responseXmlToFixedString(EcifData data) throws Exception;
}
