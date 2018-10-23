/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：SocketRequestCodeHelper.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午1:46:35
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder;

/**
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：SocketRequestCodeHelper
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午1:46:35
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午1:46:35
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public class SocketRequestCodeHelper extends SocketRequestCoder {
	private static Logger log = LoggerFactory.getLogger(SocketRequestCodeHelper.class);

	/**
	 * @构造函数
	 * @param headLength
	 * @param maxBody
	 */
	public SocketRequestCodeHelper(int headLength, int maxBody) {
		super(headLength, maxBody);
	}

	@Override
	protected void readHeaders() throws Exception {
		if (this.headLength <= 0) {
			log.error("报文头长度定义错误");
			throw new Exception("报文头长度定义错误");
		}
		charSet = MdmConstants.TX_XML_ENCODING;
		String bodyLength = new String(this.headBuffer, 0, this.headLength, charSet);
		try {
			this.contentLength = Integer.parseInt(bodyLength);
		} catch (NumberFormatException e) {
			log.error("通过前8位表示报文长度方式解析报文体长度长度异常,重新以前4位表示报文长度方式解析报文");
			throw new Exception("解析报文体长度错误");
		}
	}

	@Override
	public String encode(String body) throws Exception {
		// TODO Auto-generated method stub
		return body;
	}

}
