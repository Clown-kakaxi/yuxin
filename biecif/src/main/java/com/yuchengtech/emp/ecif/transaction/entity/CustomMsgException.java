package com.yuchengtech.emp.ecif.transaction.entity;

import org.springframework.core.NestedCheckedException;

public class CustomMsgException extends NestedCheckedException {

	public CustomMsgException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
