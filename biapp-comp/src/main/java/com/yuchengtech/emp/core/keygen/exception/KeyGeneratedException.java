package com.yuchengtech.emp.core.keygen.exception;

import com.yuchengtech.emp.core.exception.ApplicationException;


/**
 * 生成key异常
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class KeyGeneratedException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public KeyGeneratedException(String errCode) {
		this(errCode, null, null,null);  
	}
	
	public KeyGeneratedException(String errCode, String message) {
		this(errCode, message, null,null);
	}
	
	public KeyGeneratedException(String errCode, Object[] params) {
		this(errCode, null, params,null);  
	}
	
	public KeyGeneratedException(String errCode, String message, Object[] params) {
		this(errCode, message, params, null);  
	}
	
	public KeyGeneratedException(String errCode, String message, Object[] params, Throwable e) {
		super(errCode, message, params, e);
	}
}
