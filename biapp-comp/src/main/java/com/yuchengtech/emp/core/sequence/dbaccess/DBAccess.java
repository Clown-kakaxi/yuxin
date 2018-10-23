package com.yuchengtech.emp.core.sequence.dbaccess;

import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * 数据库操作接口
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface DBAccess {

	
	/** Query Sequence */
	public abstract Sequence fetchSequence(String seqKey);
	
	/** Query Sequence */
	public abstract Sequence fetchSequence(String seqKey, String roolingKey);
	
	/** Create A Sequence */
	public abstract Sequence createSequence(Sequence sequence) ;
	
	/** Remove A Sequence */
	public abstract void removeSequence(String seqKey, String roolingKey) ;
	
	/** Update */
	public abstract Sequence updateSequence(Sequence sequence) ;
	
	/** ReCycle */
	public abstract Sequence reCycleSequence(Sequence sequence) ;
	
}
