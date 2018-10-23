/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ssl
 * @文件名：SslRecordException.java
 * @版本信息：1.0.0
 * @日期：2014-4-11-上午10:52:22
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import javax.net.ssl.SSLException;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SslRecordException
 * @类描述：安全通讯异常
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-11 上午10:52:22   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-11 上午10:52:22
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SslRecordException extends SSLException {

	/**
	 * @属性名称:serialVersionUID
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -3630688855274851694L;

	
	
	/**
	 *@构造函数 
	 * @param message
	 */
	public SslRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 *@构造函数 
	 * @param cause
	 */
	public SslRecordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 *@构造函数 
	 * @param message
	 * @param cause
	 */
	public SslRecordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
