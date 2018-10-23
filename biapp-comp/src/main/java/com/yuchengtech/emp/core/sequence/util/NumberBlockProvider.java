package com.yuchengtech.emp.core.sequence.util;

import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * NumberBlock-Provider 
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class NumberBlockProvider {

	/* Instance */
	private static NumberBlockProvider instance ;
	
	private NumberBlockProvider() {} 
	
	public static NumberBlockProvider getInstance() {
		if(instance==null) {
			instance = new NumberBlockProvider();
		}
		return instance;
	}
	
	
	/* get NumberBlock Instance */
	public NumberBlock getNumberBlock(Sequence sequence) {
		return this.initialize(sequence);
	}
	
	
	/* Initialize Instance */
	private NumberBlock initialize(Sequence sequence) {
		long step = sequence.getIncremental();
		long min = this.computeMin(sequence.getCutOff(), sequence.getStartVal(), sequence.getMinVal());
		long max = this.computeMax(sequence.getCutOff(), sequence.getCacheSize(), sequence.getIncremental(), sequence.getMaxVal());
		return new NumberBlock(min, max, step);
	}
	
	/* Compute the Max */
	private long computeMax(long tmpCutOff, int tmpCacheSize, long tmpStep, long tmpMax) {
		return ((tmpCutOff+(tmpCacheSize-1)*tmpStep)>tmpMax) ? tmpMax : (tmpCutOff+(tmpCacheSize-1)*tmpStep);
	}
	
	/* Compute the Min */
	private long computeMin(long tmpCutOff, long tmpStart, long tmpMin) {
		long retvlu = tmpCutOff;
		if(retvlu<tmpStart) retvlu = tmpStart ;
		if(retvlu<tmpMin)  	retvlu = tmpMin ;
		return retvlu;
	}
	
}
