package com.yuchengtech.emp.core.sequence.picker;

import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.core.sequence.Sequence;

/*
 * Sequence Picker 
 * 
 * 这个口子拿出来是为了要兼容一些不同的实现方法；
 */
@Repository
public class CommonSequencePicker implements SequencePicker {
	
	/** Instance */
	private static CommonSequencePicker instance ;
	
	/** Constructor */
	private CommonSequencePicker() { }
	
	/** Get Instance */
	public CommonSequencePicker getInstance() {
		if(instance==null) {
			instance = new CommonSequencePicker();
		}
		return instance;
	}
	
//	@Override
	public Long pick(Sequence sequence) {
		// 这里是一个优化的点....
		return sequence.nextval();
	}
	
}
