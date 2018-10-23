package com.yuchengtech.emp.core.keygen;

import com.yuchengtech.emp.core.sequence.Sequence;



/**
 * Key Generator interface
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface KeyGenerator {

	
	/* UUID */
	public abstract String getUuidKey() ;
	
	/* UUID */
	public abstract String getUuidKey(String str) ;
	
	
	/* 时间戳 */
	public abstract String getTimeFormatKey(Sequence sequence) ;
	
	/* 时间戳 */
	public abstract String getTimeFormatKey(java.util.Date date, Sequence sequence) ;
	
	/* 时间戳 + 顺序号  */
	public abstract String getTimeFormatKey(String timeFormat, Sequence sequence) ;
	
	
	public abstract String getTimeFormatKey(String timeFormat, java.util.Date date, Sequence sequence) ;
	
	
	/* 序列格式的Key */
	public abstract long getNumberKey(String seqKey) ;
	
	public abstract long getNumberKey(String seqKey, String roolingKey) ;
	
	public abstract long getNumberKey(Sequence sequence) ;
	
	public abstract String getNumberFormatKey(String seqKey) ;
	
	public abstract String getNumberFormatKey(String seqKey, String roolingKey) ;
	
	public abstract String getNumberFormatKey(Sequence sequence) ;
	
}
