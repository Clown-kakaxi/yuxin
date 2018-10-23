package com.yuchengtech.emp.core.sequence.picker;

import com.yuchengtech.emp.core.sequence.Sequence;

/*
 * CycleSequencePicker
 */
public class CycleSequencePicker implements SequencePicker {
	
	/** Instance */
	private static CycleSequencePicker instance ;
	
	/** Constructor */
	private CycleSequencePicker() { }
	
	/** Get Instance */
	public CycleSequencePicker getInstance() {
		if(instance==null) {
			instance = new CycleSequencePicker();
		}
		return instance;
	}
	
//	@Override
	public Long pick(Sequence sequence) {
		// 这里是一个优化的点....
		return sequence.nextval();
	}

}
