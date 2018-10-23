package com.yuchengtech.emp.core.exception;

/*
 * 由于系统异常或程序错误导致的异常，当发生这种异常时，用户无法干预，只能由程序员处理。
 * 展现层只提示用户发生，系统异常，不显示详细信息。
 * 
 */
public class SystemException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public SystemException() {
	/* empty */
    }
    
    public SystemException(String msg) {
    	super(msg);
    }
    
    public SystemException(String msg, Throwable e) {
    	super(msg, e);
    }
    
    public SystemException(Throwable e) {
    	super(e);
    }
}