/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����FixedSocketExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����1:44:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import java.io.IOException;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler;
import com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�FixedSocketExecutor
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����1:44:23
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����1:44:23
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FixedSocketExecutor extends SocketExecutor {

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // ��������ֽ���
	private static int headLength = ServerConfiger.getIntArg("headLength"); // ����ͷ����

	/**
	 * @���캯��
	 * @param decoder
	 */
	public FixedSocketExecutor() {
		super(new SocketRequestCodeHelper(headLength, maxBody));
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * createOutputDocument()
	 */
	@Override
	protected String createOutputDocument() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		String r=null;
		if(data.isSuccess()){
			FixedRequestCoderHandler coderHandler = (FixedRequestCoderHandler) SpringContextUtils
				.getBean("fixedRequestXmlHandler");
			r = coderHandler.responseXmlToFixedString(data);
		}else{
			r=data.getDetailDes();
		}
		
		int length = r.getBytes(charSet).length;
		buf.append(String.format("%08d", length));
		buf.append(String.format("%20s", data.getTxCode()));
		buf.append(String.format("%4s", "ECIF"));
		if(data.isSuccess()){
			buf.append(String.format("%20s", "SUCCESS"));
		}else{
			buf.append(String.format("%20s", "ERROR"));
		}
		buf.append(String.format("%20s", data.getRepStateCd()));
		buf.append(r);
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * createDefauteMsg(java.lang.String, java.lang.String)
	 */
	@Override
	protected String createDefauteMsg(String errorCode, String msg)
			throws IOException {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		int length=0;
		if(msg!=null){
			length = msg.getBytes(charSet).length;
		}
		buf.append(String.format("%08d", length));
		buf.append(String.format("%20s", "test"));
		buf.append(String.format("%4s", "ECIF"));
		buf.append(String.format("%20s", "ERROR"));
		buf.append(String.format("%20s", errorCode));
		if(length!=0){
			buf.append(msg);
		}
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#getEcifData
	 * ()
	 */
	@Override
	protected void getEcifData() throws Exception {
		// TODO Auto-generated method stub
		/****
		 * �����������������ϵͳ
		 * 
		 * �����������ʽ���ģ�����涨�ĸ�λ���ǽ����������ϵͳ
		 */
		data.setTxCode(decoder.getheaderValue("txCode"));
		data.setOpChnlNo(decoder.getheaderValue("opChnlNo"));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * beforeExecutor()
	 */
	@Override
	protected void beforeExecutor() throws Exception {
		// TODO Auto-generated method stub
		/****
		 * ����������XML���� ֱ�ӵ���resolvingXml
		 * 
		 * ���������������������綨��������Ҫת��
		 * ***/
		this.recvmsg = decoder.getBodyString();
		data.setPrimalMsg(recvmsg);
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		/**** ����XML���� ****/
		// resolvingXml();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * afterExecutor()
	 */
	@Override
	protected void afterExecutor() {
		// TODO Auto-generated method stub

	}

}
