package com.yuchengtech.emp.core.sequence.dbaccess;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.yuchengtech.emp.core.sequence.Sequence;
import com.yuchengtech.emp.core.sequence.SequenceConstant;
import com.yuchengtech.emp.core.sequence.SequenceFactory;
import com.yuchengtech.emp.core.sequence.util.NumberBlock;

/**
 * Sequence Bean
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class DBSequence implements Sequence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Sequence Key */
	private String seqKey ;
	
	/** Rooling Key */
	private String roolingKey ;
	
	/** Minimum Value */
	private long minVal ;
	
	/** Maximum Value */
	private long maxVal ; 
	
	/** Start Value */
	private long startVal ;
	
	/** Incremental */
	private long incremental ;
	
	/** Next Value */
	private long cutOff ;
	
	/** Cyclical Or Not */
	private String cyclical ;
	
	/** Cycle Time */
	private int cycleTime ; // day,month,year.....
	
	/** Cache Size */
	private int cacheSize = -1;
	
	/** cache */
	private BlockingQueue<Long> cache = new LinkedBlockingQueue<Long>();

	/** createDate */
	private java.util.Date createDate ;
	
	/** lastEditDate */
	private java.util.Date lastEditDate ;
	
	
	/** Constructor */
	public DBSequence() { }
	
	
	/** TODO getters && setters */
	
	
	public String getSeqKey() {
		return seqKey;
	}

	public void setSeqKey(String seqKey) {
		this.seqKey = seqKey;
	}

	public String getRoolingKey() {
		return roolingKey;
	}

	public void setRoolingKey(String roolingKey) {
		this.roolingKey = roolingKey;
	}

	public long getMinVal() {
		return minVal;
	}

	public void setMinVal(long minVal) {
		this.minVal = minVal;
	}

	public long getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(long maxVal) {
		this.maxVal = maxVal;
	}

	public long getStartVal() {
		return startVal;
	}

	public void setStartVal(long startVal) {
		this.startVal = startVal;
	}

	public long getIncremental() {
		return incremental;
	}

	public void setIncremental(long incremental) {
		this.incremental = incremental;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}

	public int getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(int cycleTime) {
		this.cycleTime = cycleTime;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public BlockingQueue<Long> getCache() {
		return cache;
	}

	public void setCache(BlockingQueue<Long> cache) {
		this.cache = cache;
	}
	
	public long getCutOff() {
		return this.cutOff;
	}

	public void setCutOff(long cutOff) {
		this.cutOff = cutOff;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.util.Date getLastEditDate() {
		return this.lastEditDate;
	}
	
	public void setLastEditDate(java.util.Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	
	
	/** initial cache  */
	public void loadData(NumberBlock numBlock) {
		this.cache.addAll(numBlock.getFigures());
	}
	
	
	private SequenceFactory factory ;
	
	/*
	 * set SequenceFactory
	 */
	public void setFactory(SequenceFactory factory) {
		this.factory = factory;	
	}
	
	
//	@Override
	public long nextval() {
		java.util.Date currentDate = new java.util.Date();
		if(this.cache.isEmpty()) {
			this.factory.refill(this);
		}
		else if(this.getCyclical()!=null && 
				this.getCyclical().equals(SequenceConstant.BOOLEAN_TRUE)) {
			// 判断是否需要循环
			if(this.needCycle()) {
				this.factory.reCycle(this); // 更新数据库并重新取值
			}
		}
		Long retvlu = this.cache.poll();
		if(retvlu==null) {
			throw new NullPointerException("The Sequence ["+this.seqKey.concat("#").concat(this.roolingKey)+"] has no available value !");
		} else {
			this.setLastEditDate(currentDate); // 最后更新时间，实时更新
			return retvlu;
		}
	}
	
	
	/* if need cycle */
	public boolean needCycle() {
		java.util.Date currentDate = new java.util.Date();
		if(this.getCycleTime()==SequenceConstant.CYCLE_YEAR) {
			java.text.DateFormat format = new SimpleDateFormat("yyyy");
			if(format.format(currentDate).compareTo(format.format(this.lastEditDate))>0) {
				return true;
			}
		}
		else if(this.getCycleTime()==SequenceConstant.CYCLE_MONTH) {
			java.text.DateFormat format = new SimpleDateFormat("yyyyMM");
			if(format.format(currentDate).compareTo(format.format(this.lastEditDate))>0) {
				return true;
			}
		}
		else if(this.getCycleTime()==SequenceConstant.CYCLE_DATE) {
			java.text.DateFormat format = new SimpleDateFormat("yyyyMMdd");
			if(format.format(currentDate).compareTo(format.format(this.lastEditDate))>0) {
				return true;
			}
		}
		else if(this.getCycleTime()==SequenceConstant.CYCLE_HOUR_OF_DAY) {
			java.text.DateFormat format = new SimpleDateFormat("yyyyMMddHH");
			if(format.format(currentDate).compareTo(format.format(this.lastEditDate))>0) {
				return true;
			}
		}
		else if(this.getCycleTime()==SequenceConstant.CYCLE_MINUTE) {
			java.text.DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
			if(format.format(currentDate).compareTo(format.format(this.lastEditDate))>0) {
				return true;
			}
		}
		return false;
	}
	
}
