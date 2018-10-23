package com.yuchengtech.emp.core.keygen.uuid;

/**
 * UUID-Key-Generator
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface UUIDKeyGenerator {


	/**
	 * 获取UUID-Key
	 */
	public abstract String getUuidKey() ;
	
	/**
	 * 从已知字符串获取UUID-Key
	 */
	public abstract String getUuidKey(String src) ;
	
}
