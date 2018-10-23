package com.yuchengtech.emp.core.sequence.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Number Block
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class NumberBlock {

	private long max;
	private long min;
	private long step = 1L;
	private Collection<Long> figures = new ArrayList<Long>();
	
	protected NumberBlock(long min, long max, long step) {
		this.min = min;
		this.max = max;
		this.step = step;
		this.countFigures();
	}
	
	public long getMax() {
		return this.max;
	}
	
	public long getMin() {
		return this.min;
	}
	
	public long getStep() {
		return this.step;
	}
	
	public Collection<Long> getFigures() {
		return this.figures;
	}
	
	/* Compute Figures */
	private void countFigures() {
		if(min<max) {
			this.figures.add(min);
		}
		long nums = min;
		while((nums+=step)<=max) {
			this.figures.add(nums);
		}
	}
	
}