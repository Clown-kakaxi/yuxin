package com.yuchengtech.emp.core.keygen.sequence;

import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * Sequence-Key-Generator
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface SequenceKeyGenerator {
	
	/**
	 * 获取序列的数字主键（形如：1,2,3.。。192等等）
	 * @param seqKey 序列的标识
	 * @return
	 */
	public abstract long getNumberKey(String seqKey) ;
	
	/**
	 * 获取序列的数字主键（形如：1,2,3.。。192等等）
	 * @param seqKey 序列的标识
	 * @param roolingKey 序列的卷标
	 * @return
	 */
	public abstract long getNumberKey(String seqKey, String roolingKey) ;
	
	/**
	 * 从给定的序列对象中获取序列的数字主键（形如：1,2,3.。。192等等）
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract long getNumberKey(Sequence sequence) ;
	
	/**
	 * 获取序列的字符串主键（形如：0001,0002,0003...0192等等）
	 * @param seqKey
	 * @return
	 */
	public abstract String getNumberFormatKey(String seqKey) ;
	
	/**
	 * 获取序列的字符串主键（形如：0001,0002,0003...0192等等）
	 * @param seqKey 序列的标识
	 * @param roolingKey 序列的卷标
	 * @return
	 */
	public abstract String getNumberFormatKey(String seqKey, String roolingKey) ;
	
	/**
	 * 从给定的序列对象中获取序列的字符串主键（形如：0001,0002,0003...0192等等）
	 * @param sequence 序列对象
	 * @return
	 */
	public abstract String getNumberFormatKey(Sequence sequence) ;
}
