package com.yuchengtech.emp.core.sequence;

/**
 * Sequence Interface
 * 
 * @version 1.0
 * @since 1.0.1
 */
public abstract interface Sequence extends java.io.Serializable {
	
	/*  seq-key */
	public abstract String getSeqKey() ;
	
	/* rooling-key */
	public abstract String getRoolingKey() ;
	
	/* Start-Value */
	public abstract long getStartVal()  ;
	
	/* Incremental */
	public abstract long getIncremental() ;
	
	/* Minimum */
	public abstract long getMinVal() ;
	
	/* Maximum */
	public abstract long getMaxVal();
	
	/* Cyclical */
	public abstract String getCyclical();
	
	/* Cycle-Time */
	public abstract int getCycleTime();

	/* CacheSize */
	public abstract int getCacheSize();
	
	/* Cut-Off */
	public long getCutOff();
	
	
	/*
	 * set SequenceFactory
	 */
	public abstract void setFactory(SequenceFactory factory) ;
	
	/*
	 * 获取序列的下一个值
	 * @return
	 * Date：2013-2-4下午3:49:23
	 */
	public abstract long nextval();
	
}
/*
CREATE TABLE empafx_sequence (
   seqkey VARCHAR(128) NOT NULL,
   roolingkey VARCHAR(64) NOT NULL,
   startval NUMERIC(16) DEFAULT 1,
   minval NUMERIC(16),
   maxval NUMERIC(16),
   incremental NUMERIC(2),
   cutoff NUMERIC(16),
   cachesize NUMERIC(3),
   cyclical CHAR(1),
   cycletime CHAR(1),
   createdate datetime,
   lasteditdate datetime,
   CONSTRAINT pk PRIMARY KEY (seqkey, roolingkey)
);
 */