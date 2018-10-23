package com.yuchengtech.emp.core.keygen.sequence.impl;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.core.keygen.sequence.SequenceKeyGenerator;
import com.yuchengtech.emp.core.sequence.Sequence;
import com.yuchengtech.emp.core.sequence.SequenceFactory;
import com.yuchengtech.emp.core.sequence.text.SequenceFormat;
//import com.yuchengtech.emp.core.sequence.util.SequencePicker;

/*
 * 
 */
@Repository
public class SequenceKeyGeneratorImpl implements SequenceKeyGenerator {

	
	/** Sequence Factory */
	private SequenceFactory seqFactory = SequenceFactory.getInstance();
	
	
//	@Override
	public long getNumberKey(String seqKey) {
		return this.getNumberKey(seqKey, null);
	}

//	@Override
	public long getNumberKey(Sequence sequence) {
		return sequence.nextval();
	}
	
//	@Override
	public long getNumberKey(String seqKey, String roolingKey) {
		return seqFactory.getSequence(seqKey, roolingKey).nextval();
	}
	
//	@Override
	public String getNumberFormatKey(String seqKey) {
		return this.getNumberFormatKey(seqKey, null);
	}

//	@Override
	public String getNumberFormatKey(Sequence sequence) {
		return new SequenceFormat(sequence).format(sequence.nextval());
	}
	
//	@Override
	public String getNumberFormatKey(String seqKey, String roolingKey) {
		Sequence seq = this.seqFactory.getSequence(seqKey, roolingKey);
		return new SequenceFormat(seq).format(seq.nextval());
	}
	
}
