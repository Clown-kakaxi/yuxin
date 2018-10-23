/**
 * @项目名：ytec-mdm-sampleecif
 * @包名：com.ytec.sampleecif.interfaces
 * @文件名：SocketRequestCodeHelper.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午1:46:35
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder;

/**
 * @项目名称：ytec-mdm-sampleecif 
 * @类名称：SocketRequestCodeHelper
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午1:46:35   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午1:46:35
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SocketRequestCodeHelper extends SocketRequestCoder{
	private static Logger log = LoggerFactory.getLogger(SocketRequestCodeHelper.class);
	/**
	 *@构造函数 
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
		 * 需要规定报文头格式
		 * 例如
		 * 报文头长度32字节[报文体长度(8字节)交易码(20字节)操作系统(4字节)]或者其他自定义格式
		 * 报文头中必须定义报文体长度,其他随意
		 * ***/
		if(this.headLength<=0){
			 log.error("报文头长度定义错误");
             throw new Exception("报文头长度定义错误");
		}
		charSet=MdmConstants.TX_XML_ENCODING;
		String bodyLength=new String(this.headBuffer,0,8,charSet);
		 try {
			 this.contentLength=Integer.parseInt(bodyLength.trim());
		 }catch (NumberFormatException e) {
			 log.error("解析报文体长度长度异常",e);
             throw new Exception("解析报文体长度长度错误");
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
