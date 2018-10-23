/**
 * @��Ŀ����ytec-mdm-sampleecif
 * @������com.ytec.sampleecif.interfaces
 * @�ļ�����SocketRequestCodeHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����1:46:35
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder;

/**
 * @��Ŀ���ƣ�ytec-mdm-sampleecif 
 * @�����ƣ�SocketRequestCodeHelper
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����1:46:35   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����1:46:35
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SocketRequestCodeHelper extends SocketRequestCoder{
	private static Logger log = LoggerFactory.getLogger(SocketRequestCodeHelper.class);
	/**
	 *@���캯�� 
	 * @param headLength
	 * @param maxBody
	 */
	public SocketRequestCodeHelper(int headLength, int maxBody) {
		super(headLength, maxBody);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder#readHeaders()
	 */
	@Override
	protected void readHeaders() throws Exception {
		// TODO Auto-generated method stub
		/*****
		 * ��Ҫ�涨����ͷ��ʽ
		 * ����
		 * ����ͷ����32�ֽ�[�����峤��(8�ֽ�)������(20�ֽ�)����ϵͳ(4�ֽ�)]���������Զ����ʽ
		 * ����ͷ�б��붨�屨���峤��,��������
		 * ***/
		if(this.headLength<=0){
			 log.error("����ͷ���ȶ������");
             throw new Exception("����ͷ���ȶ������");
		}
		charSet=MdmConstants.TX_XML_ENCODING;
		String bodyLength=new String(this.headBuffer,0,8,charSet);
		 try {
			 this.contentLength=Integer.parseInt(bodyLength.trim());
		 }catch (NumberFormatException e) {
			 log.error("���������峤�ȳ����쳣",e);
             throw new Exception("���������峤�ȳ��ȴ���");
         }
		 //this.headers.put("txCode", StringUtil.StringTrim(new String(this.headBuffer,8,20,charSet)));
		// this.headers.put("opChnlNo", StringUtil.StringTrim(new String(this.headBuffer,28,4,charSet)));
		
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketMsgCoder#encode(java.lang.String)
	 */
	@Override
	public String encode(String body) throws Exception {
		// TODO Auto-generated method stub
		return body;
	}

}
