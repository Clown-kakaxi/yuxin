/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����SocketRequestCodeHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����1:46:35
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�SocketRequestCodeHelper
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����1:46:35
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����1:46:35
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class SocketRequestCodeHelper extends SocketRequestCoder {
	private static Logger log = LoggerFactory.getLogger(SocketRequestCodeHelper.class);

	/**
	 * @���캯��
	 * @param headLength
	 * @param maxBody
	 */
	public SocketRequestCodeHelper(int headLength, int maxBody) {
		super(headLength, maxBody);
	}

	@Override
	protected void readHeaders() throws Exception {
		if (this.headLength <= 0) {
			log.error("����ͷ���ȶ������");
			throw new Exception("����ͷ���ȶ������");
		}
		charSet = MdmConstants.TX_XML_ENCODING;
		String bodyLength = new String(this.headBuffer, 0, this.headLength, charSet);
		try {
			this.contentLength = Integer.parseInt(bodyLength);
		} catch (NumberFormatException e) {
			log.error("ͨ��ǰ8λ��ʾ���ĳ��ȷ�ʽ���������峤�ȳ����쳣,������ǰ4λ��ʾ���ĳ��ȷ�ʽ��������");
			throw new Exception("���������峤�ȴ���");
		}
	}

	@Override
	public String encode(String body) throws Exception {
		// TODO Auto-generated method stub
		return body;
	}

}
