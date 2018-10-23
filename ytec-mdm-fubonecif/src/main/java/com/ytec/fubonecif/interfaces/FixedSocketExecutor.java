/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：FixedSocketExecutor.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午1:44:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：FixedSocketExecutor
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午1:44:23
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午1:44:23
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FixedSocketExecutor extends SocketExecutor {

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // 报文最大字节数
	private static int headLength = ServerConfiger.getIntArg("headLength"); // 报文头长度

	/**
	 * @构造函数
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
		 * 解析出交易码和请求系统
		 * 
		 * 如果是替他格式报文，必须规定哪个位置是交易码和请求系统
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
		 * 如果传输的是XML报文 直接调用resolvingXml
		 * 
		 * 如果传输的是替他报文例如定长报文需要转换
		 * ***/
		this.recvmsg = decoder.getBodyString();
		data.setPrimalMsg(recvmsg);
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		/**** 解析XML报文 ****/
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
